package com.model.chntm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 人性耦合张量模型 (Coupled Human Nature Tensor Model, CHNTM)
 * 对应文档：五维耦合分数阶随机微分方程组的数值实现
 */
public class CHNTModel {
    // ========== 维度常量定义（对应文档5大核心维度） ==========
    public static final int DIM = 5;
    public static final int E = 0; // 情感 Emotion
    public static final int R = 1; // 理性 Reasoning
    public static final int M = 2; // 道德 Morality
    public static final int S = 3; // 社会性 Sociality
    public static final int A = 4; // 自我意识 Self-awareness

    // ========== 系统核心参数（对应文档表3） ==========
    private final double mu;              // 分数阶微分阶数，记忆依赖强度 (0.7~0.9)
    private final double eta;             // 耦合强度演化速率 (0.4~0.6)
    private final double delta;           // 耦合强度自然衰减系数 (0.2~0.4)
    private final double[] gamma;         // 各维度环境敏感系数 (0.3~0.7)
    private final double[] sigma;         // 各维度随机波动强度 (0.1~0.3)
    private final double[][] baseK;       // 基础耦合权重矩阵 5x5 (0.1~0.5)
    private final double phiThreshold;    // 非线性函数Φ的状态差阈值
    private final double phiSharpness;    // 非线性函数Φ的衰减陡度

    // ========== 数值计算参数 ==========
    private final double dt;              // 时间步长
    private final Random random;          // 随机数生成器
    private final List<HumanState> historyStates;  // 历史状态（分数阶计算依赖）
    private final List<double[]> historyRHS;       // 历史右边项值
    private final EnvironmentPerturbation environment; // 环境扰动函数

    // ========== 状态记录类 ==========
    public record HumanState(double time, double[] values) {
        public HumanState {
            if (values.length != DIM) {
                throw new IllegalArgumentException("状态维度必须为5");
            }
            values = values.clone(); // 防御性拷贝，保证不可变
        }

        @Override
        public String toString() {
            return String.format(
                    "t=%.2f | E=%.4f | R=%.4f | M=%.4f | S=%.4f | A=%.4f",
                    time, values[E], values[R], values[M], values[S], values[A]
            );
        }
    }

    // ========== 环境扰动函数式接口 ==========
    @FunctionalInterface
    public interface EnvironmentPerturbation {
        /**
         * 获取指定时间点的多尺度环境扰动向量
         * @param t 时间（单位：年）
         * @return 5维环境驱动值 U[E], U[R], U[M], U[S], U[A]
         */
        double[] getU(double t);
    }

    // ========== 构造函数：初始化模型参数与初始状态 ==========
    public CHNTModel(
            double mu, double eta, double delta,
            double[] gamma, double[] sigma, double[][] baseK,
            double phiThreshold, double phiSharpness,
            double dt, long seed,
            HumanState initialState,
            EnvironmentPerturbation environment
    ) {
        this.mu = mu;
        this.eta = eta;
        this.delta = delta;
        this.gamma = gamma.clone();
        this.sigma = sigma.clone();
        this.baseK = deepCopyMatrix(baseK);
        this.phiThreshold = phiThreshold;
        this.phiSharpness = phiSharpness;
        this.dt = dt;
        this.random = new Random(seed);
        this.environment = environment;

        // 初始化历史队列（分数阶积分依赖所有历史状态）
        this.historyStates = new ArrayList<>();
        this.historyRHS = new ArrayList<>();
        historyStates.add(initialState);
        historyRHS.add(computeRightHandSide(initialState, initialState.time()));
    }

    // ========== 核心1：非线性阈值调控函数 Φ (对应文档2.2节) ==========
    /**
     * 基于双曲正切实现：状态差小于阈值时耦合增益高，超过阈值快速衰减
     * 物理意义：维度间差异过大时，交互作用会快速减弱
     * @param diff 两个维度的状态差绝对值
     * @return 耦合增益系数 [0, 1]
     */
    private double phi(double diff) {
        return 0.5 * (1 + Math.tanh((phiThreshold - diff) / phiSharpness));
    }

    // ========== 核心2：计算当前耦合算子矩阵 K(t,X) ==========
    /**
     * 耦合强度随自我意识成熟增强，随时间自然衰减（对应文档η、δ参数）
     * @param state 当前状态
     * @param t 当前时间
     * @return 5x5 动态耦合矩阵
     */
    private double[][] computeCouplingMatrix(HumanState state, double t) {
        double[][] K = new double[DIM][DIM];
        double selfAwareness = state.values()[A];
        double decayFactor = Math.exp(-delta * t);          // 自然衰减
        double evolveFactor = 1 + eta * selfAwareness;      // 自我意识驱动增强

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                K[i][j] = baseK[i][j] * evolveFactor * decayFactor;
            }
        }
        return K;
    }

    // ========== 核心3：计算微分方程右边项 f(t,X) ==========
    /**
     * 对应文档核心方程：D_t^μ X_i = Σ K_ij*Φ(|Xj-Xi|)*Xj + γ_i*U_i + σ_ζi*ξ_i
     * @param state 当前状态
     * @param t 当前时间
     * @return 5维右边项值
     */
    private double[] computeRightHandSide(HumanState state, double t) {
        double[] rhs = new double[DIM];
        double[][] K = computeCouplingMatrix(state, t);
        double[] U = environment.getU(t);

        for (int i = 0; i < DIM; i++) {
            double couplingSum = 0.0;
            double Xi = state.values()[i];
            // 累加所有维度的耦合作用
            for (int j = 0; j < DIM; j++) {
                double Xj = state.values()[j];
                double diff = Math.abs(Xj - Xi);
                couplingSum += K[i][j] * phi(diff) * Xj;
            }
            // 耦合项 + 环境驱动项 + 维纳随机项
            double noise = sigma[i] * random.nextGaussian() * Math.sqrt(dt);
            rhs[i] = couplingSum + gamma[i] * U[i] + noise;
        }
        return rhs;
    }

    // ========== 核心4：分数阶积分单步推进（Adams-Bashforth-Moulton 预估校正法） ==========
    /**
     * 求解 Caputo 分数阶微分方程，推进一个时间步长
     * 实现记忆依赖效应：当前状态变化依赖所有历史状态
     * @return 下一时刻的状态
     */
    public HumanState step() {
        int n = historyStates.size() - 1; // 当前步数（从0开始）
        double tNext = historyStates.get(n).time() + dt;
        HumanState initState = historyStates.get(0);

        // ---------- 第一步：预估步 ----------
        double[] predictSum = new double[DIM];
        for (int j = 0; j <= n; j++) {
            int k = n - j;
            double aWeight = Math.pow(k + 1, mu) - Math.pow(k, mu);
            double[] fj = historyRHS.get(j);
            for (int i = 0; i < DIM; i++) {
                predictSum[i] += aWeight * fj[i];
            }
        }

        double dtPowMu = Math.pow(dt, mu);
        double gammaMu1 = gamma(mu + 1);
        double[] predictVals = new double[DIM];
        for (int i = 0; i < DIM; i++) {
            predictVals[i] = initState.values()[i] + (dtPowMu / gammaMu1) * predictSum[i];
        }
        HumanState predictState = new HumanState(tNext, predictVals);
        double[] fPredict = computeRightHandSide(predictState, tNext);

        // ---------- 第二步：校正步 ----------
        double[] correctSum = new double[DIM];
        for (int j = 0; j <= n; j++) {
            int k = n - j;
            double bWeight = Math.pow(k + 1, mu + 1) - Math.pow(k, mu + 1);
            double[] fj = historyRHS.get(j);
            for (int i = 0; i < DIM; i++) {
                correctSum[i] += bWeight * fj[i];
            }
        }
        // 加入预估点的贡献
        for (int i = 0; i < DIM; i++) {
            correctSum[i] += fPredict[i];
        }

        double gammaMu2 = gamma(mu + 2);
        double[] nextVals = new double[DIM];
        for (int i = 0; i < DIM; i++) {
            nextVals[i] = initState.values()[i] + (dtPowMu / gammaMu2) * correctSum[i];
        }

        // 保存新状态与对应右边项
        HumanState nextState = new HumanState(tNext, nextVals);
        historyStates.add(nextState);
        historyRHS.add(computeRightHandSide(nextState, tNext));

        return nextState;
    }

    // ========== 工具：Lanczos 近似计算伽马函数 Γ(x) ==========
    private double gamma(double x) {
        double[] coef = {
                76.18009172947146,
                -86.50532032941677,
                24.01409824083091,
                -1.231739572450155,
                0.1208650973866179e-2,
                -0.5395239384953e-5
        };
        double y = x;
        double tmp = x + 5.5;
        tmp -= (x + 0.5) * Math.log(tmp);
        double ser = 1.000000000190015;
        for (double c : coef) {
            ser += c / ++y;
        }
        return Math.exp(-tmp) * Math.sqrt(2 * Math.PI) * ser / x;
    }

    // ========== 工具：深拷贝二维矩阵 ==========
    private double[][] deepCopyMatrix(double[][] src) {
        double[][] copy = new double[src.length][];
        for (int i = 0; i < src.length; i++) {
            copy[i] = src[i].clone();
        }
        return copy;
    }

    // ========== 示例：主函数演示两种环境下的人性发展推演 ==========
    public static void main(String[] args) {
        // ---------- 1. 通用参数配置（参考文档表3参考取值） ----------
        double mu = 0.8;
        double eta = 0.5;
        double delta = 0.3;
        double[] gamma = {0.5, 0.4, 0.6, 0.5, 0.45};
        double[] sigma = {0.15, 0.12, 0.1, 0.13, 0.1};
        double phiThreshold = 0.5;
        double phiSharpness = 0.1;
        double dt = 0.1; // 时间步长0.1年
        long seed = 2026L;

        // 非对称耦合权重矩阵（行i = 维度i受维度j的影响强度）
        double[][] baseK = {
                {0.0, 0.2, 0.3, 0.4, 0.35}, // E 受 R,M,S,A 影响
                {0.4, 0.0, 0.25, 0.2, 0.3}, // R 受 E,M,S,A 影响
                {0.35, 0.3, 0.0, 0.45, 0.4}, // M 受 E,R,S,A 影响
                {0.3, 0.25, 0.35, 0.0, 0.4}, // S 受 E,R,M,A 影响
                {0.25, 0.3, 0.3, 0.35, 0.0}  // A 受 E,R,M,S 影响
        };

        // 初始状态（对应文档3.2节：新生儿初始值）
        double[] initVals = {0.1, 0.05, 0.0, 0.05, 0.0};
        HumanState initState = new HumanState(0.0, initVals);

        // ---------- 2. 场景一：安全依恋正向环境 ----------
        EnvironmentPerturbation safeEnv = t -> {
            double base = 0.4 + 0.1 * Math.tanh(t / 5); // 随成长缓慢增强
            return new double[]{base, base*0.8, base*0.9, base, base*0.7};
        };

        CHNTModel safeModel = new CHNTModel(
                mu, eta, delta, gamma, sigma, baseK,
                phiThreshold, phiSharpness, dt, seed, initState, safeEnv
        );

        System.out.println("===== 安全依恋环境：前20年发展轨迹 =====");
        for (int step = 0; step < 200; step++) { // 200步 = 20年
            HumanState s = safeModel.step();
            if (step % 20 == 19) { // 每2年打印一次
                System.out.println(s);
            }
        }

        // ---------- 3. 场景二：非安全依恋负向环境 ----------
        EnvironmentPerturbation unsafeEnv = t -> {
            double base = -0.3 - 0.1 * Math.tanh(t / 5); // 长期负向环境
            return new double[]{base, base*0.7, base*0.8, base, base*0.6};
        };

        CHNTModel unsafeModel = new CHNTModel(
                mu, eta, delta, gamma, sigma, baseK,
                phiThreshold, phiSharpness, dt, seed, initState, unsafeEnv
        );

        System.out.println("\n===== 非安全依恋环境：前20年发展轨迹 =====");
        for (int step = 0; step < 200; step++) {
            HumanState s = unsafeModel.step();
            if (step % 20 == 19) {
                System.out.println(s);
            }
        }
    }
}