<#
.SYNOPSIS
    完整测试国内 100 个主流网站（www 域名）的 IPv4 / IPv6 TCP 443 端口延迟
.DESCRIPTION
    - 强制解析 A (IPv4) 和 AAAA (IPv6) 记录，跟随 CNAME 链
    - TCP 三次握手耗时作为延迟指标
    - 需要 PowerShell 7 环境，请以管理员身份运行
#>
#Requires -Version 7.0

# ---------- 配置区域 ----------
# 国内主流网站列表（www 完整域名）
$WebsiteList = @(
    "www.baidu.com", "www.qq.com", "www.taobao.com", "www.sina.com.cn", "www.weibo.com",
    "www.163.com", "www.sohu.com", "www.tmall.com", "www.360.cn", "www.jd.com",
    "www.alipay.com", "www.zhihu.com", "www.bilibili.com", "www.meituan.com", "www.douyin.com",
    "www.csdn.net", "www.ifeng.com", "www.people.com.cn", "www.xinhuanet.com", "www.cctv.com",
    "www.china.com.cn", "www.huanqiu.com", "www.pinduoduo.com", "www.ctrip.com", "www.anjuke.com",
    "www.58.com", "www.zol.com.cn", "www.pcauto.com.cn", "www.autohome.com.cn", "www.qunar.com",
    "www.douban.com", "tieba.baidu.com", "www.cnblogs.com", "www.jianshu.com", "www.vip.com",
    "www.iqiyi.com", "www.youku.com", "www.huya.com", "www.douyu.com", "www.sogou.com",
    "www.smzdm.com", "www.xcar.com.cn", "www.qidian.com", "www.17k.com", "www.zongheng.com",
    "www.kuaishou.com", "www.ximalaya.com", "www.pingan.com", "www.cmbchina.com", "www.icbc.com.cn",
    "www.alibabacloud.com", "www.aliyun.com", "cloud.tencent.com", "www.qcloud.com", "www.mi.com",
    "www.oppo.com", "www.vivo.com.cn", "www.huawei.com", "www.dangdang.com", "www.yhd.com",
    "www.mogujie.com", "www.meilishuo.com", "www.etao.com", "www.chinaz.com", "www.admin5.com",
    "www.jzsc.net", "www.zcool.com.cn", "www.ui.cn", "www.lanrenmb.com", "www.mb5u.com",
    "www.onlinedown.net", "www.skycn.com", "www.mydrivers.com", "www.yesky.com", "www.ithome.com",
    "www.cnbeta.com", "www.geekpark.net", "www.leiphone.com", "www.36kr.com", "www.eastmoney.com",
    "www.hexun.com", "www.cnfol.com", "www.jrj.com.cn", "www.southmoney.com", "www.zhcw.com",
    "www.lottery.gov.cn", "www.guahao.com", "www.dxy.cn", "www.39.net", "www.xywy.com",
    "www.weather.com.cn", "www.12306.cn", "www.cnipa.gov.cn", "www.chinacourt.org", "www.moe.gov.cn",
    "www.edu.cn", "www.xiaohongshu.com", "www.ele.me", "www.12321.cn", "www.cnnic.cn"
)

$TestPort      = 443
$TcpTimeoutMs  = 2000   # 单个 TCP 连接超时（毫秒）
# ------------------------------

# TCP 延迟测试函数
function Test-TcpLatency {
    param(
        [string]$IPAddress,
        [int]$Port = $TestPort,
        [int]$TimeoutMs = $TcpTimeoutMs
    )
    try {
        $ip = [System.Net.IPAddress]::Parse($IPAddress)
        $client = New-Object System.Net.Sockets.TcpClient
        $sw = [System.Diagnostics.Stopwatch]::StartNew()
        $task = $client.ConnectAsync($ip, $Port)
        if ($task.Wait($TimeoutMs)) {
            $sw.Stop()
            $latency = $sw.ElapsedMilliseconds
            $client.Close()
            return $latency
        } else {
            $sw.Stop()
            $client.Close()
            return $null
        }
    } catch {
        return $null
    }
}

# 解析 IPv4 地址（取第一个）
function Get-IPv4Address {
    param([string]$Hostname)
    try {
        $records = Resolve-DnsName -Name $Hostname -Type A -DnsOnly -NoHostsFile -ErrorAction Stop
        $ip = ($records | Where-Object { $_.IPAddress -match '^\d+\.\d+\.\d+\.\d+$' } | Select-Object -First 1).IPAddress
        return $ip
    } catch {
        return $null
    }
}

# 解析 IPv6 地址（取第一个）
function Get-IPv6Address {
    param([string]$Hostname)
    try {
        $records = Resolve-DnsName -Name $Hostname -Type AAAA -DnsOnly -NoHostsFile -ErrorAction Stop
        $ip = ($records | Where-Object { $_.IPAddress -match ':' } | Select-Object -First 1).IPAddress
        return $ip
    } catch {
        return $null
    }
}

# ---------- 主测试逻辑 ----------
$Results = @()
$Total = $WebsiteList.Count
$Current = 0

foreach ($Site in $WebsiteList) {
    $Current++
    Write-Progress -Activity "TCP 延迟测试" -Status "$Current/$Total : $Site" -PercentComplete (($Current / $Total) * 100)

    # 解析地址
    $IPv4 = Get-IPv4Address -Hostname $Site
    $IPv6 = Get-IPv6Address -Hostname $Site

    # 测试 TCP 延迟
    $Lat4 = if ($IPv4) { Test-TcpLatency -IPAddress $IPv4 } else { $null }
    $Lat6 = if ($IPv6) { Test-TcpLatency -IPAddress $IPv6 } else { $null }

    $Results += [PSCustomObject]@{
        Website        = $Site
        IPv4_Address   = if ($IPv4) { $IPv4 } else { "-" }
        IPv4_TCP_ms    = if ($null -ne $Lat4) { $Lat4 } else { "-" }
        IPv6_Address   = if ($IPv6) { $IPv6 } else { "-" }
        IPv6_TCP_ms    = if ($null -ne $Lat6) { $Lat6 } else { "-" }
    }
}

Write-Progress -Activity "TCP 延迟测试" -Completed

# ---------- 输出结果 ----------
Clear-Host
Write-Host "==================== 国内主流网站 TCP 延迟测试结果 ====================" -ForegroundColor Green
$Results | Format-Table -AutoSize -Property Website, IPv4_Address, IPv4_TCP_ms, IPv6_Address, IPv6_TCP_ms

# 可选：导出为 CSV 文件
# $Results | Export-Csv -Path "$PSScriptRoot\TcpLatency_Result.csv" -NoTypeInformation -Encoding UTF8
# Write-Host "结果已导出至: $PSScriptRoot\TcpLatency_Result.csv" -ForegroundColor Yellow