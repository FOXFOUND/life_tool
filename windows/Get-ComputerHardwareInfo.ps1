<#
.SYNOPSIS
    获取电脑硬件及软件配置信息（CPU、内存、显卡、主板、磁盘、网卡、OS版本、BIOS版本等）
.DESCRIPTION
    脚本通过WMI/CIM查询系统信息，生成格式清晰的报告，仅输出到控制台，便于直接查看或分享。
.EXAMPLE
    .\Get-SystemInfo.ps1
.NOTES
    要求：PowerShell 7 或 Windows PowerShell 5.1（建议以管理员身份运行以获得完整信息）
#>

# 停止脚本执行中的错误（部分信息可能因权限无法获取，但不中断）
$ErrorActionPreference = "Continue"

# 获取计算机名和时间戳
$computerName = $env:COMPUTERNAME
$currentTime = Get-Date -Format "yyyy-MM-dd HH:mm:ss"

# 初始化报告内容
$report = @()
$report += "=" * 80
$report += " 系统配置报告"
$report += " 计算机名: $computerName"
$report += " 生成时间: $currentTime"
$report += "=" * 80

# 辅助函数：将字节转换为GB（保留2位小数）
function Convert-BytesToGB {
    param([long]$Bytes)
    if ($Bytes -and $Bytes -gt 0) {
        return [math]::Round($Bytes / 1GB, 2)
    }
    return $null
}

# ========== 1. 处理器 (CPU) ==========
$report += "`n[1] 处理器信息"
$cpu = Get-CimInstance -ClassName Win32_Processor | Select-Object -First 1
if ($cpu) {
    $report += "  名称: $($cpu.Name)"
    $report += "  核心数: $($cpu.NumberOfCores)"
    $report += "  逻辑处理器数: $($cpu.NumberOfLogicalProcessors)"
    $report += "  最大时钟频率: $($cpu.MaxClockSpeed) MHz"
} else {
    $report += "  无法获取CPU信息"
}

# ========== 2. 内存 (RAM) ==========
$report += "`n[2] 内存信息"
$physicalMemory = Get-CimInstance -ClassName Win32_PhysicalMemory
if ($physicalMemory) {
    $totalBytes = ($physicalMemory | Measure-Object -Property Capacity -Sum).Sum
    $totalGB = Convert-BytesToGB -Bytes $totalBytes
    $report += "  总物理内存: $totalGB GB"
    $report += "  内存条数量: $($physicalMemory.Count)"
    $report += "  详细条信息:"
    foreach ($mem in $physicalMemory) {
        $capGB = Convert-BytesToGB -Bytes $mem.Capacity
        $report += "    - 容量: $capGB GB, 速度: $($mem.Speed) MHz, 制造商: $($mem.Manufacturer)"
    }
} else {
    $report += "  无法获取内存信息"
}

# ========== 3. 显卡 (GPU) ==========
$report += "`n[3] 显卡信息"
$gpus = Get-CimInstance -ClassName Win32_VideoController | Where-Object { $_.Name -notlike "*Remote*" -and $_.Name -notlike "*Mirror*" }
if ($gpus) {
    foreach ($gpu in $gpus) {
        $vramGB = Convert-BytesToGB -Bytes $gpu.AdapterRAM
        $report += "  名称: $($gpu.Name)"
        $report += "  驱动版本: $($gpu.DriverVersion)"
        $report += "  显存: $(if ($vramGB) { "$vramGB GB" } else { "未知" })"
        $report += "  ---"
    }
} else {
    $report += "  无法获取显卡信息（或未找到物理显卡）"
}

# ========== 4. 主板 ==========
$report += "`n[4] 主板信息"
$board = Get-CimInstance -ClassName Win32_Baseboard | Select-Object -First 1
if ($board) {
    $report += "  制造商: $($board.Manufacturer)"
    $report += "  型号: $($board.Product)"
    $report += "  序列号: $($board.SerialNumber)"
} else {
    $report += "  无法获取主板信息"
}

# ========== 5. 磁盘信息 ==========
$report += "`n[5] 物理磁盘信息"
$disks = Get-CimInstance -ClassName Win32_DiskDrive
if ($disks) {
    foreach ($disk in $disks) {
        $sizeGB = Convert-BytesToGB -Bytes $disk.Size
        $report += "  型号: $($disk.Model)"
        $report += "  接口类型: $($disk.InterfaceType)"
        $report += "  总容量: $sizeGB GB"
        $report += "  分区样式: $($disk.PartitionStyle)"
        $report += "  ---"
    }
} else {
    $report += "  无法获取磁盘信息"
}

# ========== 6. 网络适配器（物理网卡） ==========
$report += "`n[6] 物理网络适配器"
$adapters = Get-CimInstance -ClassName Win32_NetworkAdapter | Where-Object { $_.PhysicalAdapter -eq $true -and $_.NetEnabled -eq $true }
if ($adapters) {
    foreach ($adapter in $adapters) {
        $speedMbps = if ($adapter.Speed) { [math]::Round($adapter.Speed / 1e6, 0) } else { "未知" }
        $report += "  名称: $($adapter.Name)"
        $report += "  MAC地址: $($adapter.MACAddress)"
        $report += "  链接速度: $speedMbps Mbps"
        $report += "  状态: $($adapter.NetEnabled)"
        $report += "  ---"
    }
} else {
    $report += "  未找到已启用的物理网卡"
}

# ========== 7. 软件信息（操作系统、BIOS、PowerShell版本等） ==========
$report += "`n[7] 软件及系统版本信息"

# 操作系统信息
$os = Get-CimInstance -ClassName Win32_OperatingSystem
if ($os) {
    $report += "  操作系统名称: $($os.Caption)"
    $report += "  版本: $($os.Version)"
    $report += "  Build 号: $($os.BuildNumber)"
    $report += "  系统目录: $($os.SystemDirectory)"
    $report += "  上次启动时间: $($os.LastBootUpTime)"
} else {
    $report += "  无法获取操作系统信息"
}

# 详细 Windows 版本（如 22H2）
$winRegPath = "HKLM:\SOFTWARE\Microsoft\Windows NT\CurrentVersion"
if (Test-Path $winRegPath) {
    $winReg = Get-ItemProperty -Path $winRegPath
    $displayVersion = $winReg.DisplayVersion   # 例如 22H2
    $releaseId = $winReg.ReleaseId             # 较早版本的 ReleaseId
    $edition = $winReg.EditionName
    if ($displayVersion) {
        $report += "  Windows 版本: $displayVersion"
    } elseif ($releaseId) {
        $report += "  Windows 版本: $releaseId"
    }
    if ($edition) {
        $report += "  系统版本: $edition"
    }
}

# BIOS 信息
$bios = Get-CimInstance -ClassName Win32_BIOS
if ($bios) {
    $report += "  BIOS 厂商: $($bios.Manufacturer)"
    $report += "  BIOS 版本: $($bios.SMBIOSBIOSVersion)"
    $report += "  BIOS 日期: $($bios.ReleaseDate)"
} else {
    $report += "  无法获取BIOS信息"
}

# PowerShell 版本
$psVersion = $PSVersionTable.PSVersion.ToString()
$report += "  PowerShell 版本: $psVersion"

# .NET 运行时版本（如果有）
$dotnetVersion = [System.Runtime.InteropServices.RuntimeInformation]::FrameworkDescription
if ($dotnetVersion) {
    $report += "  .NET 运行时: $dotnetVersion"
} else {
    $report += "  .NET 运行时: 无法获取"
}

# 报告结尾
$report += "`n" + "=" * 80
$report += "报告生成完毕（仅显示于控制台）"
$report += "=" * 80

# 输出到控制台
$report | Write-Output