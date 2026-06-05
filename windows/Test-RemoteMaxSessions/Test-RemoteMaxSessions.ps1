# 宽带最大并发连接测试 - 修复稳定极速版
$targets = @(
    "baidu.com","qq.com","taobao.com","jd.com","163.com","bilibili.com","aliyun.com",
    "weibo.com","youku.com","iqiyi.com","sogou.com","sohu.com","toutiao.com","360.cn",
    "csdn.net","zhihu.com","douban.com","kuaishou.com","douyin.com","pinduoduo.com",
    "tmall.com","alibaba.com","sina.com.cn","ifeng.com","12306.cn","ctrip.com",
    "meituan.com","ele.me","amap.com","qqmail.com","xinhuanet.com","huanqiu.com",
    "microsoft.com","oschina.net","xiaohongshu.com","vip.com","suning.com","126.com",
    "people.com.cn","qunar.com","mafengwo.cn","didiglobal.com","netease.com","baidu.cn",
    "baidu.com","qq.com","taobao.com","jd.com","163.com","bilibili.com","aliyun.com",
    "weibo.com","youku.com","iqiyi.com","sogou.com","sohu.com","toutiao.com","360.cn",
    "csdn.net","zhihu.com","douban.com","kuaishou.com","douyin.com","pinduoduo.com",
    "tmall.com","alibaba.com","sina.com.cn","ifeng.com","12306.cn","ctrip.com",
    "meituan.com","ele.me","amap.com","qqmail.com","xinhuanet.com","huanqiu.com",
    "baidu.com","qq.com","taobao.com","jd.com","163.com","bilibili.com","aliyun.com",
    "weibo.com","youku.com","iqiyi.com","sogou.com","sohu.com","toutiao.com","360.cn",
    "csdn.net","zhihu.com","douban.com","kuaishou.com","douyin.com","baidu.com","qq.com"
)

$connectEach = 10       # 每个网站 10 个连接
$port = 443
$timeoutMs = 500
$total = $targets.Count * $connectEach

Write-Host "`n=== 测试：$($targets.Count) 个网站 × $connectEach 连接 = 总 $total 并发 ===" -ForegroundColor Cyan

# 稳定并行测试
$results = 1..$connectEach | ForEach-Object -Parallel {
    $sites = $using:targets
    $port = $using:port
    $timeout = $using:timeoutMs
    $success = 0
    $failed = 0

    foreach ($site in $sites) {
        try {
            $tcp = New-Object System.Net.Sockets.TcpClient
            $async = $tcp.BeginConnect($site, $port, $null, $null)
            $ok = $async.AsyncWaitHandle.WaitOne($timeout)
            if ($ok -and $tcp.Connected) { $success++ }
            else { $failed++ }
            $tcp.Close()
        }
        catch { $failed++ }
    }

    [PSCustomObject]@{ S = $success; F = $failed }
} -ThrottleLimit 150

# 统计结果
$successTotal = ($results | Measure-Object -Sum S).Sum
$failedTotal = ($results | Measure-Object -Sum F).Sum
$failRate = [math]::Round($failedTotal / $total * 100, 2)

# 输出
Write-Host "✅ 成功：$successTotal`n❌ 失败：$failedTotal`n📊 失败率：$failRate%`n" -ForegroundColor White

if ($failRate -ge 15) {
    Write-Host "❌ 已超出宽带 NAT 上限" -ForegroundColor Red
}
else {
    Write-Host "✅ 稳定支持 $total 并发连接" -ForegroundColor Green
}