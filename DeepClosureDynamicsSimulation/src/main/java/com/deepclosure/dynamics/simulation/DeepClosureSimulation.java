package com.deepclosure.dynamics.simulation;

/**
 * 社会系统深度闭环动力学模拟 (Deep‑Closure Dynamics Simulation)
 * 整合六大模型，形成"制度博弈 → 异化传导 → 文明承压 → 反馈制度"的闭环。
 */
public class DeepClosureSimulation {

    static final int N = 6;                      // 六主体
    static double[] A = new double[N];           // 主体行动力度
    static double stress = 0.0;                  // 累积文明承压
    static double[][] w = new double[N][N];     // 博弈权重矩阵
    static double[] lambda = new double[N];     // 内部耗散系数
    static double[] kappa = new double[N];      // 承压反馈系数

    // ---------- 制度参数（由主体状态动态决定）----------
    static double B_legal, R_reg, alpha, P_default, I_mod, N_frame, F_incentive, D_marginal;
    static double A_critical;
    static final double epsilon_culture = 0.12; // 文化低能噪声
    static final double T_dist_Click = 0.96;    // 流量-人性点击峰值
    static final double P_grad = 0.63;          // 物理紧绷梯度
    static final double H_grad = 0.71;          // 历史惯性梯度

    // 初始参考点（用于线性偏移映射）
    static final double[] A0 = {0.55, 0.92, 0.60, 0.78, 0.35, 0.25};
    static final double B0 = 0.15, R0 = 0.22, alpha0 = 0.08, P0 = 0.68,
            I0 = 0.74, N0 = 0.81, F0 = 0.90, D0 = 0.88, Ac0 = 0.19;

    public static void main(String[] args) {
        initSystem();
        double dt = 0.01;
        double totalTime = 200;
        double printInterval = 5.0;
        double nextPrint = 0.0;

        // 打印初始状态
        updateParams();
        printState(0.0);

        // 时间推进
        for (double t = dt; t <= totalTime; t += dt) {
            step(dt);
            if (t >= nextPrint - dt / 2) {
                printState(t);
                nextPrint += printInterval;
            }
        }
    }

    /** 初始化网络、耗散与反馈系数 */
    static void initSystem() {
        System.arraycopy(A0, 0, A, 0, N);
        stress = 0;

        // 博弈权重矩阵 (j -> i)
        w[0] = new double[]{0, 0.3, 0, 0.2, -0.3, 0.1}; // 制定者
        w[1] = new double[]{0.1, 0.4, 0, 0.3, -0.2, -0.4}; // 平台/资本
        w[2] = new double[]{0.4, 0.3, 0, 0.1, -0.1, 0};   // 执行层
        w[3] = new double[]{0.2, 0.5, 0, 0, -0.3, -0.2};  // 受益者
        w[4] = new double[]{0.1, -0.4, 0, -0.3, 0.2, 0.4}; // 受损者
        w[5] = new double[]{0.2, -0.5, 0, -0.3, 0.5, 0};   // 修补者

        lambda = new double[]{0.1, 0.05, 0.15, 0.05, 0.2, 0.1};
        kappa  = new double[]{0.05, 0, 0, 0, 0.3, 0.4};     // 承压主要激活受损者与修补者
    }

    /** 单步前向欧拉积分 */
    static void step(double dt) {
        // 1. 更新异化传导层
        updateParams();
        double L = computeL();
        double C = computeC();
        double O = computeO();

        // 2. 文明承压层积分
        double psiGrad = 0.60 + 0.15 * (L + O);
        double dStress = P_grad + H_grad + psiGrad;
        stress += dStress * dt;

        // 3. 计算六主体导数
        double[] dA = new double[N];
        for (int i = 0; i < N; i++) {
            double interaction = 0.0;
            for (int j = 0; j < N; j++) {
                interaction += w[i][j] * A[j];
            }
            dA[i] = interaction - lambda[i] * A[i] + kappa[i] * dStress;
        }

        // 4. 更新主体行动力度并裁剪至 [0.01, 1.0]
        for (int i = 0; i < N; i++) {
            A[i] += dA[i] * dt;
            A[i] = clamp(A[i], 0.01, 1.0);
        }
    }

    /** 将六主体状态映射为制度参数（线性偏移形式，围绕初始点展开） */
    static void updateParams() {
        B_legal   = clamp(B0 + 0.5*(A[1]-0.92) - 0.3*(A[4]-0.35) - 0.3*(A[5]-0.25), 0.02, 0.9);
        R_reg     = clamp(R0 + 0.4*(A[5]-0.25) - 0.3*(A[1]-0.92), 0.05, 0.8);
        alpha     = clamp(alpha0 + 0.1*(A[5]-0.25), 0.01, 0.3);
        P_default = clamp(P0 + 0.3*(A[0]-0.55) + 0.3*(A[1]-0.92) - 0.2*(A[5]-0.25), 0.3, 0.95);
        I_mod     = clamp(I0 + 0.4*(A[1]-0.92) + 0.2*(A[2]-0.60), 0.3, 0.99);
        N_frame   = clamp(N0 + 0.3*(A[1]-0.92) + 0.3*(A[3]-0.78), 0.3, 0.99);
        F_incentive = clamp(F0 + 0.5*(A[1]-0.92), 0.3, 0.99);
        D_marginal  = clamp(D0 - 0.4*(A[4]-0.35) + 0.2*(A[1]-0.92), 0.2, 0.99);
        A_critical  = clamp(Ac0 + 0.1*(A[4]-0.35) + 0.1*(A[5]-0.25), 0.05, 0.5);
    }

    // ---------- 异化传导层的代数方程 ----------
    static double computeL() { return (F_incentive * D_marginal) / B_legal; }
    static double computeC() { return (I_mod * N_frame) / A_critical + epsilon_culture; }
    static double computeO() { return clamp(T_dist_Click - alpha * R_reg, 0, 1); }

    /** 工具：数值裁剪 */
    static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }

    /** 格式化输出当前状态 */
    static void printState(double t) {
        double L = computeL();
        double C = computeC();
        double O = computeO();
        System.out.printf("t=%-5.1f A:[%.3f %.3f %.3f %.3f %.3f %.3f] L=%.3f C=%.3f O=%.3f Stress=%.2f%n",
                t, A[0], A[1], A[2], A[3], A[4], A[5], L, C, O, stress);
    }
}