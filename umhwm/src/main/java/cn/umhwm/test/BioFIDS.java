package cn.umhwm.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿生非整数阶决策动力系统 (BioFIDS)
 * 基于 Caputo 分数阶导数的 L1 算法
 * 输出30天的纯文字决策日志
 */
public class BioFIDS {

    // ---------- 生理与心理固定参数 ----------
    static final double W_MAX = 100.0;          // 最大意志力容量
    static final double OMEGA = 10.0;           // 认知带宽
    static final double D2 = 0.8;               // 多巴胺 D2 受体可用性
    static final double K0 = 0.04;              // 基础延迟折扣系数
    static final double SIGMA0 = 1.2;           // 基础从众敏感度
    static final double LAMBDA_BASE = 2.5;      // 损失厌恶基准值

    // ---------- 各状态的分数阶阶次 (0~1) ----------
    static final double ALPHA_W = 0.9;          // 意志力恢复与消耗的长记忆
    static final double ALPHA_H = 0.85;         // 皮质醇代谢的慢动态
    static final double ALPHA_K = 0.9;          // 冲动性变化的路径依赖
    static final double ALPHA_LAMBDA = 0.95;    // 损失厌恶缓慢回归
    static final double ALPHA_SIGMA = 0.9;      // 从众敏感度的惯性
    static final double ALPHA_D = 0.8;          // 群体一致性感知的快速适应

    // ---------- 仿真设置 ----------
    static final double DT = 0.5;               // 步长 0.5 天
    static final double T_MAX = 30.0;           // 总仿真 30 天
    static final int STEPS = (int)(T_MAX / DT); // 总步数

    // ---------- 状态历史 (用于分数阶记忆) ----------
    List<Double> WHist = new ArrayList<>();      // 意志力
    List<Double> HHist = new ArrayList<>();      // 皮质醇
    List<Double> KHist = new ArrayList<>();      // 延迟折扣系数
    List<Double> LambdaHist = new ArrayList<>(); // 损失厌恶系数
    List<Double> SigmaHist = new ArrayList<>();  // 从众敏感度
    List<Double> DHist = new ArrayList<>();      // 感知群体一致性

    // ---------- 外部事件输入 (可随时间变化) ----------
    double deltaM = 8.0;          // 诱惑强度 (日常平均)
    double stressLevel = 2.0;     // 压力水平
    double rewardSalience = 0.6;  // 奖励凸显度 (多巴胺刺激)
    double lossShock = 0.5;       // 损失冲击强度
    double socialFeedback = 0.3;  // 社会反馈强度
    double dObserved = 0.5;       // 客观群体一致性

    public BioFIDS() {
        // 初始状态：一个休息良好、情绪稳定的成年人
        WHist.add(96.0);
        HHist.add(0.9);
        KHist.add(0.045);
        LambdaHist.add(2.6);
        SigmaHist.add(1.25);
        DHist.add(0.4);
    }

    /**
     * 计算 Gamma 函数对数值 (Stirling 近似，适用于 1~2 之间)
     */
    double logGamma(double x) {
        return 0.5 * Math.log(2 * Math.PI / x) + x * Math.log(x / Math.E);
    }

    /**
     * 显式递推计算 Caputo 分数阶微分方程下一时刻的值
     * 公式：x_{n+1} = x_n - Σ_{j=1}^{n} c_j (x_{n+1-j} - x_{n-j}) + dt^alpha * Gamma(2-alpha) * f_n
     */
    double computeNext(List<Double> history, double alpha, double dt, double f_n) {
        int n = history.size() - 1;
        double gammaVal = Math.exp(logGamma(2 - alpha));
        double dtAlpha = Math.pow(dt, alpha);
        double sum = 0.0;
        for (int j = 1; j <= n; j++) {
            double cj = Math.pow(j, 1 - alpha) - Math.pow(j - 1, 1 - alpha);
            sum += cj * (history.get(n - j + 1) - history.get(n - j));
        }
        return history.get(n) - sum + dtAlpha * gammaVal * f_n;
    }

    /**
     * 根据当前状态计算右侧函数 f(t,X)
     */
    double computeF_W(double W, double H, double k) {
        double Phi = (0.1 + Math.exp(0.05 * (W_MAX - W))) / OMEGA;
        return -0.01 * Phi * deltaM + 0.02 * (W_MAX - W);
    }

    double computeF_H(double H) {
        return -0.3 * H + 0.5 * stressLevel;
    }

    double computeF_K(double k) {
        return -0.05 * (k - K0) + 0.1 * D2 * rewardSalience;
    }

    double computeF_Lambda(double lambda) {
        return -0.02 * (lambda - LAMBDA_BASE) + 0.05 * lossShock;
    }

    double computeF_Sigma(double sigma) {
        return -0.1 * (sigma - SIGMA0) + 0.2 * socialFeedback;
    }

    double computeF_D(double d) {
        return 0.1 * (dObserved - d);
    }

    /**
     * 仿真一步：计算下一时刻所有状态并加入历史
     */
    void step() {
        int n = WHist.size() - 1;
        double W = WHist.get(n);
        double H = HHist.get(n);
        double k = KHist.get(n);
        double lambda = LambdaHist.get(n);
        double sigma = SigmaHist.get(n);
        double d = DHist.get(n);

        double fW = computeF_W(W, H, k);
        double fH = computeF_H(H);
        double fK = computeF_K(k);
        double fL = computeF_Lambda(lambda);
        double fS = computeF_Sigma(sigma);
        double fD = computeF_D(d);

        WHist.add(computeNext(WHist, ALPHA_W, DT, fW));
        HHist.add(computeNext(HHist, ALPHA_H, DT, fH));
        KHist.add(computeNext(KHist, ALPHA_K, DT, fK));
        LambdaHist.add(computeNext(LambdaHist, ALPHA_LAMBDA, DT, fL));
        SigmaHist.add(computeNext(SigmaHist, ALPHA_SIGMA, DT, fS));
        DHist.add(computeNext(DHist, ALPHA_D, DT, fD));
    }

    /**
     * 计算五大弱点指数 (基于最新状态和一些情境常数)
     */
    double[] computeIndices() {
        int last = WHist.size() - 1;
        double W = WHist.get(last);
        double H = HHist.get(last);
        double k = KHist.get(last);
        double lambda = LambdaHist.get(last);
        double sigma = SigmaHist.get(last);
        double d = DHist.get(last);

        // ----- 贪婪指数 G = ((Ed - Er)/Er) / (Rp * S) -----
        double Ed = 0.6 + 0.1 * k + 0.05 * d;   // 主观期望收益，受冲动和从众影响
        double Er = 0.4;
        double Rp = 0.3;
        double S = 0.8 + 0.1 * (3.0 - W / 33.0); // 对风险的在意程度，意志力低时更不敏感
        double G = ((Ed - Er) / Er) / (Rp * S);
        if (G < 0) G = 0;
        if (G > 50) G = 50;  // 限制范围避免过大

        // ----- 恐惧指数 F = (Ld - Lr) / (Er * T) -----
        double Ld = 0.2 + 0.01 * H + 0.05 * lambda; // 主观最大亏损，受皮质醇和损失厌恶放大
        double Lr = 0.2;
        double T = 1.0;  // 观察时长，后续可动态调整
        double F = (Ld - Lr) / (Er * T);
        if (F < 0) F = 0;

        // ----- 从众指数 H_idx = (Nf * I) / (K * J) -----
        double Nf = d;                   // 感知跟风比例
        double I = 0.8 + 0.2 * sigma;    // 影响力，被从众敏感度放大
        double K = 0.5;                  // 独立思考能力
        double J = 0.4;                  // 需求匹配度
        double HIdx = (Nf * I) / (K * J);
        if (HIdx > 40) HIdx = 40;

        // ----- 拖延指数 P = (D * C) / (R * (Tt / Td)) -----
        double D_tmp = 6.0 + 0.5 * H;            // 任务主观难度，皮质醇高时更难
        double C_tmp = 7.0 + 0.3 * k;            // 逃避快感，冲动性高时更强
        double R_tmp = 4.0;                      // 长远回报
        double Td = 30.0;                        // 总截止时间
        double Tt = Td - (last * DT);            // 剩余时间
        if (Tt < 1) Tt = 1;
        double P = (D_tmp * C_tmp) / (R_tmp * (Tt / Td));
        if (P > 50) P = 50;

        // ----- 过度自信指数 O = (As - Ar) / (E * Fm) -----
        double As = 0.65 + 0.05 * k;             // 自评准确率，冲动者更自信
        double Ar = 0.55;
        double E = 8.0;                          // 经验年数
        double Fm = 0.3;                         // 反思程度
        double O = (As - Ar) / (E * Fm);
        if (O < 0) O = 0;

        return new double[]{G, F, HIdx, P, O};
    }

    /**
     * 打印当天状态与弱点指数的纯文字日志
     */
    void printDailyLog(int step, double time) {
        int last = step;
        double W = WHist.get(last);
        double H = HHist.get(last);
        double k = KHist.get(last);
        double lambda = LambdaHist.get(last);
        double sigma = SigmaHist.get(last);
        double d = DHist.get(last);
        double[] idx = computeIndices();

        System.out.printf("第 %.1f 天", time);
        if (step == 0) System.out.print(" (初始)");
        System.out.println();
        System.out.printf("意志力 %.2f  皮质醇 %.2f  延迟折扣 %.3f  损失厌恶 %.2f  从众敏感 %.2f  群体一致 %.2f\n",
                W, H, k, lambda, sigma, d);
        System.out.println("———— 五大弱点指数 ————");
        System.out.printf("贪婪 %.2f  恐惧 %.2f  从众 %.2f  拖延 %.2f  过度自信 %.2f\n",
                idx[0], idx[1], idx[2], idx[3], idx[4]);
        System.out.println("——————————————\n");
    }

    /**
     * 在特定时间点施加外部事件冲击（修改输入变量）
     */
    void applyEvents(double time) {
        // 重置到日常基线
        deltaM = 8.0;
        stressLevel = 2.0;
        rewardSalience = 0.6;
        lossShock = 0.5;
        socialFeedback = 0.3;
        dObserved = 0.5;

        // 第5天：季度汇报高压事件
        if (time >= 5.0 && time < 6.5) {
            stressLevel = 4.5;
            deltaM = 12.0;
            lossShock = 1.5;
            dObserved = 0.75;   // 周围人都在加班
        }
        // 第12天：直播间秒杀事件
        if (time >= 12.0 && time < 12.6) {
            rewardSalience = 2.0;   // 高多巴胺刺激
            dObserved = 0.92;       // 大量人群购买
            socialFeedback = 1.0;
            deltaM = 14.0;
        }
        // 第15天：后悔反思（社会反馈变负）
        if (time >= 15.0 && time < 16.0) {
            socialFeedback = -0.8;
            lossShock = 1.2;       // 金钱损失的痛苦
        }
        // 第20天：股市大跌恐慌
        if (time >= 20.0 && time < 21.5) {
            stressLevel = 5.0;
            lossShock = 2.5;
            dObserved = 0.85;      // 恐慌抛售蔓延
            deltaM = 4.0;
        }
        // 第25天后：正念干预与环境调整
        if (time >= 25.0) {
            // 干预措施体现为参数缓慢改善，主要由系统自恢复主导
            stressLevel = 1.5;
            rewardSalience = 0.3;
            socialFeedback = -0.2; // 减少社交影响
            dObserved = 0.35;
            deltaM = 5.0;
        }
    }

    public static void main(String[] args) {
        BioFIDS sim = new BioFIDS();

        System.out.println("=== 仿生数字孪生演化日志：陈宇的30天决策动态 ===\n");
        for (int i = 0; i <= STEPS; i++) {
            double t = i * DT;
            sim.applyEvents(t);
            sim.printDailyLog(i, t);
            if (i < STEPS) {
                sim.step();
            }
        }
        System.out.println("程序运行结束。数字孪生演化完成。");
    }
}
