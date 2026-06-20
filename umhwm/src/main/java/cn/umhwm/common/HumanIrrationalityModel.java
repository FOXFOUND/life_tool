package cn.umhwm.common;
import java.util.*;
import java.util.function.Function;

/**
 * 人性弱点统一量化模型 (UMHWM) - Java实现
 * 基于生物-心理-社会三层参数，计算主观效用、弱点指数、行为概率与数字孪生状态转移
 */
public class HumanIrrationalityModel {

    // ==================== 1. 生物层参数 ====================
    private double cPfcAmy;        // 前额叶-杏仁核控制力 [0,1]
    private double d2Availability; // 多巴胺D2受体可用性
    private double hCort;          // 皮质醇浓度
    private double aT;             // 情绪唤醒度 [0,1]
    private double wCurrent;       // 当前意志力容量
    private final double wMax = 100.0; // 意志力最大值

    // ==================== 2. 心理层参数 ====================
    private double E;              // 主观成功概率 [0,1]
    private double V;              // 客观损益值 (正收益，负亏损)
    private double k;              // 延迟折扣系数 (基础冲动性)
    private double D;              // 延迟时间 (与损益单位匹配的时间尺度)
    private double lambdaLoss;     // 损失厌恶系数，默认2.5
    private double omega;          // 认知算力带宽

    // ==================== 3. 社会层参数 ====================
    private double dt;             // 群体一致性比例 [0,1]
    private double sigmaConf;      // 从众敏感度

    // ==================== 4. 行为选择参数 ====================
    private double thetaRational;  // 决策理性度/敏感度 [0,10]

    // ==================== 5. 弱点指数用额外参数 ====================
    // 这些参数仅在计算弱点指数时传入或设置，也可动态更新

    // ==================== 构造函数 ====================
    public HumanIrrationalityModel() {
        // 默认参数（一个“典型非理性人”的基线）
        this.cPfcAmy = 0.42;
        this.d2Availability = 0.5;
        this.hCort = 0.0;
        this.aT = 0.2;
        this.wCurrent = 100.0;
        this.E = 0.5;
        this.V = 10.0;
        this.k = 0.04;
        this.D = 1.0;
        this.lambdaLoss = 2.5;
        this.omega = 1.0;
        this.dt = 0.5;
        this.sigmaConf = 1.0;
        this.thetaRational = 5.0;
    }

    // 全参数构造函数
    public HumanIrrationalityModel(double cPfcAmy, double d2Availability, double hCort, double aT,
                                   double wCurrent, double E, double V, double k, double D,
                                   double lambdaLoss, double omega, double dt, double sigmaConf,
                                   double thetaRational) {
        this.cPfcAmy = clamp(cPfcAmy, 0, 1);
        this.d2Availability = Math.max(0, d2Availability);
        this.hCort = Math.max(0, hCort);
        this.aT = clamp(aT, 0, 1);
        this.wCurrent = clamp(wCurrent, 0, wMax);
        this.E = clamp(E, 0, 1);
        this.V = V;
        this.k = Math.max(0, k);
        this.D = Math.max(0, D);
        this.lambdaLoss = Math.max(0, lambdaLoss);
        this.omega = Math.max(0.1, omega); // 避免除零
        this.dt = clamp(dt, 0, 1);
        this.sigmaConf = Math.max(0, sigmaConf);
        this.thetaRational = clamp(thetaRational, 0, 10);
    }

    // ==================== 核心计算模块 ====================

    /**
     * 理性修正系数 Λ_bio = C_pfc_amy / (1 + 0.02 * H_cort)
     */
    public double computeLambdaBio() {
        return cPfcAmy / (1.0 + 0.02 * hCort);
    }

    /**
     * 前景价值函数 v(x)
     * @param x 客观收益/亏损
     * @return 主观价值
     */
    public double prospectValue(double x) {
        if (x >= 0) {
            return Math.pow(x, 0.88);
        } else {
            return -lambdaLoss * Math.pow(-x, 0.88);
        }
    }

    /**
     * 冲动贴现系数 Γ = k * (1 + 0.3*D2 + 0.5*a_t)
     */
    public double computeGamma() {
        return k * (1.0 + 0.3 * d2Availability + 0.5 * aT);
    }

    /**
     * 时间贴现分式 numerator / denominator
     * 分子 = 0.9 * I(D>0) + 0.9^D
     * 分母 = 1 + Γ * D
     */
    public double computeTimeDiscount() {
        double gamma = computeGamma();
        double numerator;
        if (D == 0) {
            numerator = 1.0; // 即时奖励不打折
        } else {
            numerator = 0.9 + Math.pow(0.9, D); // 实际公式: 0.9*1 + 0.9^D
        }
        double denominator = 1.0 + gamma * D;
        return numerator / denominator;
    }

    /**
     * Term1 = Λ_bio * [ v(V) * E * timeDiscount ]
     */
    public double computeTerm1() {
        double lambdaBio = computeLambdaBio();
        double vVal = prospectValue(V);
        double timeDisc = computeTimeDiscount();
        return lambdaBio * vVal * E * timeDisc;
    }

    /**
     * 意志力代价系数 Φ
     * Φ = (0.1 + exp(0.05*(W_max - W(t)))) / Ω
     */
    public double computePhi() {
        return (0.1 + Math.exp(0.05 * (wMax - wCurrent))) / omega;
    }

    /**
     * Term2 = -Φ * ΔM
     * @param deltaM 诱惑强度 (短期享乐选项与长期目标的效用差值)
     */
    public double computeTerm2(double deltaM) {
        return -computePhi() * deltaM;
    }

    /**
     * Term3 = σ_conf * (-0.8 * d_t + 0.2)
     */
    public double computeTerm3() {
        return sigmaConf * (-0.8 * dt + 0.2);
    }

    /**
     * 总主观效用 U_subj = Term1 + Term2 + Term3 + ε
     * @param deltaM 诱惑强度
     * @param noise 随机噪声 (通常服从N(0,0.01)的随机数，调用时可传入0或由外部生成)
     */
    public double computeSubjectiveUtility(double deltaM, double noise) {
        return computeTerm1() + computeTerm2(deltaM) + computeTerm3() + noise;
    }

    /**
     * Softmax选择概率
     * @param utilities 所有选项的主观效用数组
     * @param index 要计算概率的选项索引
     * @return 该选项被选择的概率
     */
    public double softmaxProbability(double[] utilities, int index) {
        double maxU = Arrays.stream(utilities).max().orElse(0);
        double sumExp = 0;
        for (double u : utilities) {
            sumExp += Math.exp(thetaRational * (u - maxU)); // 减去最大值防溢出
        }
        double expU = Math.exp(thetaRational * (utilities[index] - maxU));
        return expU / sumExp;
    }

    /**
     * 意志力动态更新 (离散化，假设时间步长1个单位)
     * dW/dt = -0.01*Φ*ΔM + 0.02*(W_max - W(t))
     * @param deltaM 当前时间步内面对的诱惑强度
     * @return 更新后的意志力存量
     */
    public double updateWillpower(double deltaM) {
        double phi = computePhi();
        double deltaW = -0.01 * phi * deltaM + 0.02 * (wMax - wCurrent);
        wCurrent = clamp(wCurrent + deltaW, 0, wMax);
        return wCurrent;
    }

    // ==================== 五大弱点指数计算 ====================

    /**
     * 贪婪指数 G = ((E_d - E_r) / E_r) / (R_p * S)
     * 注意分母不能为0
     */
    public static double greedIndex(double Ed, double Er, double Rp, double S) {
        if (Er == 0 || (Rp * S) == 0) {
            return Ed > Er ? Double.POSITIVE_INFINITY : 0;
        }
        return ((Ed - Er) / Er) / (Rp * S);
    }

    /**
     * 恐惧指数 F = (L_d - L_r) / (E_r * T)
     */
    public static double fearIndex(double Ld, double Lr, double Er, double T) {
        if (Er == 0 || T == 0) {
            return (Ld > Lr) ? Double.POSITIVE_INFINITY : 0;
        }
        return (Ld - Lr) / (Er * T);
    }

    /**
     * 从众指数 H = (N_f * I) / (K * J)
     */
    public static double herdIndex(double Nf, double I, double K, double J) {
        if (K == 0 || J == 0) {
            return Double.POSITIVE_INFINITY;
        }
        return (Nf * I) / (K * J);
    }

    /**
     * 拖延指数 P = (D_task * C_escape) / (R * (T_remaining / T_deadline))
     * @param D_task 任务主观难度
     * @param C_escape 逃避选项的即时快感
     * @param R 完成任务的长远回报
     * @param T_remaining 剩余时间
     * @param T_deadline 总截止时间
     */
    public static double procrastinationIndex(double D_task, double C_escape, double R,
                                              double T_remaining, double T_deadline) {
        if (R == 0 || T_deadline == 0) return Double.POSITIVE_INFINITY;
        double timePressure = T_remaining / T_deadline;
        if (timePressure == 0) return Double.POSITIVE_INFINITY;
        return (D_task * C_escape) / (R * timePressure);
    }

    /**
     * 过度自信指数 O = (A_s - A_r) / (E_years * F_m)
     */
    public static double overconfidenceIndex(double As, double Ar, double E_years, double Fm) {
        if (E_years == 0 || Fm == 0) {
            return (As > Ar) ? Double.POSITIVE_INFINITY : 0;
        }
        return (As - Ar) / (E_years * Fm);
    }

    // ==================== 神经底层量化 ====================

    /**
     * 延迟折扣主观价值 V = A / (1 + k * D)
     */
    public static double discountValue(double A, double k, double D) {
        return A / (1 + k * D);
    }

    /**
     * 冷热决策权重 w = 1 / (1 + exp(-(2.5 * Z_xy - 0.5)))
     */
    public static double decisionWeight(double Zxy) {
        return 1.0 / (1.0 + Math.exp(-(2.5 * Zxy - 0.5)));
    }

    /**
     * 冷热综合价值 V_total = w * V_cold + (1 - w) * V_hot
     */
    public static double totalValue(double Vcold, double Vhot, double Zxy) {
        double w = decisionWeight(Zxy);
        return w * Vcold + (1 - w) * Vhot;
    }

    // ==================== 贝叶斯人格更新 ====================

    /**
     * 贝叶斯更新后验概率
     * @param prior 先验概率 P(θ)
     * @param likelihood 似然度 P(Y|θ)
     * @param evidence 证据 P(Y) (边际概率)
     * @return 后验概率 P(θ|Y)
     */
    public static double bayesianUpdate(double prior, double likelihood, double evidence) {
        if (evidence == 0) return prior; // 无信息时保持先验
        return (likelihood * prior) / evidence;
    }

    // 更实用的贝叶斯更新：给定是否观察到行为，更新属于“高特质”的概率
    public static double updateTraitProbability(double priorHigh, double probBehaviorGivenHigh,
                                                double probBehaviorGivenLow) {
        double evidence = probBehaviorGivenHigh * priorHigh + probBehaviorGivenLow * (1 - priorHigh);
        if (evidence == 0) return priorHigh;
        return (probBehaviorGivenHigh * priorHigh) / evidence;
    }

    // ==================== UMHWM 状态转移 (简化版) ====================

    /**
     * UMHWM数字孪生状态转移 (线性简化版)
     * X_{t+1} = A * X_t + B * U_t + ω_t
     * @param Xt 当前状态向量 (5层)
     * @param A 状态转移矩阵 (5x5)
     * @param Ut 外部干预向量
     * @param B 干预效果矩阵
     * @param omega 过程噪声向量
     * @return 下一时刻状态向量
     */
    public static double[] stateTransition(double[] Xt, double[][] A, double[] Ut,
                                           double[][] B, double[] omega) {
        int n = Xt.length;
        double[] AXt = multiplyMatrixVector(A, Xt);
        double[] BUt = multiplyMatrixVector(B, Ut);
        double[] Xt1 = new double[n];
        for (int i = 0; i < n; i++) {
            Xt1[i] = AXt[i] + BUt[i] + omega[i];
        }
        return Xt1;
    }

    // 矩阵乘以向量辅助方法
    private static double[] multiplyMatrixVector(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int cols = vector.length;
        double[] result = new double[rows];
        for (int i = 0; i < rows; i++) {
            double sum = 0;
            for (int j = 0; j < cols; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }
        return result;
    }

    // ==================== 工具方法 ====================
    private static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    // ==================== Getters & Setters (仅关键字段) ====================
    public double getcPfcAmy() { return cPfcAmy; }
    public void setcPfcAmy(double cPfcAmy) { this.cPfcAmy = clamp(cPfcAmy, 0, 1); }
    public double getd2Availability() { return d2Availability; }
    public void setd2Availability(double d2Availability) { this.d2Availability = Math.max(0, d2Availability); }
    public double gethCort() { return hCort; }
    public void sethCort(double hCort) { this.hCort = Math.max(0, hCort); }
    public double getaT() { return aT; }
    public void setaT(double aT) { this.aT = clamp(aT, 0, 1); }
    public double getwCurrent() { return wCurrent; }
    public void setwCurrent(double wCurrent) { this.wCurrent = clamp(wCurrent, 0, wMax); }
    public double getE() { return E; }
    public void setE(double E) { this.E = clamp(E, 0, 1); }
    public double getV() { return V; }
    public void setV(double V) { this.V = V; }
    public double getK() { return k; }
    public void setK(double k) { this.k = Math.max(0, k); }
    public double getD() { return D; }
    public void setD(double D) { this.D = Math.max(0, D); }
    public double getLambdaLoss() { return lambdaLoss; }
    public void setLambdaLoss(double lambdaLoss) { this.lambdaLoss = Math.max(0, lambdaLoss); }
    public double getOmega() { return omega; }
    public void setOmega(double omega) { this.omega = Math.max(0.1, omega); }
    public double getDt() { return dt; }
    public void setDt(double dt) { this.dt = clamp(dt, 0, 1); }
    public double getSigmaConf() { return sigmaConf; }
    public void setSigmaConf(double sigmaConf) { this.sigmaConf = Math.max(0, sigmaConf); }
    public double getThetaRational() { return thetaRational; }
    public void setThetaRational(double thetaRational) { this.thetaRational = clamp(thetaRational, 0, 10); }
    public double getwMax() { return wMax; }

    // ==================== 使用示例 ====================
    public static void main(String[] args) {
        // 1. 实例化模型并计算主观效用
        HumanIrrationalityModel model = new HumanIrrationalityModel();
        model.setV(100.0);          // 客观收益100
        model.setD(12.0);           // 延迟12个月
        model.sethCort(5.0);        // 中等压力
        model.setaT(0.7);           // 较高唤醒度
        model.setDt(0.8);           // 80%的人都在做
        model.setSigmaConf(1.2);    // 从众敏感

        double term1 = model.computeTerm1();
        double term2 = model.computeTerm2(30); // 诱惑强度30
        double term3 = model.computeTerm3();
        double noise = 0.02; // 模拟随机噪声
        double U = model.computeSubjectiveUtility(30, noise);
        System.out.printf("U_subj = %.2f (Term1=%.2f, Term2=%.2f, Term3=%.2f, noise=%.2f)%n",
                U, term1, term2, term3, noise);

        // 2. 两个选项的概率
        double[] utils = {U, U - 5}; // 选项A和B
        double pA = model.softmaxProbability(utils, 0);
        double pB = model.softmaxProbability(utils, 1);
        System.out.printf("选择概率: P(A)=%.2f, P(B)=%.2f%n", pA, pB);

        // 3. 弱点指数
        double greed = greedIndex(80, 50, 0.2, 1.0);
        double fear = fearIndex(40, 20, 10, 0.5);
        double herd = herdIndex(0.9, 5, 2, 0.3);
        double procrast = procrastinationIndex(8, 9, 5, 30, 180);
        double overconf = overconfidenceIndex(80, 55, 10, 0.5);
        System.out.printf("贪婪: %.2f, 恐惧: %.2f, 从众: %.2f, 拖延: %.2f, 过度自信: %.2f%n",
                greed, fear, herd, procrast, overconf);

        // 4. 贝叶斯更新示例
        double prior = 0.3;
        double post = updateTraitProbability(prior, 0.8, 0.1); // 观察到行为Y
        System.out.printf("后验高拖延概率: %.2f%n", post);

        // 5. 意志力更新
        double newW = model.updateWillpower(30);
        System.out.printf("意志力更新后: %.1f/%.1f%n", newW, model.getwMax());
    }
}