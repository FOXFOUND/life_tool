import numpy as np
import matplotlib.pyplot as plt

# ==================== 原始数据 ====================
years = np.array([
    1993.6, 2002.5, 2011.4, 2019.0, 2022.4,
    2028.5, 2035.3, 2043.2, 2052.4, 2063.4, 2096.5
])
stresses = np.array([
    1000, 10000, 100000, 500000, 1e6,
    1e7, 1e8, 1e9, 1e10, 1e11, 159575032944.7
])

# ==================== 二次拟合参数（基准年份 2052.4） ====================
# 模型：S(t) = A*(t - 2052.4)^2 + B*(t - 2052.4) + C
# 基于 (2052.4, 1e10), (2063.4, 1e11), (2096.5, 1.59575e11) 三点求解
A = -1.44715e8    # 二次项系数（负值，开口向下）
B = 9.7736e9      # 一次项系数
C = 1.0e10        # 常数项（即 2052.4 年的值）
base_year = 2052.4

def stress_fit(year):
    """拟合函数：输入年份，返回对应的 Stress 预测值"""
    t = year - base_year
    return A * t**2 + B * t + C

# ==================== 计算极限峰值 ====================
peak_t = -B / (2 * A)                      # 极值点对应的 t 偏移量
peak_year = base_year + peak_t             # 极值点年份 ≈ 2086.2
peak_stress = C - B**2 / (4 * A)           # 峰值 Stress ≈ 1.75e11

print(f"【推算结果】")
print(f"社会极限峰值 (Stress max): {peak_stress:.3e}")
print(f"达到峰值的年份: {peak_year:.3f}")

# ==================== 生成绘图数据 ====================
# 绘制范围从 1990 到 2120，但拟合曲线在 2025 年之前为负值（对数无法显示），用 nan 屏蔽
year_plot = np.linspace(1990, 2120, 800)
stress_plot = stress_fit(year_plot)
stress_plot[stress_plot <= 0] = np.nan      # 规避对数坐标中负值报错

# ==================== 开始绘图 ====================
plt.figure(figsize=(14, 8))

# 1. 原始数据散点图（红色）
plt.scatter(years, stresses, color='darkred', s=80,
            label='原始数据', zorder=5, edgecolors='black')

# 2. 二次拟合外推曲线（蓝色）
plt.plot(year_plot, stress_plot, 'b-', linewidth=2.5,
         label='二次拟合曲线 (外推模型)', zorder=3)

# 3. 峰值极限点（绿色五角星）
plt.scatter(peak_year, peak_stress, color='limegreen', marker='*',
            s=350, label=f'社会极限峰值\n{peak_stress:.2e} (≈{peak_year:.1f}年)',
            zorder=10, edgecolors='darkgreen', linewidth=1.5)

# 4. 峰值年份参考虚线
plt.axvline(x=peak_year, color='gray', linestyle='--', alpha=0.7, linewidth=1.5)

# ==================== 图表修饰 ====================
plt.yscale('log')                          # 使用对数坐标，适配大跨度数据
plt.xlim(1990, 2115)
plt.ylim(5e2, 1e12)                        # 留出上下空间

plt.xlabel('年份', fontsize=13)
plt.ylabel('Stress 水平 (对数坐标)', fontsize=13)
plt.title('Stress 水平增长趋势与社会极限预测 (二次峰值模型)', fontsize=16, pad=15)
plt.grid(True, which='both', linestyle='--', alpha=0.4, linewidth=0.8)
plt.legend(loc='upper left', fontsize=11)

# 美观：Y轴显示科学计数法
from matplotlib.ticker import LogFormatterSciNotation
plt.gca().yaxis.set_major_formatter(LogFormatterSciNotation())

plt.tight_layout()
plt.show()