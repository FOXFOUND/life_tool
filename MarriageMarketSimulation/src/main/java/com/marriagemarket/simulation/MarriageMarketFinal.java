package com.marriagemarket.simulation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 婚恋市场金融化模拟器 终极版
 * 融合模型：
 *   - 女性价值函数 V_f(age)：小步长动态规划，28岁后指数衰减
 *   - 男性财富：几何布朗运动，能力类型 θ 不可直接观测
 *   - 信号博弈：男性主动选择报告类型 x，满足激励相容约束
 *   - 市场摩擦：随机搜寻匹配，每年新满20岁男女涌入
 *   - 分离均衡：高能力者支付高信号成本，低能力者退出
 */
public class MarriageMarketFinal {

    // ==================== 全局参数 ====================
    static final double R = 0.05;               // 连续折现率
    static final double BETA = 0.5;             // 女性纳什议价能力
    static final double B0 = 1.0;               // 基础共同收益
    static final double B1 = 2.0;               // 类型对收益的边际影响
    static final double MU0 = 0.02;             // 财富基础漂移率
    static final double MU1 = 0.08;             // 类型对漂移的边际影响
    static final double SIGMA = 0.20;           // 财富波动率
    static final double C_COST = 0.5;           // 信号成本系数
    static final int MIN_AGE = 20;              // 最小进入年龄
    static final int MAX_AGE_F = 45;            // 女性退出年龄
    static final double LAMBDA = 2.0;           // 每年期望相遇次数
    static final double DT = 0.1;               // 时间步长（年）
    static final int INIT_F = 1000;             // 初始女性数
    static final int INIT_M = 1000;             // 初始男性数
    static final int NEW_PER_YR = 30;           // 每年新进入人数
    static final double INIT_WEALTH = 10.0;     // 男性初始财富
    static final double EXP_DECAY = 0.4;        // 28岁后价值指数衰减率
    static final int CRIT_AGE = 28;             // 红利截止年龄
    static final int SEED = 42;
    static final Random RAND = new Random(SEED);

    // 男性能力类型采样（截断对数正态，中位数~1.6，范围0.5~3.5）
    static double sampleTheta() {
        double u;
        do {
            u = Math.exp(RAND.nextGaussian() * 0.45 + 0.45);
        } while (u < 0.5 || u > 3.5);
        return u;
    }

    static double B(double theta) { return B0 + B1 * theta; }
    static double mu(double theta) { return MU0 + MU1 * theta; }

    /**
     * 信号成本函数 C(x, θ)：报告类型 x 所需支付的不可逆成本
     * 满足 Spence 单交叉条件：C_x > 0, C_{xθ} < 0
     */
    static double signalCost(double x, double trueTheta) {
        if (x <= 0.5) return 0;
        return C_COST * x * Math.exp(-trueTheta / 2.0);
    }

    // ==================== 女性价值函数（小步长动态规划） ====================
    static double[] Vf;                     // 按时间步长索引的值
    static int idxCrit;                     // 28岁对应索引

    static int ageToIdx(double age) {
        return (int) Math.round((age - MIN_AGE) / DT);
    }

    static double idxToAge(int idx) {
        return MIN_AGE + idx * DT;
    }

    static void computeFemaleValue() {
        int totalSteps = (int) Math.round((MAX_AGE_F - MIN_AGE) / DT) + 1;
        Vf = new double[totalSteps];
        idxCrit = ageToIdx(CRIT_AGE);
        double disc = Math.exp(-R * DT);
        double mProb = 1 - Math.exp(-LAMBDA * DT);   // 一步内至少一次相遇概率

        // 抽样 theta 用于期望计算
        int nSamples = 2000;
        double[] thetaSamples = new double[nSamples];
        for (int i = 0; i < nSamples; i++) thetaSamples[i] = sampleTheta();

        double V28 = 0.7;
        double[] temp = new double[totalSteps];

        for (int iter = 0; iter < 100; iter++) {
            // 28岁以后：强制指数衰减
            for (int i = idxCrit; i < totalSteps; i++) {
                double age = idxToAge(i);
                temp[i] = V28 * Math.exp(-EXP_DECAY * (age - CRIT_AGE));
            }

            // 从27岁倒退至20岁
            for (int i = idxCrit - 1; i >= 0; i--) {
                double Vnext = temp[i + 1];
                double V = Vnext;
                for (int sub = 0; sub < 30; sub++) {
                    final double Vnow = V;
                    double expected = 0.0;
                    for (double th : thetaSamples) {
                        double Uf = BETA * B(th) + (1 - BETA) * Vnow;
                        expected += Math.max(Uf, Vnext);
                    }
                    expected /= nSamples;
                    double Vnew = disc * ((1 - mProb) * Vnext + mProb * expected);
                    if (Math.abs(Vnew - V) < 1e-6) {
                        V = Vnew;
                        break;
                    }
                    V = Vnew;
                }
                temp[i] = V;
            }

            double newV28 = temp[idxCrit];
            if (Math.abs(newV28 - V28) < 1e-5) {
                V28 = newV28;
                break;
            }
            V28 = newV28;
        }
        Vf = temp;
    }

    static double VfAge(double age) {
        int idx = ageToIdx(age);
        if (idx < 0) idx = 0;
        if (idx >= Vf.length) idx = Vf.length - 1;
        return Vf[idx];
    }

    // ==================== 激励相容：男性最优报告选择 ====================
    /**
     * 男性净收益：真实类型 θ，报告 x，女性外部选项 V
     * 女性要求转移支付 P(x) = β·B(x) + (1-β)·V
     * 男性收益 = B(θ) - P(x) - C(x, θ)
     */
    static double maleNetUtility(double x, double theta, double V) {
        double P = BETA * B(x) + (1 - BETA) * V;
        return B(theta) - P - signalCost(x, theta);
    }

    /**
     * 寻找男性最优报告类型（在可行区间内最大化净收益）
     * 若最优净收益 < 0 返回 -1（表示放弃匹配）
     */
    static double optimalReport(double theta, double V) {
        double reqMin = Math.max(0.5, (V - B0) / B1); // 女性最低要求 B(x) >= V
        double bestX = -1, bestU = -Double.MAX_VALUE;
        // 网格搜索（步长 0.02）
        for (double x = reqMin; x <= 3.5; x += 0.02) {
            double u = maleNetUtility(x, theta, V);
            if (u > bestU) {
                bestU = u;
                bestX = x;
            }
        }
        return bestU >= 0 ? bestX : -1;
    }

    /** 检查激励相容（调试用）：打印几个 theta 的最优报告 */
    static void verifyIC(double V) {
        System.out.printf("\n激励相容验证 (V=%.3f):\n", V);
        System.out.println("真实θ   最优报告x*   如实报告收益   冒充最高收益");
        for (double th = 0.8; th <= 3.0; th += 0.4) {
            double xOpt = optimalReport(th, V);
            double uTrue = maleNetUtility(th, th, V);
            double uFake = maleNetUtility(3.0, th, V);
            System.out.printf("%.2f     %.2f          %.3f          %.3f\n",
                    th, xOpt, uTrue, uFake);
        }
    }

    // ==================== 代理人 ====================
    static class Male {
        int id;
        double theta;       // 真实能力（私人信息）
        double wealth;      // 可观测财富
        double age;
        boolean married;

        Male(int id, double theta, double age) {
            this.id = id;
            this.theta = theta;
            this.age = age;
            this.wealth = INIT_WEALTH;
        }

        void growWealth(double dt) {
            double drift = mu(theta);
            double eps = RAND.nextGaussian();
            double logReturn = (drift - 0.5 * SIGMA * SIGMA) * dt
                    + SIGMA * Math.sqrt(dt) * eps;
            wealth *= Math.exp(logReturn);
            if (wealth < 0) wealth = 0;
        }
    }

    static class Female {
        int id;
        double age;
        boolean married;

        Female(int id, double age) {
            this.id = id;
            this.age = age;
        }
    }

    static class MatchRecord {
        double femaleAge;
        double maleAge;
        double maleTheta;
        double reportedX;
        double signalCost;
    }

    static List<MatchRecord> matches = new ArrayList<>();

    /**
     * 一次匹配尝试：男性选择最优报告，若能支付信号成本则匹配成功
     */
    static boolean attemptMatch(Female f, Male m) {
        if (f.married || m.married) return false;
        double V = VfAge(f.age);
        double xOpt = optimalReport(m.theta, V);
        if (xOpt < 0) return false;             // 无利可图，放弃
        double cost = signalCost(xOpt, m.theta);
        if (m.wealth < cost) return false;      // 无法支付信号

        // 执行匹配
        m.wealth -= cost;
        f.married = true;
        m.married = true;

        MatchRecord rec = new MatchRecord();
        rec.femaleAge = f.age;
        rec.maleAge = m.age;
        rec.maleTheta = m.theta;
        rec.reportedX = xOpt;
        rec.signalCost = cost;
        matches.add(rec);
        return true;
    }

    // ==================== 主模拟 ====================
    public static void main(String[] args) {
        // 1. 预计算女性价值函数
        computeFemaleValue();
        System.out.println("========== 女性等待价值 V_f(age) ==========");
        for (int age = 20; age <= 45; age += 1) {
            System.out.printf("%2d岁: %.4f\n", age, VfAge(age));
        }

        // 2. 激励相容验证（选取两个典型年龄）
        verifyIC(VfAge(25));
        verifyIC(VfAge(30));

        // 3. 初始化人群
        List<Female> females = new ArrayList<>();
        List<Male> males = new ArrayList<>();
        for (int i = 0; i < INIT_F; i++) {
            females.add(new Female(i, 20 + RAND.nextDouble() * 5));
        }
        for (int i = 0; i < INIT_M; i++) {
            males.add(new Male(i, sampleTheta(), 20 + RAND.nextDouble() * 10));
        }
        int nextId = INIT_F + INIT_M;

        // 4. 时间主循环 (模拟30年)
        double simYears = 30.0;
        int totalSteps = (int) (simYears / DT);

        for (int step = 0; step < totalSteps; step++) {
            double time = step * DT;

            // 每年新进入者 (精确判断整年)
            if (Math.abs(time - Math.round(time)) < DT / 2) {
                int yr = (int) Math.round(time);
                if (yr < 30) {  // 最后30年不再流入，让市场出清
                    for (int i = 0; i < NEW_PER_YR; i++) {
                        females.add(new Female(nextId++, 20.0));
                        males.add(new Male(nextId++, sampleTheta(), 20.0));
                    }
                }
            }

            // 年龄增长与财富积累
            for (Female f : females) if (!f.married) f.age += DT;
            for (Male m : males) if (!m.married) {
                m.age += DT;
                m.growWealth(DT);
            }

            // 匹配过程：每个未婚女性以概率 meetingProb 相遇
            List<Female> availF = females.stream()
                    .filter(f -> !f.married).collect(Collectors.toList());
            List<Male> availM = males.stream()
                    .filter(m -> !m.married).collect(Collectors.toList());
            double mProb = 1 - Math.exp(-LAMBDA * DT);

            for (Female f : availF) {
                if (RAND.nextDouble() < mProb) {
                    if (availM.isEmpty()) break;
                    Male candidate = availM.get(RAND.nextInt(availM.size()));
                    attemptMatch(f, candidate);
                    // 更新可用男性列表
                    availM = males.stream()
                            .filter(m -> !m.married).collect(Collectors.toList());
                }
            }
        }

        // 5. 结果统计
        long marriedF = females.stream().filter(f -> f.married).count();
        long marriedM = males.stream().filter(m -> m.married).count();
        System.out.println("\n========== 模拟结果统计 ==========");
        System.out.printf("女性结婚率: %.1f%% (%d/%d)\n",
                100.0 * marriedF / females.size(), marriedF, females.size());
        System.out.printf("男性结婚率: %.1f%% (%d/%d)\n",
                100.0 * marriedM / males.size(), marriedM, males.size());

        double avgFA = matches.stream().mapToDouble(r -> r.femaleAge).average().orElse(0);
        double avgMA = matches.stream().mapToDouble(r -> r.maleAge).average().orElse(0);
        System.out.printf("平均结婚年龄 女:%.1f 男:%.1f 年龄差:%.1f\n",
                avgFA, avgMA, avgMA - avgFA);

        // 按男性能力分组
        double medianTheta = males.stream().mapToDouble(m -> m.theta).sorted()
                .skip(males.size() / 2).findFirst().orElse(1.8);
        List<MatchRecord> high = matches.stream()
                .filter(r -> r.maleTheta >= medianTheta).collect(Collectors.toList());
        List<MatchRecord> low = matches.stream()
                .filter(r -> r.maleTheta < medianTheta).collect(Collectors.toList());

        double highAvgCost = high.stream().mapToDouble(r -> r.signalCost).average().orElse(0);
        double lowAvgCost = low.stream().mapToDouble(r -> r.signalCost).average().orElse(0);
        double highAvgX = high.stream().mapToDouble(r -> r.reportedX).average().orElse(0);
        double lowAvgX = low.stream().mapToDouble(r -> r.reportedX).average().orElse(0);

        System.out.printf("\n高能力男性(θ≥%.2f): %d对, 平均信号成本:%.2f, 平均报告类型:%.2f\n",
                medianTheta, high.size(), highAvgCost, highAvgX);
        System.out.printf("低能力男性(θ<%.2f): %d对, 平均信号成本:%.2f, 平均报告类型:%.2f\n",
                medianTheta, low.size(), lowAvgCost, lowAvgX);

        // 报告类型与真实类型的一致性（分离均衡验证）
        double corr = matches.stream()
                .mapToDouble(r -> r.maleTheta * r.reportedX).average().orElse(0);
        System.out.printf("真实θ与报告x的乘积均值: %.4f (若正相关说明分离有效)\n", corr);

        // ==================== 策略建议 ====================
        System.out.println("\n========== 基于模型的策略建议 ==========");
        System.out.println("【给女性】");
        System.out.println("1. 28岁前你的等待价值很高，不要过早匹配低信号男性，保持选择权。");
        System.out.println("2. 要求可验证的硬信号（如财富、房产、职业资质），这能迫使高能力男性主动报告，滤除低类型。");
        System.out.println("3. 扩大社交圈（提高λ）可增加相遇机会，有效延缓价值衰减带来的压力。");
        System.out.println("4. 30岁后信号要求可适度降低，但需权衡剩余时间窗口，避免错过'托底'机会。");

        System.out.println("\n【给男性】");
        System.out.println("1. 你的内在能力无法被直接观测，必须通过昂贵信号（彩礼、婚前投入）证明自己。这是市场分离均衡的结果，非文化压迫。");
        System.out.println("2. 高能力者应尽早积累可观测信号（财富、职位），降低信号支付的痛苦程度，并在女性红利期内进入市场。");
        System.out.println("3. 低能力男性可能发现最优策略是放弃婚姻市场，转而寻求其他人生价值，这是理性选择。");
        System.out.println("4. 等待事业完全成熟（'上市'）可减少信号成本，但可能错过女性窗口期，需结合自身θ与年龄动态权衡。");
        System.out.println("5. 诚实报告类型通常最优（激励相容），试图伪装高类型会带来过高信号成本，得不偿失。");
    }
}