package cn.umhwm.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿生非整数阶决策动力系统 (BioFIDS) 仿真器
 * 使用 Caputo 分数阶导数的 L1 数值算法
 */
public class BioFIDS {

    // ---------- 固定参数 ----------
    static final double W_MAX = 100.0;
    static final double OMEGA = 10.0;          // 认知带宽
    static final double D2 = 0.8;              // 多巴胺 D2 受体可用性
    static final double k0 = 0.04;             // 基础延迟折扣
    static final double SIGMA0 = 1.2;          // 基础从众敏感度

    // 分数阶阶次
    static final double ALPHA_W = 0.9;
    static final double ALPHA_H = 0.85;
    static final double ALPHA_K = 0.9;
    static final double ALPHA_LAMBDA = 0.95;
    static final double ALPHA_SIGMA = 0.9;
    static final double ALPHA_D = 0.8;

    // 仿真步长与总时间
    static final double DT = 0.1;               // 步长 (天)
    static final double T_MAX = 30.0;           // 总仿真 30 天
    static final int STEPS = (int)(T_MAX / DT);

    // 状态历史（用于 L1 分数阶导数计算）
    List<Double> W_hist = new ArrayList<>();
    List<Double> H_hist = new ArrayList<>();
    List<Double> k_hist = new ArrayList<>();
    List<Double> lambda_hist = new ArrayList<>();
    List<Double> sigma_hist = new ArrayList<>();
    List<Double> d_hist = new ArrayList<>();

    // 当前外部输入 (可随时间变化)
    double deltaM = 8.0;         // 诱惑强度
    double stress = 2.0;
    double rewardSalience = 0.6;
    double lossShock = 0.5;
    double socialFeedback = 0.3;
    double dObs = 0.75;

    public BioFIDS() {
        // 初始条件：基于原文的典型值
        W_hist.add(95.0);        // 清晨充满电
        H_hist.add(1.2);
        k_hist.add(0.05);
        lambda_hist.add(2.8);
        sigma_hist.add(1.3);
        d_hist.add(0.5);
    }

    /**
     * 计算 L1 近似下的 Caputo 分数阶导数 D^alpha x(t_{n+1})
     * 使用公式：D^alpha x(t_{n+1}) ≈ (DT^{-alpha}/Gamma(2-alpha)) *
     *   [ (x_{n+1} - x_n) * a_0 + Σ_{j=1}^{n} (x_{n+1-j} - x_{n-j}) * a_j ]
     * 其中 a_j = (j+1)^{1-alpha} - j^{1-alpha}
     *
     * @param hist   历史序列 (x0, x1, ..., xn)
     * @param alpha  分数阶次
     * @param dt     步长
     * @param xnPlus1 待求的 x_{n+1} (用于隐式求解，此处用显式预测)
     * @return D^alpha x(t_{n+1}) 的近似值
     */
    double caputoDerivativeL1(List<Double> hist, double alpha, double dt, double xnPlus1) {
        int n = hist.size() - 1;  // 历史包含 x0...xn, n为当前步
        double gamma = Math.exp(logGamma(2 - alpha)); // Gamma(2-alpha) 用 Stirling 近似
        double coeff = Math.pow(dt, -alpha) / gamma;
        double sum = 0.0;
        // j=0 项 (x_{n+1} - x_n) * a_0
        double a0 = 1.0 - Math.pow(0, 1 - alpha); // (1)^{1-alpha} - 0^{1-alpha} = 1
        sum += (xnPlus1 - hist.get(n)) * a0;
        // 累加历史项 j=1..n
        for (int j = 1; j <= n; j++) {
            double aj = Math.pow(j + 1, 1 - alpha) - Math.pow(j, 1 - alpha);
            double diff = hist.get(n - j + 1) - hist.get(n - j);
            sum += diff * aj;
        }
        return coeff * sum;
    }

    // Gamma 函数对数 (Stirling 近似，针对 1~2 之间的自变量已足够)
    double logGamma(double x) {
        // 简单的近似: Gamma(x) ≈ sqrt(2*pi/x) * (x/e)^x
        return 0.5 * Math.log(2 * Math.PI / x) + x * Math.log(x / Math.E);
    }

    /**
     * 核心：一步仿真迭代（显式预测）
     * 根据当前历史计算下一时刻所有状态，并更新历史
     */
    void step() {
        int n = W_hist.size() - 1;
        double W_n = W_hist.get(n);
        double H_n = H_hist.get(n);
        double k_n = k_hist.get(n);
        double lambda_n = lambda_hist.get(n);
        double sigma_n = sigma_hist.get(n);
        double d_n = d_hist.get(n);

        // ---- 计算右侧函数 f(t_n, X_n) ----
        // 意志力消耗项中的 Phi
        double Phi = (0.1 + Math.exp(0.05 * (W_MAX - W_n))) / OMEGA;
        double f_W = -0.01 * Phi * deltaM + 0.02 * (W_MAX - W_n);
        double f_H = -0.3 * H_n + 0.5 * stress;
        double f_k = -0.05 * (k_n - k0) + 0.1 * D2 * rewardSalience;
        double f_lambda = -0.02 * (lambda_n - 2.5) + 0.05 * lossShock;
        double f_sigma = -0.1 * (sigma_n - SIGMA0) + 0.2 * socialFeedback;
        double f_d = 0.1 * (dObs - d_n);

        // ---- 用显式预测法求 x_{n+1} ----
        // 从 Caputo 导数定义： D^alpha x = f  ->  (通过 L1) 可推出递推式。
        // 简化：使用公式 x_{n+1} = x_n + ... 但需解方程。
        // 这里采用一种简单策略：预测下一个值 x_{n+1}，用历史导数进行外推。
        // 对于 Caputo 导数显式递推:
        // x_{n+1} = x_n - Σ_{j=1}^{n} c_j (x_{n+1-j} - x_{n-j}) + DT^{alpha} Gamma(2-alpha) f
        // 其中 c_j = j^{1-alpha} - (j-1)^{1-alpha}, 注意 a0=1.
        double gammaW = Math.exp(logGamma(2 - ALPHA_W));
        double gammaH = Math.exp(logGamma(2 - ALPHA_H));
        double gammaK = Math.exp(logGamma(2 - ALPHA_K));
        double gammaL = Math.exp(logGamma(2 - ALPHA_LAMBDA));
        double gammaS = Math.exp(logGamma(2 - ALPHA_SIGMA));
        double gammaD = Math.exp(logGamma(2 - ALPHA_D));

        double W_next = computeNextCaputo(W_hist, ALPHA_W, DT, gammaW, f_W);
        double H_next = computeNextCaputo(H_hist, ALPHA_H, DT, gammaH, f_H);
        double k_next = computeNextCaputo(k_hist, ALPHA_K, DT, gammaK, f_k);
        double lambda_next = computeNextCaputo(lambda_hist, ALPHA_LAMBDA, DT, gammaL, f_lambda);
        double sigma_next = computeNextCaputo(sigma_hist, ALPHA_SIGMA, DT, gammaS, f_sigma);
        double d_next = computeNextCaputo(d_hist, ALPHA_D, DT, gammaD, f_d);

        // 更新历史
        W_hist.add(W_next);
        H_hist.add(H_next);
        k_hist.add(k_next);
        lambda_hist.add(lambda_next);
        sigma_hist.add(sigma_next);
        d_hist.add(d_next);
    }

    /**
     * 根据 Caputo 导数的显式递推公式计算下一时刻的值
     * 公式: x_{n+1} = x_n - Σ_{j=1}^{n} c_j (x_{n+1-j} - x_{n-j}) + dt^alpha * Gamma(2-alpha) * f_n
     * c_j = j^{1-alpha} - (j-1)^{1-alpha}
     */
    double computeNextCaputo(List<Double> hist, double alpha, double dt, double gammaVal, double f_n) {
        int n = hist.size() - 1;
        double sum = 0.0;
        for (int j = 1; j <= n; j++) {
            double cj = Math.pow(j, 1 - alpha) - Math.pow(j - 1, 1 - alpha);
            sum += cj * (hist.get(n - j + 1) - hist.get(n - j));
        }
        double dtAlpha = Math.pow(dt, alpha);
        return hist.get(n) - sum + dtAlpha * gammaVal * f_n;
    }

    // 计算五大弱点指数（基于当前最新状态）
    double[] weaknessIndices() {
        int n = W_hist.size() - 1;
        double W = W_hist.get(n);
        double H = H_hist.get(n);
        double k = k_hist.get(n);
        double lambda = lambda_hist.get(n);
        double sigma = sigma_hist.get(n);
        double d = d_hist.get(n);

        // 这些是根据状态变量直接或间接计算出的瞬时输入（示例常数）
        double E_d = 0.7;   // 主观成功概率（由过度自信等决定，可进一步建模）
        double E_r = 0.4;
        double R_p = 0.3;   // 感知风险
        double S = 0.9;     // 对风险的在意程度
        double G = ((E_d - E_r) / E_r) / (R_p * S);

        double L_d = 0.5;   // 主观最大亏损
        double L_r = 0.2;
        double T = 1.0;     // 观察时长（年）
        double F = (L_d - L_r) / (E_r * T);

        double N_f = 0.8;   // 跟风比例
        double I = 0.9;     // 影响力
        double K = 0.5;     // 独立思考能力
        double J = 0.4;     // 需求匹配度
        double H_idx = (N_f * I) / (K * J);

        // 拖延指数：D任务难度, C逃避快感, R长远回报, T_t剩余时间, T_d总截止时间
        double D_tmp = 7.0;
        double C_tmp = 8.0;
        double R_tmp = 4.0;
        double T_t = 20;    // 剩余时间 (天)
        double T_d = 30;    // 总时间
        double P_idx = (D_tmp * C_tmp) / (R_tmp * (T_t / T_d));

        double A_s = 0.75;  // 自评准确率
        double A_r = 0.55;
        double E = 8.0;     // 经验年数
        double F_m = 0.3;   // 反思程度
        double O = (A_s - A_r) / (E * F_m);

        return new double[]{G, F, H_idx, P_idx, O};
    }

    public static void main(String[] args) {
        BioFIDS sim = new BioFIDS();
        System.out.println("时间(天)\tW\tHcort\tk\tlambda\tsigma\td\tG\tF\tH\tP\tO");
        for (int i = 0; i <= STEPS; i++) {
            double t = i * DT;
            double[] state = {
                    sim.W_hist.get(i), sim.H_hist.get(i), sim.k_hist.get(i),
                    sim.lambda_hist.get(i), sim.sigma_hist.get(i), sim.d_hist.get(i)
            };
            double[] indices = sim.weaknessIndices();
            System.out.printf("%.1f\t%.2f\t%.2f\t%.3f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\t%.2f\n",
                    t, state[0], state[1], state[2], state[3], state[4], state[5],
                    indices[0], indices[1], indices[2], indices[3], indices[4]);
            if (i < STEPS) sim.step();
        }
    }
}