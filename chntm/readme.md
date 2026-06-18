下面将文档中的**人性耦合张量模型（CHNTM）核心演化方程**转化为可运行的 Java 代码。代码基于 **Adams-Bashforth-Moulton 预估校正法** 数值求解 Caputo 分数阶随机微分方程组，完整复现五维耦合、非线性阈值、环境驱动、随机扰动、记忆依赖等核心机制。


---

## 代码与文档的对应关系
| 文档概念 | 代码对应实现 |
|----------|--------------|
| 五维状态向量 $\boldsymbol{X}(t)$ | `HumanState` 记录类，`values` 数组对应 E/R/M/S/A |
| 分数阶微分算子 $D_t^\mu$ | `step()` 方法中 Adams-Bashforth-Moulton 预估校正法 |
| 非线性耦合项 $K_{ij} \cdot \Phi(\|X_j-X_i\|) \cdot X_j$ | `computeRightHandSide()` + `phi()` + `computeCouplingMatrix()` |
| 环境驱动项 $\gamma_i \cdot U_i(t)$ | `EnvironmentPerturbation` 接口 + `gamma` 系数 |
| 随机波动项 $\sigma_{\zeta i} \cdot \Xi_i(t)$ | `random.nextGaussian() * Math.sqrt(dt)` 模拟维纳过程 |
| 耦合演化速率 $\eta$ / 衰减系数 $\delta$ | `computeCouplingMatrix()` 中 `evolveFactor` 与 `decayFactor` |
| 初始状态新生儿赋值 | `initVals` 数组，符合文档 3.2 节取值逻辑 |

---

## 运行说明
1. 直接编译运行 `main` 方法，会输出**安全依恋**与**非安全依恋**两种环境下，个体 0-20 岁的人性维度发展轨迹
2. 可自定义修改：
    - 调整 `baseK` 矩阵修改不同维度间的影响强度
    - 实现 `EnvironmentPerturbation` 接口自定义任意环境场景
    - 修改 `mu` 调整记忆依赖强度（越接近1，早期经历影响越持久）
3. 可扩展功能：添加吸引子判断逻辑（对应文档 2.3 节三类稳态）、添加参数校准方法、增加可视化输出。