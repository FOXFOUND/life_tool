$cutoff = (Get-Date).AddMonths(-6)
$base = "C:\Users\xxx\AppData\Roaming"
$result = @()
foreach ($sub in Get-ChildItem $base -Directory -ErrorAction SilentlyContinue) {
    $files = Get-ChildItem $sub.FullName -Recurse -File -ErrorAction SilentlyContinue
    if ($files.Count -eq 0) { continue }
    $hasRecent = $false
    foreach ($f in $files) {
        if ($f.LastAccessTime -ge $cutoff) {
            $hasRecent = $true
            break
        }
    }
    if (-not $hasRecent) { $result += $sub.FullName }
}
$result