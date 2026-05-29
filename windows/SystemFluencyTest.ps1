<#
.SYNOPSIS
    Windows 11 系统流畅度综合测试工具 (v2.1 修复版)
.DESCRIPTION
    修复多核并行测试 Runspace 错误，并优化应用启动/动画测试。
    需要 PowerShell 7 及管理员权限。
#>

#Requires -Version 7.0
#Requires -RunAsAdministrator

$ErrorActionPreference = 'Stop'
$ProgressPreference = 'SilentlyContinue'

Write-Host "===== 系统流畅度测试工具 v2.1 =====" -ForegroundColor Cyan
Write-Host "检查运行环境..." -ForegroundColor Yellow

if ($PSVersionTable.PSVersion.Major -lt 7) {
    Write-Error "此脚本需要 PowerShell 7 或更高版本。"
    exit 1
}
if (-not ([Security.Principal.WindowsPrincipal] [Security.Principal.WindowsIdentity]::GetCurrent()).IsInRole([Security.Principal.WindowsBuiltInRole] "Administrator")) {
    Write-Error "请以管理员身份运行 PowerShell 7。"
    exit 1
}

# 系统信息
$cpu = Get-CimInstance Win32_Processor | Select-Object -First 1
$os = Get-CimInstance Win32_OperatingSystem
$memory = Get-CimInstance Win32_PhysicalMemory | Measure-Object -Property Capacity -Sum
$totalRAM = [math]::Round($memory.Sum / 1GB, 2)
$disk = Get-PhysicalDisk | Select-Object -First 1

$sysInfo = [PSCustomObject]@{
    OS           = "$($os.Caption) Build $($os.BuildNumber)"
    CPU          = $cpu.Name.Trim()
    Cores        = $cpu.NumberOfCores
    LogicalCores = $cpu.NumberOfLogicalProcessors
    RAM_GB       = $totalRAM
    DiskModel    = $disk.FriendlyName
    DiskType     = $disk.MediaType
    DiskSize_GB  = [math]::Round($disk.Size / 1GB, 2)
}
Write-Host "系统信息:" -ForegroundColor Green
$sysInfo | Format-List

# 辅助函数
function Write-Step { param([string]$m) Write-Host "`n>> $m" -ForegroundColor Cyan }
function Measure-Time { param([scriptblock]$sb) $sw = [Diagnostics.Stopwatch]::StartNew(); & $sb; $sw.Stop(); $sw.Elapsed.TotalSeconds }

# === 1. CPU单核 (递归斐波那契) ===
Write-Step "测试 CPU 单核性能 (递归斐波那契 n=35)..."
$fib = { param($n) if ($n -le 1) { $n } else { (& $fib ($n-1)) + (& $fib ($n-2)) } }
$cpuSingleTime = Measure-Time { & $fib 35 }
if ($cpuSingleTime -gt 60) { $cpuSingleTime = 60 }

# === 2. CPU多核 (ForEach-Object -Parallel 计算 π) ===
Write-Step "测试 CPU 多核性能 (并行计算 Pi，迭代 50,000,000)..."
$logicalCores = [Environment]::ProcessorCount
$totalSteps = 50000000
$step = 1.0 / $totalSteps

$cpuMultiTime = Measure-Time {
    $partialSums = 1..$logicalCores | ForEach-Object -Parallel {
        $coreId = $_
        $chunkSize = [math]::Floor($using:totalSteps / $using:logicalCores)
        $start = ($coreId - 1) * $chunkSize
        $end = if ($coreId -eq $using:logicalCores) { $using:totalSteps } else { $coreId * $chunkSize }

        $sum = 0.0
        for ($i = $start; $i -lt $end; $i++) {
            $x = ($i + 0.5) * $using:step
            $sum += 4.0 / (1.0 + $x * $x)
        }
        $sum
    } -ThrottleLimit $logicalCores

    $pi = ($partialSums | Measure-Object -Sum).Sum * $step
}

# === 3. 内存带宽 ===
Write-Step "测试内存带宽 (读取/写入 2GB 数组)..."
$size = 256MB
$memTime = Measure-Time {
    $array = [double[]]::new($size)
    for ($i = 0; $i -lt $size; $i++) { $array[$i] = $i * 1.0 }
    $sum = 0.0
    for ($i = 0; $i -lt $size; $i++) { $sum += $array[$i] }
    if ($sum -eq 0) { Write-Host " " }
}

# === 4. 磁盘速度 ===
Write-Step "测试磁盘速度 (1GB 临时文件)..."
$tempFile = [IO.Path]::GetTempFileName()
try {
    $fileSize = 1GB
    $buffer = [byte[]]::new(1MB)
    (New-Object Random).NextBytes($buffer)

    $writeTime = Measure-Time {
        $fs = [IO.File]::OpenWrite($tempFile)
        $written = 0L
        while ($written -lt $fileSize) {
            $fs.Write($buffer, 0, $buffer.Length)
            $written += $buffer.Length
        }
        $fs.Close()
    }
    $readTime = Measure-Time {
        $fs = [IO.File]::OpenRead($tempFile)
        $read = 0L
        while ($read -lt $fileSize) {
            $fs.Read($buffer, 0, $buffer.Length) | Out-Null
            $read += $buffer.Length
        }
        $fs.Close()
    }
    $diskWriteMBs = [math]::Round($fileSize / 1MB / $writeTime, 2)
    $diskReadMBs  = [math]::Round($fileSize / 1MB / $readTime, 2)
}
finally {
    Remove-Item $tempFile -Force -ErrorAction SilentlyContinue
}

# === 5. 2D图形 ===
Write-Step "测试 2D 图形性能 (GDI+ 万次绘制)..."
Add-Type -AssemblyName System.Drawing
$gfxTime = Measure-Time {
    $bitmap = [Drawing.Bitmap]::new(1920, 1080)
    $g = [Drawing.Graphics]::FromImage($bitmap)
    $pen = [Drawing.Pen]::new([Drawing.Color]::Red, 2)
    $brush = [Drawing.SolidBrush]::new([Drawing.Color]::Blue)

    for ($i = 0; $i -lt 10000; $i++) {
        $x = Get-Random -Max 1900
        $y = Get-Random -Max 1060
        $g.DrawLine($pen, $x, $y, $x+50, $y+50)
        $g.FillRectangle($brush, $x+10, $y+10, 20, 20)
    }
    $g.Dispose()
    $bitmap.Dispose()
    $pen.Dispose()
    $brush.Dispose()
}

# === 6. 常见应用启动速度 ===
Write-Step "测试常见应用启动速度 (记事本、计算器、画图)..."
$appsToTest = @(
    @{Name='记事本'; Path='notepad.exe'},
    @{Name='计算器'; Path='calc.exe'},
    @{Name='画图';   Path='mspaint.exe'}
)
$startupTimes = @()
foreach ($app in $appsToTest) {
    $fullPath = Join-Path $env:SystemRoot "System32\$($app.Path)"
    if (-not (Test-Path $fullPath)) {
        Write-Host "  [跳过] $($app.Name) 不可用" -ForegroundColor DarkGray
        continue
    }
    try {
        $time = Measure-Time {
            $proc = Start-Process $fullPath -PassThru
            $idle = $proc.WaitForInputIdle(3000)   # 最多等3秒
            if (-not $proc.HasExited) {
                $proc.Kill()
            }
        }
        Write-Host "  $($app.Name) 启动耗时: $([math]::Round($time,2)) 秒" -ForegroundColor Gray
        $startupTimes += $time
    }
    catch {
        Write-Host "  [失败] $($app.Name) 测试异常: $_" -ForegroundColor Red
    }
}
$startupAvg = if ($startupTimes.Count -gt 0) { [math]::Round(($startupTimes | Measure-Object -Average).Average, 2) } else { 10.0 }
Write-Host "  平均启动时间: $startupAvg 秒" -ForegroundColor Gray

# === 7. 动画流畅性 (2D动画仿真) ===
Write-Step "测试动画流畅性 (3秒离屏动画，50个运动图形)..."
$animFPS = 0
$animDuration = 3
$animTime = Measure-Time {
    try {
        $bitmap = [Drawing.Bitmap]::new(1280, 720)
        $g = [Drawing.Graphics]::FromImage($bitmap)
        $g.SmoothingMode = [Drawing.Drawing2D.SmoothingMode]::AntiAlias
        $bgBrush = [Drawing.SolidBrush]::new([Drawing.Color]::White)
        $rand = [Random]::new()

        $objects = 1..50 | ForEach-Object {
            [PSCustomObject]@{
                X = $rand.NextDouble() * 1200
                Y = $rand.NextDouble() * 680
                SpeedX = ($rand.NextDouble() * 200) + 50
                SpeedY = ($rand.NextDouble() * 200) + 50
                Color = [Drawing.Color]::FromArgb($rand.Next(256),$rand.Next(256),$rand.Next(256))
                Size = $rand.Next(20,60)
            }
        }

        $frameCount = 0
        $sw = [Diagnostics.Stopwatch]::StartNew()
        while ($sw.Elapsed.TotalSeconds -lt $animDuration) {
            $g.FillRectangle($bgBrush, 0, 0, 1280, 720)
            foreach ($obj in $objects) {
                $obj.X += $obj.SpeedX * 0.016
                $obj.Y += $obj.SpeedY * 0.016
                if ($obj.X -lt 0 -or $obj.X -gt 1200) { $obj.SpeedX *= -1 }
                if ($obj.Y -lt 0 -or $obj.Y -gt 680)  { $obj.SpeedY *= -1 }

                $pen = [Drawing.Pen]::new($obj.Color, 2)
                $brush = [Drawing.SolidBrush]::new($obj.Color)
                try {
                    $g.DrawEllipse($pen, [int]$obj.X, [int]$obj.Y, $obj.Size, $obj.Size)
                    $g.FillEllipse($brush, [int]$obj.X+2, [int]$obj.Y+2, $obj.Size-4, $obj.Size-4)
                }
                finally {
                    $pen.Dispose()
                    $brush.Dispose()
                }
            }
            $frameCount++
        }
        $sw.Stop()
        $animFPS = [math]::Round($frameCount / $animDuration, 1)
    }
    finally {
        if ($g) { $g.Dispose() }
        if ($bitmap) { $bitmap.Dispose() }
        if ($bgBrush) { $bgBrush.Dispose() }
    }
}
Write-Host "  动画平均帧率: $animFPS FPS (3秒共 $frameCount 帧)" -ForegroundColor Gray

# === 8. 综合负载测试 ===
Write-Step "综合负载测试 (同时 CPU + 磁盘写入)..."
$comprehensiveTime = Measure-Time {
    $job1 = Start-Job -ScriptBlock {
        $sum = 0
        for ($i = 0; $i -lt 50000000; $i++) { $sum += $i % 17 }
    }
    $job2 = Start-Job -ScriptBlock {
        $temp = [IO.Path]::GetTempFileName()
        $fs = [IO.File]::OpenWrite($temp)
        $buf = [byte[]]::new(1MB)
        for ($i=0; $i -lt 512; $i++) { $fs.Write($buf,0,$buf.Length) }
        $fs.Close()
        Remove-Item $temp
    }
    Wait-Job $job1, $job2 | Out-Null
    Receive-Job $job1, $job2 | Out-Null
    Remove-Job $job1, $job2
}

# ========== 评分计算 (9项) ==========
Write-Step "计算流畅度评分..."

$scoreCPU       = if ($cpuSingleTime -lt 2) {100} elseif ($cpuSingleTime -lt 4) {80} elseif ($cpuSingleTime -lt 8) {60} elseif ($cpuSingleTime -lt 15) {40} else {20}
$scoreMulti     = if ($cpuMultiTime -lt 1.5) {100} elseif ($cpuMultiTime -lt 3) {80} elseif ($cpuMultiTime -lt 6) {60} elseif ($cpuMultiTime -lt 10) {40} else {20}
$scoreMem       = if ($memTime -lt 1.0) {100} elseif ($memTime -lt 2.0) {80} elseif ($memTime -lt 3.5) {60} elseif ($memTime -lt 6) {40} else {20}
$scoreDiskWrite = if ($diskWriteMBs -gt 1500) {100} elseif ($diskWriteMBs -gt 800) {80} elseif ($diskWriteMBs -gt 400) {60} elseif ($diskWriteMBs -gt 150) {40} else {20}
$scoreDiskRead  = if ($diskReadMBs -gt 2000) {100} elseif ($diskReadMBs -gt 1200) {80} elseif ($diskReadMBs -gt 600) {60} elseif ($diskReadMBs -gt 200) {40} else {20}
$scoreGfx       = if ($gfxTime -lt 2) {100} elseif ($gfxTime -lt 4) {80} elseif ($gfxTime -lt 8) {60} elseif ($gfxTime -lt 15) {40} else {20}
$scoreStartup   = if ($startupAvg -lt 1.0) {100} elseif ($startupAvg -lt 2.0) {80} elseif ($startupAvg -lt 4.0) {60} elseif ($startupAvg -lt 8.0) {40} else {20}
$scoreAnim      = if ($animFPS -gt 120) {100} elseif ($animFPS -gt 90) {80} elseif ($animFPS -gt 60) {60} elseif ($animFPS -gt 30) {40} else {20}
$scoreCompr     = if ($comprehensiveTime -lt 4) {100} elseif ($comprehensiveTime -lt 8) {80} elseif ($comprehensiveTime -lt 15) {60} elseif ($comprehensiveTime -lt 25) {40} else {20}

$totalScore = [math]::Round(
    ($scoreCPU + $scoreMulti + $scoreMem + $scoreDiskWrite + $scoreDiskRead + $scoreGfx + $scoreStartup + $scoreAnim + $scoreCompr) / 9, 0
)

if ($totalScore -ge 85) { $level = "优秀"; $color = "Green" }
elseif ($totalScore -ge 65) { $level = "良好"; $color = "Yellow" }
elseif ($totalScore -ge 45) { $level = "一般"; $color = "DarkYellow" }
else { $level = "较差"; $color = "Red" }

# 汇总
$results = [PSCustomObject]@{
    SystemInfo         = $sysInfo
    CPUSingle_sec      = [math]::Round($cpuSingleTime, 2)
    CPUMulti_sec       = [math]::Round($cpuMultiTime, 2)
    MemoryBandwidth_sec = [math]::Round($memTime, 2)
    DiskWrite_MBps     = $diskWriteMBs
    DiskRead_MBps      = $diskReadMBs
    Graphics2D_sec     = [math]::Round($gfxTime, 2)
    AppStartupAvg_sec  = $startupAvg
    Animation_FPS      = $animFPS
    Comprehensive_sec  = [math]::Round($comprehensiveTime, 2)
    TotalScore         = $totalScore
    FluencyLevel       = $level
}

Write-Host "`n========== 流畅度测试结果 ==========" -ForegroundColor Cyan
$results | Format-List
Write-Host "系统流畅度评分: $totalScore / 100  ($level)" -ForegroundColor $color

$reportPath = Join-Path $PWD "FluencyReport_$(Get-Date -Format 'yyyyMMdd_HHmmss').json"
$results | ConvertTo-Json -Depth 4 | Out-File $reportPath -Encoding utf8
Write-Host "`n详细报告已保存至: $reportPath" -ForegroundColor Magenta