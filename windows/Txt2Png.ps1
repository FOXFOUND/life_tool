<#
.SYNOPSIS
将文本文件（.txt）转换为 PNG 图片。

.DESCRIPTION
支持自定义字体、字号、前景色、背景色和输出路径。
纯 Windows PowerShell 7 实现，依赖 WPF（System.Windows.Controls.TextBlock）。

.PARAMETER TxtFilePath
必选，要转换的文本文件路径。

.PARAMETER OutFile
可选，输出的 PNG 文件路径。默认与 txt 文件同名但扩展名改为 .png。

.PARAMETER Font
可选，字体名称（需系统已安装），如 '微软雅黑', 'SimHei', 'Consolas'。默认 '微软雅黑'。

.PARAMETER FontSize
可选，字号大小（整数），默认 24。

.PARAMETER Foreground
可选，文字颜色（支持颜色名称或 #RRGGBB），默认 'Black'。

.PARAMETER Background
可选，背景颜色，默认 'White'。

.PARAMETER Width
可选，图片宽度（像素），文字将在此宽度内自动换行。默认 800。

.EXAMPLE
.\Txt2Png.ps1 -TxtFilePath .\readme.txt

.EXAMPLE
.\Txt2Png.ps1 -TxtFilePath .\log.txt -OutFile .\output.png -Font 'Consolas' -FontSize 16 -Foreground 'Green' -Background 'Black' -Width 1200
#>

# ========== 参数定义必须放在脚本最顶部（非注释行） ==========
param(
    [Parameter(Mandatory)]
    [string]$TxtFilePath,

    [string]$OutFile,

    [string]$Font = '微软雅黑',

    [int]$FontSize = 24,

    [string]$Foreground = 'Black',

    [string]$Background = 'White',

    [int]$Width = 800
)

# ========== 函数定义 ==========
function Convert-TextToPng {
    param(
        [Parameter(Mandatory)]
        [string]$Text,

        [string]$Font = '微软雅黑',
        [int]$FontSize = 24,
        [string]$Foreground = 'Black',
        [string]$Background = 'White',
        [int]$Width = 800,

        [string]$OutFile = "output_$(Get-Random).png"
    )

    # 加载 WPF 程序集
    Add-Type -AssemblyName PresentationFramework -ErrorAction Stop

    # 转义 XML 特殊字符（避免 XAML 解析错误）
    $escapedText = [System.Security.SecurityElement]::Escape($Text)

    $xaml = @"
<TextBlock xmlns="http://schemas.microsoft.com/winfx/2006/xaml/presentation"
           FontFamily="$Font" FontSize="$FontSize"
           Foreground="$Foreground" Background="$Background"
           TextWrapping="Wrap" Width="$Width">
    $escapedText
</TextBlock>
"@

    try {
        $reader = [System.Xml.XmlReader]::Create([System.IO.StringReader]$xaml)
        $textBlock = [System.Windows.Markup.XamlReader]::Load($reader)
        $reader.Close()

        $textBlock.Measure([System.Windows.Size]::new($Width, [double]::PositiveInfinity))
        $textBlock.Arrange([System.Windows.Rect]::new($textBlock.DesiredSize))
        $textBlock.UpdateLayout()

        $actualWidth = [math]::Min($Width, $textBlock.ActualWidth)
        $actualHeight = $textBlock.ActualHeight

        if ($actualHeight -eq 0) {
            throw "渲染高度为 0，请检查文本内容是否为空。"
        }

        $renderTarget = [System.Windows.Media.Imaging.RenderTargetBitmap]::new(
            [int]$actualWidth,
            [int]$actualHeight,
            96, 96,
            [System.Windows.Media.PixelFormats]::Default
        )
        $renderTarget.Render($textBlock)

        $encoder = [System.Windows.Media.Imaging.PngBitmapEncoder]::new()
        $encoder.Frames.Add([System.Windows.Media.Imaging.BitmapFrame]::Create($renderTarget))

        $fileStream = [System.IO.FileStream]::new($OutFile, [System.IO.FileMode]::Create)
        $encoder.Save($fileStream)
        $fileStream.Close()

        Write-Host "✅ 图片已生成: $OutFile (尺寸: ${actualWidth}x${actualHeight})"
    }
    catch {
        Write-Error "转换失败: $_"
        if ($reader) { $reader.Close() }
    }
}

function Convert-TxtFileToPng {
    param(
        [Parameter(Mandatory)]
        [string]$TxtFilePath,

        [string]$OutFile,
        [string]$Font = '微软雅黑',
        [int]$FontSize = 24,
        [string]$Foreground = 'Black',
        [string]$Background = 'White',
        [int]$Width = 800
    )

    if (-not (Test-Path -LiteralPath $TxtFilePath)) {
        Write-Error "文件不存在: $TxtFilePath"
        return
    }

    $text = Get-Content -Path $TxtFilePath -Raw -Encoding UTF8

    if ([string]::IsNullOrWhiteSpace($text)) {
        Write-Error "文件内容为空: $TxtFilePath"
        return
    }

    if (-not $OutFile) {
        $OutFile = [System.IO.Path]::ChangeExtension($TxtFilePath, '.png')
    }

    Convert-TextToPng -Text $text -OutFile $OutFile -Font $Font -FontSize $FontSize `
                      -Foreground $Foreground -Background $Background -Width $Width
}

# ========== 脚本执行入口：当直接运行脚本时（非点号导入）调用转换函数 ==========
if ($MyInvocation.InvocationName -ne '.') {
    Convert-TxtFileToPng -TxtFilePath $TxtFilePath -OutFile $OutFile -Font $Font `
                         -FontSize $FontSize -Foreground $Foreground -Background $Background -Width $Width
}