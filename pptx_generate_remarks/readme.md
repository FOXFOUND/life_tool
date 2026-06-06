```markdown
# PPT 备注自动生成与添加工具

本工具链用于从 PowerPoint 文件（.pptx）中提取每页文本内容，借助 AI 生成专业备注解说词，然后将解说词自动写回 PPT 的备注栏中。  
适合制作带有完整讲解脚本的演示文稿，尤其适用于学术报告、法律分析、社会批判等深度内容。

## 整体流程

1. **提取 PPT 文本** → 生成 `ppt_content.md`
2. **使用 AI 生成备注** → 得到 `remarks.txt`（每页解说词）
3. **将备注写入 PPT** → 得到带备注的新 `.pptx` 文件

---

## 环境准备

### 安装 Python 依赖

```bash
pip install python-pptx
```

> 本工具使用 `python-pptx` 库读写 PPTX 文件。

### 获取脚本文件

确保以下两个脚本与您的 PPT 文件放在同一目录下（或任意路径）：

- `extract_ppt.py` – 提取 PPT 文本内容为 Markdown
- `add_notes_to_pptx.py` – 将备注文本写入 PPT

---

## 步骤一：提取 PPT 内容

```bash
python extract_ppt.py 你的演示文稿.pptx
```

- **参数**：第一个参数为 PPTX 文件路径
- **输出**：在当前目录生成 `ppt_content.md`，包含每页的标题和要点列表。

> 如果 PPT 中有表格、组合形状等，脚本会递归提取所有文本。

---

## 步骤二：使用 AI 生成备注解说词

### 准备材料

1. **`ppt_content.md`**（上一步生成的 PPT 内容）
2. **分析报告**（例如名为 `1.md` 的深度分析文本，内容包含案例、法律概念、文化批判等）

### 编写提示词（Prompt）

将以下提示词与两份材料一同提交给 AI（如 ChatGPT、Claude、DeepSeek 等）：

```
你是一位擅长社会批判与法律分析的学术报告撰写者。
我将给你两份材料：

- ppt_content.md：一份PPT的文本内容，共25页，每页包含标题和要点。

请你完成以下任务：
为 ppt_content.md 中的每一页PPT生成一段详细的备注解说词。要求：
- 每页对应一段解说词，输出格式为纯文本，每页一段，段首标注“第X页：”。
- 解说词不能空洞、套话，要紧密结合 1.md 中的分析内容，充实具体案例、法律概念、文化批判等。
- 上下页之间要保持逻辑连贯，形成完整的报告讲述流程。
- 语言风格：80%专业分析 + 20%口语化解读，既有学术严谨性，又具有可读性和感染力。

请开始生成。
```

### 获取备注文件

将 AI 返回的结果保存为 **`remarks.txt`**，格式示例：

```
第1页：大家好，今天我们讨论的主题是...（详细解说）
第2页：上一页我们提出了问题，这一页将具体分...
...
```

> ⚠️ 注意：文件编码必须为 UTF-8，每行格式严格为 `第X页：解说内容`（支持中文冒号或英文冒号）。

---

## 步骤三：将备注写入 PPT

```bash
python add_notes_to_pptx.py 你的演示文稿.pptx remarks.txt 输出带备注的文稿.pptx
```

- **参数1**：原始 PPTX 路径
- **参数2**：备注文本文件路径（`remarks.txt`）
- **参数3**（可选）：输出文件路径。若不指定，默认在原文件同目录下生成 `原文件名_with_notes.pptx`

执行后，脚本会：
- 解析备注文件，按页码匹配幻灯片
- 将备注内容写入每页的备注栏（Notes）
- 保存为新文件

---

## 完整示例

假设当前目录有：

```
extract_ppt.py
add_notes_to_pptx.py
1.pptx
1.md
```

**执行流程：**

```bash
# 1. 提取 PPT 内容
python extract_ppt.py 1.pptx
# 生成 ppt_content.md

# 2. （手动）将 ppt_content.md 和 1.md 提供给 AI，得到 remarks.txt

# 3. 写入备注
python add_notes_to_pptx.py 1.pptx remarks.txt 1_带备注.pptx
```

最终得到 `1_带备注.pptx`，在 PowerPoint 中打开，点击每页下方的备注栏即可看到生成的解说词。

---

## 常见问题

### Q1：提取 PPT 内容时遇到 “No module named 'pptx'”
**A**：请先安装 `python-pptx`：`pip install python-pptx`

### Q2：备注文件中的页码与 PPT 实际页数不一致怎么办？
**A**：脚本会忽略超出实际页数的备注，缺失的页码留空。建议核对 PPT 页数后调整备注文件。

### Q3：AI 生成的内容太长，备注栏放不下？
**A**：PowerPoint 备注栏理论上无严格字符限制，但建议每页解说词控制在 500 字以内，以保证阅读舒适度。

### Q4：能否批量处理多个 PPT？
**A**：可以写简单的 shell 循环，但需要为每个 PPT 单独生成对应的 `remarks.txt`。

---

## 文件说明

| 文件 | 作用 |
|------|------|
| `extract_ppt.py` | 提取 PPT 文本为 Markdown |
| `add_notes_to_pptx.py` | 将备注文本写入 PPT |
| `ppt_content.md` | 中间文件，供 AI 理解 PPT 结构 |
| `remarks.txt` | AI 生成的解说词，按页编号 |
| `*_with_notes.pptx` | 最终带备注的演示文稿 |

---

## 许可证

本工具脚本遵循 MIT 协议，可自由修改和分发。
```