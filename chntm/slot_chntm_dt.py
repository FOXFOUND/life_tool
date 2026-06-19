import numpy as np
import matplotlib.pyplot as plt

# 中文字体
plt.rcParams['font.sans-serif'] = ['SimHei', 'Microsoft YaHei', 'WenQuanYi Micro Hei']
plt.rcParams['axes.unicode_minus'] = False

# ========== 原始数据 ==========
t = np.array([2, 4, 6, 8, 10, 12, 14, 16, 18, 20])
dt = 2.0  # 时间步长

secure = {
    'E': [416.3683, 289.4455, 354.8378, 445.9031, 548.4438,
          658.1043, 772.5836, 891.7557, 1014.9112, 1141.0146],
    'R': [11941.0123, 98705.0090, 166910.9170, 228578.4504, 286294.1805,
          341221.2011, 394016.0767, 445097.2127, 494753.4015, 543195.3986],
    'M': [476.8914, 305.1741, 361.5674, 450.0339, 552.2343,
          662.5679, 778.7250, 899.6073, 1024.9512, 1154.2769],
    'S': [43.4979, 107.3950, 170.2838, 235.5465, 304.4700,
          376.9422, 453.2162, 533.7078, 617.8251, 706.0908],
    'A': [11936.5333, 98665.6975, 166843.7020, 228485.5887, 286176.5354,
          341079.4474, 393850.7976, 444909.0256, 494543.5794, 542964.5759]
}

insecure = {
    'E': [-3.3734, 4.4163, 3.8007, 8.7088, 25.0491,
          31.9836, 32.8838, 47.3346, 59.6829, 77.0195],
    'R': [-3.6497, -0.4226, -1.8675, -8.2147, -17.4741,
          -29.0029, -42.7550, -59.1395, -78.2922, -99.9574],
    'M': [-3.6228, 6.3800, 7.6115, 6.2687, 3.5838,
          -5.0598, -18.7623, -37.0940, -59.5617, -86.2431],
    'S': [-3.8993, 1.7468, -0.6794, -11.3176, -27.3413,
          -48.1287, -73.4949, -102.9554, -137.0005, -174.9973],
    'A': [-3.6521, 2.7487, 3.4519, 8.8240, 22.0234,
          29.0117, 32.5589, 45.9978, 58.7656, 76.2050]
}

# ========== 离散导数函数 (中心差分 + 端点单侧差分) ==========
def discrete_derivative(t, y):
    """计算离散导数 dy/dt，均匀步长"""
    dydt = np.zeros_like(y)
    h = t[1] - t[0]  # 步长
    # 内部点：中心差分
    for i in range(1, len(t)-1):
        dydt[i] = (y[i+1] - y[i-1]) / (2 * h)
    # 端点：向前/向后差分
    dydt[0] = (y[1] - y[0]) / h
    dydt[-1] = (y[-1] - y[-2]) / h
    return dydt

# 计算导数
sec_deriv = {}
ins_deriv = {}
for var in ['E', 'R', 'M', 'S', 'A']:
    sec_deriv[var] = discrete_derivative(t, secure[var])
    ins_deriv[var] = discrete_derivative(t, insecure[var])

# ========== 打印导数结果 ==========
print("时间 t =", t)
print("\n--- 安全依恋环境导数 ---")
for var in ['E', 'R', 'M', 'S', 'A']:
    print(f"d{var}/dt:", np.round(sec_deriv[var], 2))
print("\n--- 非安全依恋环境导数 ---")
for var in ['E', 'R', 'M', 'S', 'A']:
    print(f"d{var}/dt:", np.round(ins_deriv[var], 2))

# ========== 绘制导数对比图 ==========
variables = ['E', 'R', 'M', 'S', 'A']
fig, axes = plt.subplots(2, 3, figsize=(15, 10))
axes = axes.flatten()

for i, var in enumerate(variables):
    ax = axes[i]
    ax.plot(t, sec_deriv[var], 'o-', color='steelblue', label='安全依恋', linewidth=2)
    ax.plot(t, ins_deriv[var], 's--', color='coral', label='非安全依恋', linewidth=2)
    ax.set_title(f'd{var}/dt', fontsize=14, fontweight='bold')
    ax.set_xlabel('时间 t')
    ax.set_ylabel('导数值')
    ax.legend()
    ax.grid(True, linestyle='--', alpha=0.6)

axes[-1].set_visible(False)
plt.suptitle('一阶导数对比：安全 vs. 非安全依恋环境', fontsize=16, fontweight='bold')
plt.tight_layout()
plt.show()