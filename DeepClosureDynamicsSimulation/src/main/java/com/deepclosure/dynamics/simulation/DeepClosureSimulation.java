package com.deepclosure.dynamics.simulation;

/**
 * 社会系统深度闭环动力学模拟 (优化版)
 * 目标：实现 Stress 的指数增长
 */
public class DeepClosureSimulation {

    static final int N = 6; // 六主体
    static double[] A = new double[N]; // 主体行动力度
    static double stress = 0.0; // 累积文明承压

    // 【新增】压力自催化系数：这是实现指数增长的关键参数
    static final double ETA_STRESS = 0.008;

    static double[][] w = new double[N][N]; // 博弈权重矩阵
    static double[] lambda = new double[N]; // 内部耗散系数
    static double[] kappa = new double[N]; // 承压反馈系数

    // ---------- 制度参数（由主体状态动态决定）----------
    static double B_legal, R_reg, alpha, P_default, I_mod, N_frame, F_incentive, D_marginal;
    static double A_critical;

    // 固定常数
    static final double epsilon_culture = 0.12; // 文化低能噪声
    static final double T_dist_Click = 0.96; // 流量-人性点击峰值
    static final double P_grad = 0.63; // 物理紧绷梯度
    static final double H_grad = 0.71; // 历史惯性梯度

    // 【优化】调整后的初始参考点
    // 策略：略微降低受损者和修补者的初始力度，制造"压力蓄力-爆发"的滞后效果
    static final double[] A0 = {0.60, 0.85, 0.65, 0.70, 0.20, 0.15};

    // 制度参数初始点
    static final double B0 = 0.15, R0 = 0.22, alpha0 = 0.08, P0 = 0.68,
            I0 = 0.74, N0 = 0.81, F0 = 0.90, D0 = 0.88, Ac0 = 0.19;

    public static void main(String[] args) {
        initSystem();
        double dt = 0.01;
        double totalTime = 400;
        double printInterval = 5.0;
        double nextPrint = 0.0;

        // 打印表头
        System.out.printf("t=%-5s A:[%s] L=%s C=%s O=%s Stress=%s%n",
                "Time", "Actor_Strengths", "Exploitation", "Cognition", "Obscenity", "指数承压");

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

    /**
     * 初始化网络、耗散与反馈系数
     * 优化点：增强受损者与修补者的反馈敏感度(kappa)
     */
    static void initSystem() {
        System.arraycopy(A0, 0, A, 0, N);
        stress = 0.01; // 初始微量压力，避免对数零点问题

        // 博弈权重矩阵 (j -> i)
        // 优化策略：增强受损者(4)和修补者(5)对资本(1)的反作用力，制造剧烈震荡
        w[0] = new double[]{0, 0.3, 0, 0.2, -0.3, 0.1}; // 制定者
        w[1] = new double[]{0.1, 0.4, 0, 0.3, -0.5, -0.6}; // 平台/资本 (受到更强的反向博弈)
        w[2] = new double[]{0.4, 0.3, 0, 0.1, -0.1, 0}; // 执行层
        w[3] = new double[]{0.2, 0.5, 0, 0, -0.3, -0.2}; // 受益者
        w[4] = new double[]{0.1, -0.4, 0, -0.3, 0.2, 0.4}; // 受损者
        w[5] = new double[]{0.2, -0.5, 0, -0.3, 0.5, 0}; // 修补者

        // 内部耗散系数 lambda
        // 降低资本和修补者的耗散，让他们更持久
        lambda = new double[]{0.12, 0.03, 0.15, 0.08, 0.18, 0.08};

        // 【关键优化】承压反馈系数 kappa
        // 原值: {0.05, 0, 0, 0, 0.3, 0.4}
        // 新值: 让受损者(4)和修补者(5)对压力极其敏感，形成正反馈回路
        kappa = new double[]{0.08, 0.05, 0.1, 0.05, 0.45, 0.60};
    }

    /**
     * 单步前向欧拉积分
     * 核心算法修改：引入压力自催化项 (Stress -> dStress)
     */
    static void step(double dt) {
        // 1. 更新制度参数
        updateParams();
        double L = computeL();
        double C = computeC();
        double O = computeO();

        // 2. 【核心修改】文明承压层积分 (实现指数增长的关键)
        // 基础梯度
        double psiGrad_base = 0.60 + 0.15 * (L + O);

        // 自催化逻辑：当前的压力(Stress)会放大压力的增长率
        // 公式: dStress = (基础常数 + 耦合项 * 当前压力) * 基础梯度
        // 当 stress 增大时，dStress 会指数级倍增
        double dStress = (P_grad + H_grad + psiGrad_base) * (1.0 + ETA_STRESS * stress);

        stress += dStress * dt;

        // 3. 计算六主体导数
        double[] dA = new double[N];
        for (int i = 0; i < N; i++) {
            double interaction = 0.0;
            for (int j = 0; j < N; j++) {
                interaction += w[i][j] * A[j];
            }
            // 强化反馈：高压力直接激化主体行动
            dA[i] = interaction - lambda[i] * A[i] + kappa[i] * dStress;
        }

        // 4. 更新主体行动力度
        for (int i = 0; i < N; i++) {
            A[i] += dA[i] * dt;
            A[i] = clamp(A[i], 0.01, 1.0);
        }
    }

    /**
     * 将六主体状态映射为制度参数
     */
    static void updateParams() {
        B_legal = clamp(B0 + 0.5*(A[1]-0.92) - 0.3*(A[4]-0.35) - 0.3*(A[5]-0.25), 0.02, 0.9);
        R_reg = clamp(R0 + 0.4*(A[5]-0.25) - 0.3*(A[1]-0.92), 0.05, 0.8);
        alpha = clamp(alpha0 + 0.1*(A[5]-0.25), 0.01, 0.3);
        P_default = clamp(P0 + 0.3*(A[0]-0.55) + 0.3*(A[1]-0.92) - 0.2*(A[5]-0.25), 0.3, 0.95);
        I_mod = clamp(I0 + 0.4*(A[1]-0.92) + 0.2*(A[2]-0.60), 0.3, 0.99);
        N_frame = clamp(N0 + 0.3*(A[1]-0.92) + 0.3*(A[3]-0.78), 0.3, 0.99);
        F_incentive = clamp(F0 + 0.5*(A[1]-0.92), 0.3, 0.99);
        D_marginal = clamp(D0 - 0.4*(A[4]-0.35) + 0.2*(A[1]-0.92), 0.2, 0.99);
        A_critical = clamp(Ac0 + 0.1*(A[4]-0.35) + 0.1*(A[5]-0.25), 0.05, 0.5);
    }

    // ---------- 异化传导层的代数方程 ----------
    static double computeL() {
        return (F_incentive * D_marginal) / B_legal;
    }

    static double computeC() {
        return (I_mod * N_frame) / A_critical + epsilon_culture;
    }

    static double computeO() {
        return clamp(T_dist_Click - alpha * R_reg, 0, 1);
    }

    /** 工具：数值裁剪 */
    static double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }

    /** 格式化输出当前状态 */
    static void printState(double t) {
        double L = computeL();
        double C = computeC();
        double O = computeO();
        // 输出格式微调，保留更多Stress信息
        System.out.printf("t=%-5.1f A:[%.2f %.2f %.2f %.2f %.2f %.2f] L=%.2f C=%.2f O=%.2f Stress=%.1f%n",
                t, A[0], A[1], A[2], A[3], A[4], A[5], L, C, O, stress);
    }
}