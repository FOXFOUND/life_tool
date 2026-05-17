<#
抖音全域名双栈优选Hosts - 最终完美版
✅ 自动覆盖同域名，不追加
✅ IPv4/IPv6 交替各测5次
✅ 纯TCP 443 无ping
✅ 自动清理旧配置
✅ 保留原始Hosts内容
#>

# 抖音全域名列表
$targetDomains = @(
    "www.douyin.com",
    "fonts.bytedance.com",
    "lf3-pendah.bytetos.com",
    "lf3-short.ibytedapm.com",
    "lf3-social.iesdouyin.com",
    "lf3-static.bytednsdoc.com",
    "lf3-webcast.bytetos.com",
    "lf26-font-sign.bytehwm.com",
    "lf-c-flwb.bytetos.com",
    "lf-cdn-tos.bytescm.com",
    "lf-douyin-pc-web.douyinstatic.com",
    "lf-normal-gr-sourcecdn.bytegecko.com",
    "lf-rc1.yhgfb-cn-static.com",
    "lf-security.bytegoofy.com",
    "lf-static.applogcdn.com",
    "lf-ucenter-web.yhgfb-cn-static.com",
    "p3-pc-sign.douyinpic.com",
    "p3-pc-weboff.byteimg.com",
    "p3-pc.douyinpic.com",
    "p3-sign.douyinpic.com",
    "p3.douyinpic.com",
    "p5-ex-gddgtc-sign.douyinpic.com",
    "p9-pc-sign.douyinpic.com",
    "p11-sign.douyinpic.com",
    "p11.douyinpic.com",
    "p26-safelight.byteimg.com",
    "p26-sign.douyinpic.com",
    "p-pc-weboff.byteimg.com",
    "privacy.zijieapi.com",
    "sf1-cdn-tos.douyinstatic.com"
)

$hostsPath = "$env:SystemRoot\System32\drivers\etc\hosts"
$tagStart = "# ====== 抖音双栈优选节点【自动生成】 ======"
$tagEnd   = "# ====== 抖音节点配置结束【勿手动修改】 ======"

# 读取并清理旧区块（彻底删除旧配置，保证覆盖）
$allHosts = Get-Content -Path $hostsPath -Encoding UTF8
$cleanHosts = @()
$inBlock = $false

foreach ($line in $allHosts) {
    if ($line.Trim() -eq $tagStart) { $inBlock = $true; continue }
    if ($line.Trim() -eq $tagEnd)   { $inBlock = $false; continue }
    if (-not $inBlock) { $cleanHosts += $line }
}

# TCP 5次平均测速函数（IPv4 + IPv6 通用）
function Get-TcpAvgLatency {
    param(
        [string]$TargetIP,
        [int]$Port = 443,
        [int]$TestTimes = 5
    )
    $sumMs = 0.0
    for ($i = 1; $i -le $TestTimes; $i++) {
        $sw = [System.Diagnostics.Stopwatch]::StartNew()
        $tcpClient = New-Object System.Net.Sockets.TcpClient
        try {
            $tcpClient.Connect($TargetIP, $Port)
        }
        catch { }
        finally {
            $tcpClient.Close()
            $sw.Stop()
            $sumMs += $sw.Elapsed.TotalMilliseconds
        }
    }
    return [math]::Round($sumMs / $TestTimes, 2)
}

$newRules = @()

# 开始优选所有域名
foreach ($domain in $targetDomains) {
    Write-Host "`n[正在优选] $domain" -ForegroundColor Cyan

    $dnsV4 = Resolve-DnsName -Name $domain -Type A -ErrorAction SilentlyContinue
    $dnsV6 = Resolve-DnsName -Name $domain -Type AAAA -ErrorAction SilentlyContinue

    $bestV4 = $null
    $bestV4Ms = 999999
    $bestV6 = $null
    $bestV6Ms = 999999

    # IPv4 测速
    if ($dnsV4) {
        foreach ($ip in $dnsV4.IPAddress) {
            $ms = Get-TcpAvgLatency -TargetIP $ip
            Write-Host "  IPv4 $ip  5次平均：$ms ms"
            if ($ms -lt $bestV4Ms) {
                $bestV4Ms = $ms
                $bestV4 = $ip
            }
        }
    }

    # IPv6 测速
    if ($dnsV6) {
        foreach ($ip in $dnsV6.IPAddress) {
            $ms = Get-TcpAvgLatency -TargetIP $ip
            Write-Host "  IPv6 $ip  5次平均：$ms ms"
            if ($ms -lt $bestV6Ms) {
                $bestV6Ms = $ms
                $bestV6 = $ip
            }
        }
    }

    # 写入最优结果（覆盖模式，唯一一条）
    if ($bestV4) { $newRules += "$bestV4`t$domain" }
    if ($bestV6) { $newRules += "$bestV6`t$domain" }

    Write-Host "✅ 完成：IPv4[$bestV4Ms] IPv6[$bestV6Ms]" -ForegroundColor Green
}

# 写入新Hosts（完全覆盖旧区块）
$finalContent = $cleanHosts + @($tagStart) + $newRules + @($tagEnd)
Set-Content -Path $hostsPath -Value $finalContent -Encoding UTF8 -Force

# 刷新DNS
ipconfig /flushdns

Write-Host "`n✅ 全部完成！旧记录已覆盖，最优节点已生效！" -ForegroundColor Green