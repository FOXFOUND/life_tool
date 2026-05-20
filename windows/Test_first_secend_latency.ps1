# 测试稳定公网地址
$targetUrl = "https://www.douyin.com/favicon.ico"

# 定义分段测速函数
function Test-NetDetailLatency {
    param([string]$Url)
    $uri = [System.Uri]$Url
    $domain = $uri.Host

    # 1. DNS解析耗时
    $dnsStart = [DateTime]::Now.Ticks
    $dnsRes = [System.Net.Dns]::GetHostAddresses($domain)
    $dnsEnd = [DateTime]::Now.Ticks
    $dnsMs = [Math]::Round(($dnsEnd - $dnsStart) / 10000, 2)

    # 2. TCP+TLS完整连接+请求总耗时
    $sw = [System.Diagnostics.Stopwatch]::StartNew()
    try{
        $req = [System.Net.HttpWebRequest]::Create($Url)
        $req.Timeout = 5000
        $req.KeepAlive = $true
        $resp = $req.GetResponse()
        $resp.Close()
    }catch{}
    $sw.Stop()
    $totalMs = $sw.ElapsedMilliseconds

    Write-Host "——————————————————————"
    Write-Host "DNS解析耗时：$dnsMs ms"
    Write-Host "整体请求总耗时：$totalMs ms"
    return $totalMs
}

Write-Host "【第一次 冷连接：全新握手+DNS查询】`n" -ForegroundColor Cyan
$first = Test-NetDetailLatency -Url $targetUrl

Start-Sleep -Milliseconds 100

Write-Host "`n【第二次 热连接：复用连接+缓存】`n" -ForegroundColor Green
$second = Test-NetDetailLatency -Url $targetUrl

# 计算差值
$gap = $first - $second
Write-Host "`n【耗时差值】首次比二次多耗时：$gap ms" -ForegroundColor Yellow
Write-Host "【核心差异】差值 = DNS查询 + TCP三次握手 + TLS加密握手开销" -ForegroundColor Gray