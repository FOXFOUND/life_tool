# ===================== 只需要改这里 =====================
$folderPath = "Z:\1"  # 你的目标文件夹
# =======================================================

# 获取所有文件，按最后访问时间从远到近排序
$files = Get-ChildItem -Path $folderPath -File | Sort-Object -Property LastAccessTime

if ($files.Count -eq 0) {
    Write-Warning "文件夹内没有文件"
    return
}

# 预览
Write-Host "`n===== 即将重命名 =====" -ForegroundColor Cyan
$files | ForEach-Object -Begin { $i = 1 } {
    Write-Host "[$i] $($_.Name) → $i$($_.Extension)"
    $i++
}

# 确认
$confirm = Read-Host "确认？Y/N"
if ($confirm -notmatch '^[Yy]$') {
    Write-Host "已取消" -ForegroundColor Yellow
    return
}

# 重命名
$files | ForEach-Object -Begin { $index = 1 } {
    $newName = Join-Path -Path $folderPath -ChildPath "$index$($_.Extension)"
    Rename-Item -Path $_.FullName -NewName $newName -Force
    $index++
}

Write-Host "`n✅ 重命名完成！" -ForegroundColor Green