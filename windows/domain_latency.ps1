# 抖音/字节跳动域名列表
$domains = @(
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

# 测试参数
$testCount = 5
$port = 443
$timeoutMs = 2000

# TCP延迟测试函数（修复版，确保资源正确释放）
function Test-TcpLatency {
    param(
        [string]$Hostname,
        [int]$Port,
        [int]$TimeoutMs,
        [string]$AddressFamily
    )
    
    $latencies = @()
    
    for ($i = 0; $i -lt $testCount; $i++) {
        $client = $null
        try {
            $addressFamilyEnum = if ($AddressFamily -eq "IPv4") {
                [System.Net.Sockets.AddressFamily]::InterNetwork
            } else {
                [System.Net.Sockets.AddressFamily]::InterNetworkV6
            }
            
            $client = New-Object System.Net.Sockets.TcpClient($addressFamilyEnum)
            $sw = [System.Diagnostics.Stopwatch]::StartNew()
            
            $task = $client.ConnectAsync($Hostname, $Port)
            if ($task.Wait($TimeoutMs)) {
                $sw.Stop()
                $latencies += $sw.Elapsed.TotalMilliseconds
            }
        }
        catch {
            # 忽略连接错误
        }
        finally {
            if ($client -ne $null) {
                $client.Close()
                $client.Dispose()
            }
        }
        
        Start-Sleep -Milliseconds 100
    }
    
    if ($latencies.Count -eq 0) {
        return [PSCustomObject]@{
            Avg  = "N/A"
            Min  = "N/A"
            Max  = "N/A"
            Loss = 100.0
        }
    }
    
    return [PSCustomObject]@{
        Avg  = [math]::Round(($latencies | Measure-Object -Average).Average, 1)
        Min  = [math]::Round(($latencies | Measure-Object -Minimum).Minimum, 1)
        Max  = [math]::Round(($latencies | Measure-Object -Maximum).Maximum, 1)
        Loss = [math]::Round((($testCount - $latencies.Count) / $testCount) * 100, 1)
    }
}

# 结果数组
$results = @()

Write-Host "`n=== 抖音/字节跳动域名TCP 443端口延迟测试 (IPv4+IPv6) ===" -ForegroundColor Cyan
Write-Host "测试时间: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
Write-Host "测试次数: 每个域名每个协议测试$testCount次"
Write-Host "======================================================`n"

# 测试所有域名
for ($i = 0; $i -lt $domains.Count; $i++) {
    $domain = $domains[$i]
    $progress = [math]::Round(($i / $domains.Count) * 100, 1)
    
    Write-Host "[$progress%] 正在测试: $domain" -ForegroundColor Yellow
    
    # 同时测试IPv4和IPv6
    $v4 = Test-TcpLatency -Hostname $domain -Port $port -TimeoutMs $timeoutMs -AddressFamily "IPv4"
    $v6 = Test-TcpLatency -Hostname $domain -Port $port -TimeoutMs $timeoutMs -AddressFamily "IPv6"
    
    $results += [PSCustomObject]@{
        域名             = $domain
        "IPv4平均(ms)"   = $v4.Avg
        "IPv4最小(ms)"   = $v4.Min
        "IPv4最大(ms)"   = $v4.Max
        "IPv4丢包(%)"    = $v4.Loss
        "IPv6平均(ms)"   = $v6.Avg
        "IPv6最小(ms)"   = $v6.Min
        "IPv6最大(ms)"   = $v6.Max
        "IPv6丢包(%)"    = $v6.Loss
    }
}

Write-Host "`n[100%] 测试完成！" -ForegroundColor Green

# 输出完美对齐的终端表格
Write-Host "`n`n=== 终端对齐表格 ===" -ForegroundColor Green
$results | Format-Table -AutoSize -Property 域名, "IPv4平均(ms)", "IPv4最小(ms)", "IPv4最大(ms)", "IPv4丢包(%)", "IPv6平均(ms)", "IPv6最小(ms)", "IPv6最大(ms)", "IPv6丢包(%)"

# 输出制表符分隔格式（可直接复制到Excel）
Write-Host "`n`n=== 制表符分隔格式（复制到Excel） ===" -ForegroundColor Green
Write-Host "域名`tIPv4平均(ms)`tIPv4最小(ms)`tIPv4最大(ms)`tIPv4丢包(%)`tIPv6平均(ms)`tIPv6最小(ms)`tIPv6最大(ms)`tIPv6丢包(%)"
foreach ($r in $results) {
    Write-Host "$($r.域名)`t$($r.'IPv4平均(ms)')`t$($r.'IPv4最小(ms)')`t$($r.'IPv4最大(ms)')`t$($r.'IPv4丢包(%)')`t$($r.'IPv6平均(ms)')`t$($r.'IPv6最小(ms)')`t$($r.'IPv6最大(ms)')`t$($r.'IPv6丢包(%)')"
}