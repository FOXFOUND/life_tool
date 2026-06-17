#!/usr/bin/env python3
# -*- coding: utf-8 -*-

import matplotlib.pyplot as plt
import numpy as np
import re
import sys

def parse_line(line):
    """
    解析单行数据，返回字典 {t, A, L, C, O, Stress}
    若解析失败，返回 None
    """
    # 去除首尾空白
    line = line.strip()
    if not line:
        return None

    try:
        # 分割各部分（按空格），但 A 数组中可能包含空格，需要特殊处理
        # 示例: "t=0.0   A:[0.60 0.85 0.65 0.70 0.20 0.15] L=4.22 C=3.47 O=0.95 Stress=0.0"
        # 先按 'A:[' 分割，得到前后部分
        parts = line.split('A:[')
        if len(parts) != 2:
            return None
        # 前半部分包含 t
        before = parts[0].strip()
        t_match = re.search(r't=([\d.]+)', before)
        if not t_match:
            return None
        t = float(t_match.group(1))

        # 后半部分包含数组和后面的变量
        after = parts[1]
        # 分离数组部分和后面部分
        # 数组以 ']' 结束
        bracket_end = after.find(']')
        if bracket_end == -1:
            return None
        array_str = after[:bracket_end].strip()
        # 解析数组元素
        a_values = [float(x) for x in array_str.split()]

        # 剩余部分
        rest = after[bracket_end+1:].strip()
        # 提取 L, C, O, Stress
        l_match = re.search(r'L=([\d.]+)', rest)
        c_match = re.search(r'C=([\d.]+)', rest)
        o_match = re.search(r'O=([\d.]+)', rest)
        s_match = re.search(r'Stress=([\d.]+)', rest)

        if not (l_match and c_match and o_match and s_match):
            return None

        L = float(l_match.group(1))
        C = float(c_match.group(1))
        O = float(o_match.group(1))
        Stress = float(s_match.group(1))

        return {
            't': t,
            'A': a_values,
            'L': L,
            'C': C,
            'O': O,
            'Stress': Stress
        }
    except Exception:
        return None

def read_data(filename):
    """
    读取整个文件，返回两个列表：t_list, stress_list
    同时可返回其他变量（L, C, O）以供扩展
    """
    t_list = []
    stress_list = []
    L_list = []
    C_list = []
    O_list = []

    with open(filename, 'r', encoding='utf-8') as f:
        for line in f:
            data = parse_line(line)
            if data is not None:
                t_list.append(data['t'])
                stress_list.append(data['Stress'])
                L_list.append(data['L'])
                C_list.append(data['C'])
                O_list.append(data['O'])
            # 忽略解析失败的行

    return t_list, stress_list, L_list, C_list, O_list

def plot_data(t, stress, L=None, C=None, O=None, save_name='stress_plot.png'):
    """
    绘制图形：左侧线性坐标，右侧对数坐标（应力）
    若提供 L,C,O，则在左侧子图中额外绘制（但数值较小，可单独展示）
    """
    fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(14, 5))

    # --- 左图：线性坐标 ---
    ax1.plot(t, stress, 'bo-', markersize=4, linewidth=1.5, label='Stress')
    ax1.set_xlabel('Time t')
    ax1.set_ylabel('Stress')
    ax1.set_title('Linear Scale')
    ax1.grid(True, linestyle='--', alpha=0.6)
    # 如果 L 等数据可用且非恒值，可叠加显示（但本例中它们很快恒定，故注释）
    # if L is not None:
    #     ax1.plot(t, L, 'g--', label='L')
    #     ax1.plot(t, C, 'r--', label='C')
    #     ax1.plot(t, O, 'm--', label='O')
    #     ax1.legend()

    # --- 右图：对数纵轴 ---
    ax2.semilogy(t, stress, 'ro-', markersize=4, linewidth=1.5, label='Stress')
    ax2.set_xlabel('Time t')
    ax2.set_ylabel('Stress (log scale)')
    ax2.set_title('Logarithmic Scale (base 10)')
    ax2.grid(True, linestyle='--', alpha=0.6, which='both')
    ax2.legend()

    plt.tight_layout()
    plt.savefig(save_name, dpi=300)
    plt.show()
    print(f"图表已保存为 {save_name}")

def main():
    # 请修改为您的实际文件路径
    if len(sys.argv) > 1:
        filename = sys.argv[1]
    else:
        filename = 'data.txt'   # 默认文件名，可改为您的文件

    try:
        t, stress, L, C, O = read_data(filename)
    except FileNotFoundError:
        print(f"错误：文件 '{filename}' 未找到，请检查路径。")
        return
    except Exception as e:
        print(f"读取文件时出错: {e}")
        return

    if not t:
        print("警告：未能从文件中解析出任何有效数据，请检查文件格式。")
        return

    print(f"成功读取 {len(t)} 条记录。")
    plot_data(t, stress, L, C, O)

if __name__ == '__main__':
    main()