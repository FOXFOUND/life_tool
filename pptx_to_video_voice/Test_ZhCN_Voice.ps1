<#
Edge-TTS 大陆普通话音色试听 - 稳定修复版（自动重试+跳过失败）
#>

# 大陆简体普通话 zh-CN 全音色（去掉了不稳定/已废弃的）
$VoiceList = @(
    # 女声
    "zh-CN-XiaoxiaoNeural",  # 晓晓
    "zh-CN-XiaoyiNeural",    # 晓伊
    "zh-CN-XiaohanNeural",   # 晓涵
    "zh-CN-XiaomoNeural",    # 晓墨
    "zh-CN-XiaoruiNeural",   # 晓睿
    "zh-CN-XiaoxuanNeural",  # 晓萱
    "zh-CN-XiaoningNeural",  # 晓宁

    # 男声
    "zh-CN-YunxiNeural",     # 云希
    "zh-CN-YunyangNeural",   # 云扬
    "zh-CN-YunjianNeural",   # 云健
    "zh-CN-YunxiaNeural",    # 云夏
    "zh-CN-YunzeNeural"     # 云泽
)

# 测试文案
$TestText = "大家好，这是Edge-TTS普通话音色试听，体验AI人声质感。"

# 创建输出目录
$OutDir = Join-Path $PWD.Path "zh-CN_稳定试听"
if (-not (Test-Path $OutDir)) { New-Item -ItemType Directory -Path $OutDir | Out-Null }

Write-Host "===== 开始生成稳定版zh-CN音色试听 =====" -ForegroundColor Cyan

# 循环生成（带重试+跳过）
foreach ($voice in $VoiceList) {
    $mp3 = Join-Path $OutDir "$voice.mp3"
    Write-Host "`n正在生成：$voice" -ForegroundColor Yellow

    # 最多重试2次，避免卡死
    $success = $false
    for ($i = 0; $i -lt 2; $i++) {
        try {
            edge-tts --voice $voice --text $TestText --write-media $mp3 2>&1 | Out-Null
            if (Test-Path $mp3) { $success = $true; break }
        }
        catch { }
        Start-Sleep 1
    }

    if ($success) {
        Write-Host "✅ 成功：$voice" -ForegroundColor Green
    }
    else {
        Write-Host "❌ 跳过：$voice（接口不稳定）" -ForegroundColor Red
    }
}

Write-Host "`n===== 全部完成！音频目录：$OutDir =====" -ForegroundColor Cyan
Start-Process $OutDir