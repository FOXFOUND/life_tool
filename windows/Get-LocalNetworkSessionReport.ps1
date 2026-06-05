<#
.SYNOPSIS
    检测网络会话数并评估系统支持的最大检测能力
.DESCRIPTION
    同时统计 TCP 连接及 UDP 端点，根据系统内存和检测方式给出可安全检测的会话数参考。
    适用于 Windows、Linux 和 macOS，自动选择最优检测方式。
.NOTES
    需要 PowerShell 7+
#>

# 区域设置，避免中文环境下 netstat 解析问题
$PSDefaultParameterValues['*:Encoding'] = 'utf8'

function Get-SystemMaxPorts {
    # 理论端口范围：0-65535，实际可用动态端口由系统决定
    $maxPorts = 65535
    # 尝试读取动态端口范围（Windows）
    try {
        $netsh = netsh int ipv4 show dynamicport tcp 2>$null
        if ($netsh -match '起始端口\s*:\s*(\d+).*端口数\s*:\s*(\d+)') {
            $start = [int]$Matches[1]
            $num   = [int]$Matches[2]
            Write-Host "系统 TCP 动态端口: ${start} - $($start+$num-1) (共 $num 个)" -ForegroundColor DarkGray
            $maxPorts = [Math]::Min($maxPorts, $num)
        }
    } catch { }
    Write-Host "理论每对(源IP,目标IP,目标端口)最大可用源端口: $maxPorts" -ForegroundColor DarkGray
    return $maxPorts
}

function Get-TcpConnections {
    param([switch]$Lightweight)
    try {
        if ($Lightweight -or ($IsWindows -and $PSVersionTable.PSVersion.Major -ge 6)) {
            # 轻量方式：通过 .NET IPGlobalProperties（所有平台均可）
            $ipp = [System.Net.NetworkInformation.IPGlobalProperties]::GetIPGlobalProperties()
            $conns = $ipp.GetActiveTcpConnections()
            return $conns
        } else {
            # 标准 cmdlet（PowerShell 5.1+ 也可用，但对象较重）
            $conns = Get-NetTCPConnection -ErrorAction Stop
            return $conns
        }
    } catch {
        # 回退到 ss 命令（Linux/macOS）
        if ($IsLinux -or $IsMacOS) {
            $output = & ss -tan state established 2>$null
            if ($LASTEXITCODE -eq 0) {
                # 用自定义对象模拟
                $lines = $output -split "`n" | Where-Object { $_ -match 'ESTAB' }
                $conns = $lines | ForEach-Object {
                    [PSCustomObject]@{ State = 'Established' }
                }
                return $conns
            }
        }
        Write-Warning "无法获取 TCP 连接: $_"
        return @()
    }
}

function Get-UdpEndpoints {
    try {
        $ep = Get-NetUDPEndpoint -ErrorAction Stop
        return $ep
    } catch {
        if ($IsLinux -or $IsMacOS) {
            $output = & ss -uan 2>$null
            if ($LASTEXITCODE -eq 0) {
                $lines = $output -split "`n" | Where-Object { $_ -match 'UNCONN' }
                $eps = $lines | ForEach-Object {
                    [PSCustomObject]@{ LocalAddress = '0.0.0.0'; LocalPort = 0 }
                }
                return $eps
            }
        }
        Write-Warning "无法获取 UDP 端点: $_"
        return @()
    }
}

# ---------- 主执行 ----------
Write-Host "===== 网络会话检测报告 ($(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')) =====" -ForegroundColor Cyan

# 1. 统计 TCP 连接数（默认使用轻量 .NET 方法，避免高内存占用）
$tcpConnections = Get-TcpConnections -Lightweight
$tcpCount = $tcpConnections.Count
Write-Host "当前 TCP 连接数: $tcpCount" -ForegroundColor Green

# 2. 统计 UDP 端点
$udpEndpoints = Get-UdpEndpoints
$udpCount = $udpEndpoints.Count
Write-Host "当前 UDP 端点数量: $udpCount" -ForegroundColor Green

# 3. 系统最大理论端口评估
Write-Host "`n--- 系统支持上限评估 ---" -ForegroundColor Yellow
$maxPorts = Get-SystemMaxPorts

# 4. 根据当前内存粗略计算 PowerShell 可安全检测的会话数
# 经验值：轻量对象每个约占用 600 字节，为安全保留 1/4 可用内存
$os = if ($IsWindows) { Get-CimInstance Win32_OperatingSystem } else { $null }
$freeMemMB = if ($os) { $os.FreePhysicalMemory / 1024 } else { (Get-Content /proc/meminfo | Select-String 'MemAvailable' | ForEach-Object { [int]($_ -split '\s+')[1] }) / 1024 }
$safeTcpLimit = [math]::Floor(($freeMemMB * 1024 * 1024) / 600 * 0.25)

Write-Host "操作系统可用内存: $([math]::Round($freeMemMB, 2)) MB" -ForegroundColor DarkGray
Write-Host "基于当前可用内存，安全检测的 TCP 连接数建议不超过: $safeTcpLimit" -ForegroundColor Yellow

if ($tcpCount -gt 50000) {
    Write-Warning "当前连接数已超过 5 万，直接使用 Get-NetTCPConnection 可能严重影响性能。本脚本已采用轻量 .NET 方法，但若连接数超过 20 万，仍可能出现缓慢。"
}

# 5. 提供简要统计
if ($tcpConnections -and $tcpConnections[0].State) {
    # 如果是 .NET TcpConnectionInformation 对象，State 可能不包含 Listen（.NET 只返回 Established）
    $stateCounts = $tcpConnections | Group-Object State | Select-Object Count, Name
    Write-Host "`nTCP 连接状态分布:" -ForegroundColor Cyan
    $stateCounts | Format-Table -AutoSize
}

Write-Host "`n检测完成。若要持续监控，建议将此脚本加入定时任务。" -ForegroundColor Cyan