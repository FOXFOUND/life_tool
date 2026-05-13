# ========== 全局编码设置（核心修复乱码） ==========
$ErrorActionPreference = 'SilentlyContinue'
# 强制所有输出使用 UTF-8 编码
$OutputEncoding = [System.Text.Encoding]::UTF8
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
[Console]::InputEncoding = [System.Text.Encoding]::UTF8
$Host.UI.RawUI.OutputEncoding = [System.Text.Encoding]::UTF8

$host.UI.RawUI.WindowTitle = "详细网络配置诊断"

# ========== 以下为原功能代码（已修复语法错误+编码统一） ==========
Write-Output "===== 系统信息 ====="
Get-ComputerInfo | Select-Object CsName, WindowsVersion, WindowsEditionId | Format-List

Write-Output "`n===== 网卡硬件信息和驱动 ====="
Get-NetAdapter -Physical | Sort-Object ifIndex | ForEach-Object {
    $adapter = $_
    Write-Output "`n[$($adapter.ifIndex)] $($adapter.Name) ($($adapter.InterfaceDescription))"
    Write-Output "状态: $($adapter.Status), 速度: $($adapter.LinkSpeed), MAC: $($adapter.MacAddress), MTU: $($adapter.MtuSize)"
    Write-Output "驱动日期: $($adapter.DriverDate), 版本: $($adapter.DriverVersion), 设备名: $($adapter.DeviceName)"
    
    # 修复：Try-Catch 完整闭合，避免解析错误
    try {
        $pnp = Get-PnpDevice -InstanceId $adapter.PnPDeviceID -ErrorAction Stop
        Write-Output "PNP状态: $($pnp.Status)"
    } catch {
        Write-Output "PNP状态: 无法获取（$($_.Exception.Message)）"
    }

    try {
        $pwr = Get-CimInstance -ClassName MSPower_DeviceWakeEnable -Namespace root/wmi | Where-Object { $_.InstanceName -like "*$($adapter.Name)*" }
        if ($pwr) { 
            Write-Output "网络唤醒: 已启用" 
        } else { 
            Write-Output "网络唤醒: 未配置或禁用" 
        }
    } catch {
        Write-Output "网络唤醒: 无法获取（$($_.Exception.Message)）"
    }
} # ForEach-Object 闭合大括号（修复匹配问题）

Write-Output "`n===== 所有网卡高级属性 ====="
Get-NetAdapter | Sort-Object ifIndex | ForEach-Object {
    $adapterName = $_.Name
    if ($_.Status -ne 'Up' -and $_.Status -ne 'Disconnected') { return }
    Write-Output "`n网卡: $adapterName ($($_.InterfaceDescription))"
    $advancedProps = Get-NetAdapterAdvancedProperty -Name $adapterName -ErrorAction SilentlyContinue
    if ($advancedProps) {
        $advancedProps | Sort-Object DisplayName | Format-Table DisplayName, DisplayValue, RegistryKeyword -AutoSize -Wrap | Out-String | Write-Output
    } else {
        Write-Output "无高级属性"
    }
}

Write-Output "`n===== RSS 详细信息 ====="
Get-NetAdapter | Where-Object { $_.Status -eq 'Up' } | ForEach-Object {
    $rss = Get-NetAdapterRss -Name $_.Name -ErrorAction SilentlyContinue
    if ($rss) {
        Write-Output "`n$($_.Name): 启用=$($rss.Enabled), 队列数=$($rss.NumberOfReceiveQueues), Profile=$($rss.Profile)"
        Write-Output "基础处理器: $($rss.BaseProcessorNumber), 最大处理器: $($rss.MaxProcessorNumber)"
        Write-Output "最大处理器数: $($rss.MaxProcessors)"
    }
}

Write-Output "`n===== 所有 IP 配置 (IPv4/IPv6) ====="
Get-NetIPConfiguration -Detailed | ForEach-Object {
    Write-Output "`n接口: $($_.InterfaceAlias) [索引 $($_.InterfaceIndex)]"
    # 修复：空值处理，避免输出空行/错误
    $ipv4Addr = if ($_.IPv4Address) { $_.IPv4Address.IPAddress } else { "无" }
    $ipv4Gateway = if ($_.IPv4DefaultGateway) { $_.IPv4DefaultGateway.NextHop } else { "无" }
    $ipv4Dns = if ($_.DNSServer | Where-Object { $_.AddressFamily -eq 2 }) { 
        $_.DNSServer | Where-Object { $_.AddressFamily -eq 2 } | ForEach-Object { $_.ServerAddresses } -Join ", "
    } else { "无" }
    
    $ipv6Addr = if ($_.IPv6Address) { $_.IPv6Address.IPAddress } else { "无" }
    $ipv6Gateway = if ($_.IPv6DefaultGateway) { $_.IPv6DefaultGateway.NextHop } else { "无" }
    $ipv6Dns = if ($_.DNSServer | Where-Object { $_.AddressFamily -eq 23 }) { 
        $_.DNSServer | Where-Object { $_.AddressFamily -eq 23 } | ForEach-Object { $_.ServerAddresses } -Join ", "
    } else { "无" }

    Write-Output "IPv4地址: $ipv4Addr"
    Write-Output "IPv4默认网关: $ipv4Gateway"
    Write-Output "IPv4 DNS服务器: $ipv4Dns"
    Write-Output "IPv6地址: $ipv6Addr"
    Write-Output "IPv6默认网关: $ipv6Gateway"
    Write-Output "IPv6 DNS服务器: $ipv6Dns"
}

Write-Output "`n===== IPv4/IPv6 接口跃点数和自动跃点 ====="
Get-NetIPInterface | Where-Object { $_.AddressFamily -eq 'IPv4' -or $_.AddressFamily -eq 'IPv6' } |
Select-Object ifIndex, InterfaceAlias, AddressFamily, NlMtu, InterfaceMetric, AutomaticMetric, Dhcp, ConnectionState |
Sort-Object AddressFamily, InterfaceMetric | Format-Table -AutoSize

Write-Output "`n===== TCP 设置 (Get-NetTCPSetting) ====="
Get-NetTCPSetting | Select-Object * | Format-List *

Write-Output "`n===== TCP 全局参数 (netsh int tcp show global) ====="
netsh int tcp show global

Write-Output "`n===== TCP 统计信息 ====="
netsh int tcp show statistics

Write-Output "`n===== UDP 统计信息 ====="
netsh int udp show statistics

Write-Output "`n===== IP 统计信息 ====="
netsh int ip show ipstats

Write-Output "`n===== 路由表 (IPv4) ====="
Get-NetRoute -AddressFamily IPv4 | Sort-Object DestinationPrefix | Format-Table -AutoSize

Write-Output "`n===== 路由表 (IPv6) ====="
Get-NetRoute -AddressFamily IPv6 | Sort-Object DestinationPrefix | Format-Table -AutoSize

Write-Output "`n===== 网络活动连接 (ESTABLISHED) ====="
Get-NetTCPConnection -State Established | Select-Object LocalAddress, LocalPort, RemoteAddress, RemotePort, OwningProcess |
Format-Table -AutoSize

Write-Output "`n===== 监听端口 ====="
Get-NetTCPConnection -State Listen | Select-Object LocalAddress, LocalPort, OwningProcess |
Group-Object LocalPort | ForEach-Object {
    $p = $_.Group | Select-Object -First 1
    Write-Output "端口 $($p.LocalPort) : 进程 PID $($p.OwningProcess)"
}

Write-Output "`n===== 注册表 TCP/IP 参数 (HKEY_LOCAL_MACHINE\SYSTEM\CurrentControlSet\Services\Tcpip\Parameters) ====="
Get-ItemProperty "HKLM:\SYSTEM\CurrentControlSet\Services\Tcpip\Parameters" | Format-List *

Write-Output "`n===== 注册表接口特定 TCP/IP 参数 ====="
Get-ChildItem "HKLM:\SYSTEM\CurrentControlSet\Services\Tcpip\Parameters\Interfaces" -ErrorAction SilentlyContinue | ForEach-Object {
    $ifPath = $_.PSPath
    $props = Get-ItemProperty -Path $ifPath -ErrorAction SilentlyContinue | Select-Object * -ExcludeProperty PSPath, PSParentPath, PSChildName, PSDrive, PSProvider
    Write-Output "`n接口 GUID: $($_.PSChildName)"
    $props | Format-List
}

Write-Output "`n===== DNS 缓存设置 ====="
if (Test-Path "HKLM:\SYSTEM\CurrentControlSet\Services\Dnscache\Parameters") {
    Get-ItemProperty "HKLM:\SYSTEM\CurrentControlSet\Services\Dnscache\Parameters" -ErrorAction SilentlyContinue | Format-List
} else {
    Write-Output "DNS缓存注册表项不存在"
}

Write-Output "`n===== 服务状态 (网卡相关) ====="
$services = @("Dhcp", "Dnscache", "WinHttpAutoProxySvc", "NlaSvc", "lmhosts", "Tcpip", "PolicyAgent")
Get-Service -Name $services -ErrorAction SilentlyContinue | Select-Object Name, DisplayName, Status, StartType | Format-Table -AutoSize

Write-Output "`n===== 防火墙配置概要 ====="
netsh advfirewall show allprofiles

Write-Output "`n===== QoS 策略 ====="
Get-NetQosPolicy | Format-List *

Write-Output "`n===== 传递优化状态 ====="
$doKey = "HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\DeliveryOptimization\Config"
if (Test-Path $doKey) {
    Get-ItemProperty $doKey | Format-List
} else {
    Write-Output "传递优化注册表项不存在"
}

Write-Output "ok"