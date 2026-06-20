package cn.umhwm.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 人性弱点多维数学建模与量化研究 - Java实现框架
 * 包含：生物-心理-社会参数、U-DUF效用公式、五大弱点指数、
 * 神经底层指标、SEM因果模型、贝叶斯更新、数字孪生系统。
 * 所有公式均依据报告原文实现，便于学术验证与工程落地。
 */
public class WeaknessModel {

    // ==================== 1. 参数类定义 ====================

    /** 生物层参数（大脑/生理硬件） */
    public static class BioParams {
        public double pfcAmyControl;   // C_pfc-amy: 前额叶对杏仁核控制力 [0,1]
        public double dopamineDensity; // D2: 多巴胺受体密度，正数
        public double cortisol;        // H_cort: 皮质醇浓度，越高越冲动
        public double emotionArousal;  // a_t: 情绪激烈程度 [0,1]
        public double willpower;       // W(t): 剩余自控意志力 [0, Wmax]
        public static final double WILLPOWER_MAX = 100.0;

        public BioParams(double pfcAmyControl, double dopamineDensity, double cortisol,
                         double emotionArousal, double willpower) {
            this.pfcAmyControl = pfcAmyControl;
            this.dopamineDensity = dopamineDensity;
            this.cortisol = cortisol;
            this.emotionArousal = emotionArousal;
            this.willpower = Math.min(willpower, WILLPOWER_MAX);
        }
    }

    /** 心理层参数（主观认知） */
    public static class PsychoParams {
        public double successProb;     // E: 自判成功概率 [0,1]
        public double objectiveValue;  // V: 客观收益/亏损（可正可负）
        public double discountK;       // k: 延迟折扣系数（冲动指标）
        public double delayMonths;     // D: 等待时长（月）
        public double lossAversion;    // lambda_loss: 损失厌恶系数，基准2.5
        public double bandwidth;       // Omega: 认知算力带宽，多任务时下降

        public PsychoParams(double successProb, double objectiveValue, double discountK,
                            double delayMonths, double lossAversion, double bandwidth) {
            this.successProb = successProb;
            this.objectiveValue = objectiveValue;
            this.discountK = discountK;
            this.delayMonths = delayMonths;
            this.lossAversion = lossAversion;
            this.bandwidth = bandwidth;
        }
    }

    /** 社会层参数（群体压力） */
    public static class SocialParams {
        public double groupRatio;      // d_t: 身边大多数人的选择占比 [0,1]
        public double conformitySensitivity; // sigma_conf: 害怕不一致的敏感度
        public double socialCost;      // S: 不和群体一致的隐性损失（年化）

        public SocialParams(double groupRatio, double conformitySensitivity, double socialCost) {
            this.groupRatio = groupRatio;
            this.conformitySensitivity = conformitySensitivity;
            this.socialCost = socialCost;
        }
    }

    // ==================== 2. U-DUF 统一决策主观效用计算 ====================

    public static class UDUFCalculator {
        private static final Random RANDOM = new Random();

        /**
         * 计算主观效用 U_subj(H_C;t) = Term1 + Term2 + Term3 + noise
         * @param bio   生物层参数
         * @param psycho 心理层参数
         * @param social 社会层参数
         * @param rationalTheta 理性系数 θ_rational [0,10]，用于Softmax概率
         * @param includeNoise 是否加入随机噪声 ε~N(0,0.1)
         * @return 主观效用值
         */
        public static double computeSubjectiveUtility(BioParams bio, PsychoParams psycho,
                                                      SocialParams social, double rationalTheta,
                                                      boolean includeNoise) {
            double term1 = computeTerm1(bio, psycho);
            double term2 = computeTerm2(bio, psycho);
            double term3 = computeTerm3(social);
            double noise = includeNoise ? RANDOM.nextGaussian() * 0.1 : 0.0;
            return term1 + term2 + term3 + noise;
        }

        /** Term1: 收益生物学修正项 */
        private static double computeTerm1(BioParams bio, PsychoParams psycho) {
            // Lambda_bio = C_pfc-amy / (1 + 0.02*H_cort)
            double lambdaBio = bio.pfcAmyControl / (1.0 + 0.02 * bio.cortisol);

            // 前景价值函数 V(x) = x^0.88 或 -lambda_loss * (-x)^0.88
            double value = prospectValue(psycho.objectiveValue, psycho.lossAversion);

            // 冲动贴现系数 Gamma = k * (1 + 0.3*D2 + 0.5*a_t)
            double gamma = psycho.discountK * (1.0 + 0.3 * bio.dopamineDensity + 0.5 * bio.emotionArousal);

            // 时间贴现分式: (0.9*I(D>0) + 0.9^D) / (1 + Gamma*D)
            double delayFactor;
            if (psycho.delayMonths > 0) {
                double numerator = 0.9 + Math.pow(0.9, psycho.delayMonths);
                double denominator = 1.0 + gamma * psycho.delayMonths;
                delayFactor = numerator / denominator;
            } else {
                // 即时奖励，I(D>0)=0，则分子为0.9^0=1，分母为1
                delayFactor = 1.0;
            }

            return lambdaBio * (value * psycho.successProb * delayFactor);
        }

        /** 前景价值函数 */
        private static double prospectValue(double x, double lambdaLoss) {
            if (x >= 0) {
                return Math.pow(x, 0.88);
            } else {
                return -lambdaLoss * Math.pow(-x, 0.88);
            }
        }

        /** Term2: 意志力损耗项（永远为负） */
        private static double computeTerm2(BioParams bio, PsychoParams psycho) {
            // Phi = (0.1 + exp(0.05*(Wmax - W))) / Omega
            double wDiff = BioParams.WILLPOWER_MAX - bio.willpower;
            double phi = (0.1 + Math.exp(0.05 * wDiff)) / psycho.bandwidth;

            // Delta M: 短期享乐与长期目标吸引力差值，这里简化为 (1 - successProb) 作为享乐倾向
            double deltaM = 1.0 - psycho.successProb; // 假设成功概率低时，享乐吸引力高
            // 若想更精细，可接受外部参数，此处简化
            return -phi * deltaM;
        }

        /** Term3: 从众心理项 */
        private static double computeTerm3(SocialParams social) {
            // sigma_conf * (-0.8*d_t + 0.2)
            return social.conformitySensitivity * (-0.8 * social.groupRatio + 0.2);
        }

        /**
         * Softmax选择概率（二选一场景，另一选项效用为0）
         * @param u 当前选项主观效用
         * @param theta 理性系数
         * @return 选择当前选项的概率
         */
        public static double softmaxProbability(double u, double theta) {
            if (theta <= 0) return 0.5; // 随机选
            double expU = Math.exp(theta * u);
            double exp0 = 1.0; // 另一选项效用为0
            return expU / (expU + exp0);
        }

        /** 更新意志力动态（每步消耗） */
        public static double updateWillpower(BioParams bio, PsychoParams psycho, double deltaTime) {
            // dW/dt = -0.01*Phi*DeltaM + 0.02*(Wmax - W)
            double wDiff = BioParams.WILLPOWER_MAX - bio.willpower;
            double phi = (0.1 + Math.exp(0.05 * wDiff)) / psycho.bandwidth;
            double deltaM = 1.0 - psycho.successProb;
            double dW = -0.01 * phi * deltaM + 0.02 * wDiff;
            double newW = bio.willpower + dW * deltaTime;
            return Math.max(0, Math.min(BioParams.WILLPOWER_MAX, newW));
        }
    }

    // ==================== 3. 五大人性弱点指数 ====================

    public static class WeaknessIndexCalculator {

        /** 贪婪指数 G = ((E_d - E_r)/E_r) / (R_p * S) */
        public static double greedIndex(double expectedGainSelf, double expectedGainRational,
                                        double perceivedRisk, double riskAversion) {
            if (expectedGainRational <= 0 || perceivedRisk <= 0 || riskAversion <= 0) return Double.POSITIVE_INFINITY;
            double numerator = (expectedGainSelf - expectedGainRational) / expectedGainRational;
            return numerator / (perceivedRisk * riskAversion);
        }

        /** 恐惧指数 F = (L_d - L_r) / (E_r * T) */
        public static double fearIndex(double lossSelf, double lossRational, double expectedReturn, double timeHorizon) {
            if (expectedReturn <= 0 || timeHorizon <= 0) return Double.POSITIVE_INFINITY;
            return (lossSelf - lossRational) / (expectedReturn * timeHorizon);
        }

        /** 从众指数 H = (N_f * I) / (K * J) */
        public static double conformityIndex(double followerRatio, double groupInfluence,
                                             double independentThinking, double selfMatch) {
            if (independentThinking <= 0 || selfMatch <= 0) return Double.POSITIVE_INFINITY;
            return (followerRatio * groupInfluence) / (independentThinking * selfMatch);
        }

        /** 拖延指数 P = (D * C) / (R * (T_t/T_d)) */
        public static double procrastinationIndex(double taskDifficulty, double escapePleasure,
                                                  double longTermReward, double timeRemainingRatio) {
            if (longTermReward <= 0 || timeRemainingRatio <= 0) return Double.POSITIVE_INFINITY;
            return (taskDifficulty * escapePleasure) / (longTermReward * timeRemainingRatio);
        }

        /** 过度自信指数 O = (A_s - A_r) / (E * F_m) */
        public static double overconfidenceIndex(double selfAccuracy, double realAccuracy,
                                                 double experience, double failureFocus) {
            if (experience <= 0 || failureFocus <= 0) return Double.POSITIVE_INFINITY;
            return (selfAccuracy - realAccuracy) / (experience * failureFocus);
        }
    }

    // ==================== 4. 神经底层量化指标 ====================

    public static class NeuralMetrics {

        /** 延迟折扣价值 V = A / (1 + k*D) */
        public static double delayedValue(double rewardAmount, double discountK, double delayMonths) {
            return rewardAmount / (1.0 + discountK * delayMonths);
        }

        /** 前景价值（同prospectValue，但独立提供） */
        public static double prospectValue(double x, double lambdaLoss) {
            if (x >= 0) return Math.pow(x, 0.88);
            else return -lambdaLoss * Math.pow(-x, 0.88);
        }

        /** 冷热决策权重 w = 1/(1+exp(-(2.5*Z - 0.5)))，其中Z为标准化脑区间控制力 */
        public static double coldWeight(double zScore) {
            return 1.0 / (1.0 + Math.exp(-(2.5 * zScore - 0.5)));
        }

        /** 总决策价值 = w*V_cold + (1-w)*V_hot */
        public static double totalValue(double coldValue, double hotValue, double zScore) {
            double w = coldWeight(zScore);
            return w * coldValue + (1 - w) * hotValue;
        }
    }

    // ==================== 5. SEM因果模型（简化矩阵运算） ====================

    /**
     * 简化SEM：η = Bη + Γξ + ζ
     * 此处用二维示例：η = [自控力, 拖延行为], ξ = [前额叶功能, 皮质醇]
     * B为2x2，Γ为2x2，ζ为随机误差
     */
    public static class SEModel {
        private double[][] B; // 行为间影响
        private double[][] Gamma; // 生理到心理
        private double[] zeta; // 误差

        public SEModel(double[][] B, double[][] Gamma, double[] zeta) {
            this.B = B;
            this.Gamma = Gamma;
            this.zeta = zeta;
        }

        /** 计算内生变量 η = (I-B)^(-1) * (Γξ + ζ) */
        public double[] computeEta(double[] xi) {
            int n = B.length;
            double[][] IminusB = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    IminusB[i][j] = (i == j ? 1.0 : 0.0) - B[i][j];
                }
            }
            // 简单2x2求逆（假设非奇异）
            double det = IminusB[0][0] * IminusB[1][1] - IminusB[0][1] * IminusB[1][0];
            if (Math.abs(det) < 1e-12) throw new RuntimeException("矩阵奇异");
            double[][] inv = new double[n][n];
            inv[0][0] = IminusB[1][1] / det;
            inv[0][1] = -IminusB[0][1] / det;
            inv[1][0] = -IminusB[1][0] / det;
            inv[1][1] = IminusB[0][0] / det;

            // 计算 Γξ + ζ
            double[] rhs = new double[n];
            for (int i = 0; i < n; i++) {
                rhs[i] = zeta[i];
                for (int j = 0; j < Gamma[0].length; j++) {
                    rhs[i] += Gamma[i][j] * xi[j];
                }
            }
            // 乘逆矩阵
            double[] eta = new double[n];
            for (int i = 0; i < n; i++) {
                eta[i] = inv[i][0] * rhs[0] + inv[i][1] * rhs[1];
            }
            return eta;
        }

        /** 效应分解：总效应 = 直接 + 中介 (c = c' + a*b) 示例 */
        public static double totalEffect(double direct, double indirectA, double indirectB) {
            return direct + indirectA * indirectB;
        }
    }

    // ==================== 6. 贝叶斯动态更新（简化Beta分布） ====================

    public static class BayesianUpdater {
        private double alpha; // 成功/高特质计数
        private double beta;  // 失败/低特质计数

        public BayesianUpdater(double alpha, double beta) {
            this.alpha = alpha;
            this.beta = beta;
        }

        /** 观测到新的行为（1=高冲动/拖延，0=低） */
        public void update(boolean highTrait) {
            if (highTrait) alpha++;
            else beta++;
        }

        /** 后验期望概率 = alpha / (alpha + beta) */
        public double getPosteriorMean() {
            return alpha / (alpha + beta);
        }

        /** 预测未来30天发生概率（使用后验期望） */
        public double predictProbability() {
            return getPosteriorMean();
        }
    }

    // ==================== 7. UMHWM v2.0 数字孪生系统 ====================

    /**
     * 状态向量 X_t = [N, P, C, B, S]^T
     * N: 神经层 (例如前额叶控制力)
     * P: 心理特质 (例如冲动系数)
     * C: 认知偏差 (例如损失厌恶)
     * B: 弱点行为 (例如拖延行为强度)
     * S: 社会环境 (例如群体压力)
     */
    public static class DigitalTwin {
        private double[] state;          // X_t
        private double[][] A;            // 状态转移矩阵
        private double[][] Bmat;         // 干预控制矩阵
        private Map<String, Double> history; // 历史记录

        public DigitalTwin(double[] initialState, double[][] A, double[][] Bmat) {
            this.state = initialState.clone();
            this.A = A;
            this.Bmat = Bmat;
            this.history = new HashMap<>();
        }

        /** 状态转移 X_{t+1} = A*X_t + B*U_t + ω */
        public void step(double[] interventionU, double[] noiseOmega) {
            int n = state.length;
            double[] newState = new double[n];
            for (int i = 0; i < n; i++) {
                double sumA = 0.0;
                for (int j = 0; j < n; j++) {
                    sumA += A[i][j] * state[j];
                }
                double sumB = 0.0;
                if (interventionU != null && Bmat != null) {
                    for (int j = 0; j < interventionU.length; j++) {
                        sumB += Bmat[i][j] * interventionU[j];
                    }
                }
                double noise = (noiseOmega != null && i < noiseOmega.length) ? noiseOmega[i] : 0.0;
                newState[i] = sumA + sumB + noise;
            }
            this.state = newState;
            // 记录历史
            history.put("step_" + history.size(), state[0]); // 示例
        }

        /** 预测未来30天行为概率（基于当前状态） */
        public double predictBehaviorRisk(int behaviorIndex, double threshold) {
            // 简单示例：若行为层状态超过阈值则概率高
            if (behaviorIndex < 0 || behaviorIndex >= state.length) return 0.0;
            double value = state[behaviorIndex];
            // 使用sigmoid转换为概率
            return 1.0 / (1.0 + Math.exp(-(value - threshold)));
        }

        /** 获取当前状态 */
        public double[] getState() {
            return state.clone();
        }

        /** 存储个体完整孪生数据（简化） */
        public void saveTwinData() {
            // 实际可序列化到文件或数据库
            System.out.println("孪生数据已保存: " + history.size() + " 条记录");
        }
    }

    // ==================== 8. 主程序示例 ====================

    public static void main(String[] args) {
        // 示例：构建参数并计算U-DUF效用
        BioParams bio = new BioParams(0.42, 1.2, 15.0, 0.3, 70.0);
        PsychoParams psycho = new PsychoParams(0.7, 10000, 0.04, 12, 2.5, 0.8);
        SocialParams social = new SocialParams(0.9, 1.2, 50000);

        double utility = UDUFCalculator.computeSubjectiveUtility(bio, psycho, social, 1.0, true);
        double prob = UDUFCalculator.softmaxProbability(utility, 1.0);
        System.out.printf("主观效用: %.4f, 选择概率: %.4f\n", utility, prob);

        // 计算拖延指数
        double procrast = WeaknessIndexCalculator.procrastinationIndex(8, 9, 5, 0.1);
        System.out.printf("拖延指数: %.2f\n", procrast);

        // 神经指标
        double delayedVal = NeuralMetrics.delayedValue(1000, 0.12, 6);
        System.out.printf("延迟折扣价值: %.2f\n", delayedVal);

        // SEM示例
        double[][] B = {{0.1, 0.2}, {0.3, 0.1}};
        double[][] Gamma = {{0.5, 0.1}, {0.2, 0.4}};
        double[] zeta = {0.05, 0.03};
        SEModel sem = new SEModel(B, Gamma, zeta);
        double[] xi = {0.8, 0.6}; // 前额叶功能、皮质醇
        double[] eta = sem.computeEta(xi);
        System.out.printf("自控力: %.4f, 拖延行为: %.4f\n", eta[0], eta[1]);

        // 贝叶斯更新
        BayesianUpdater bayes = new BayesianUpdater(1, 1);
        bayes.update(true);
        bayes.update(false);
        System.out.printf("后验拖延概率: %.4f\n", bayes.predictProbability());

        // 数字孪生
        double[] initState = {0.4, 0.3, 2.5, 0.6, 0.8};
        double[][] A = {{0.9, 0.1, 0.0, 0.0, 0.0},
                {0.0, 0.8, 0.2, 0.0, 0.0},
                {0.0, 0.0, 0.7, 0.3, 0.0},
                {0.0, 0.0, 0.0, 0.6, 0.4},
                {0.1, 0.0, 0.0, 0.0, 0.9}};
        double[][] Bmat = {{1.0, 0.0}, {0.0, 1.0}, {0.0, 0.0}, {0.0, 0.0}, {0.0, 0.0}};
        DigitalTwin twin = new DigitalTwin(initState, A, Bmat);
        double[] intervention = {-0.1, 0.2}; // 外部干预
        double[] noise = {0.01, -0.01, 0.0, 0.0, 0.0};
        twin.step(intervention, noise);
        double risk = twin.predictBehaviorRisk(3, 0.5);
        System.out.printf("未来30天行为风险概率: %.4f\n", risk);
        twin.saveTwinData();

        System.out.println("模型运行完成。");
    }
}