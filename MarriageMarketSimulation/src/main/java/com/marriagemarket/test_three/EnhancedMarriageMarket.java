package com.marriagemarket.test_three;

import java.util.*;
import java.util.stream.*;

// ============== 全局参数 (已微调) ==============
class Params {
    // 基础时间与年龄
    double r = 0.05, dt = 0.5, minAge = 20.0, maxFemaleAge = 45.0;
    int ageSteps;

    // 市场摩擦
    double lambda = 2.5;              // 微调：基准相遇强度略降，延长搜索时间

    // 匹配与议价
    double B = 10.0;                  // 保留，但实际使用 Y_max 替代
    double beta = 0.4;                // 女性议价能力

    // 男性能力分布
    double thetaMin = 0.5, thetaMax = 5.0;
    double thetaMu = 0.8, thetaSigma = 0.6;

    // 男性财富动态
    double muBase = 0.02, muScale = 0.03, sigmaW = 0.2, initWealthMale = 1.0;

    // 女性属性
    double initBeautyMean = 6.0, initBeautyStd = 1.5;
    double initIncomeMean = 2.0, initIncomeStd = 0.8;
    double initEduMean = 3.0, initEduStd = 1.0;
    double beautyDecay = 0.08, incomeGrowth = 0.02;

    // 信号与彩礼
    double signalCoeff = 0.3, signalWealthRatio = 0.4;

    // 匹配阈值
    double baseThreshold = 1.6;

    // 扩展机制：离婚、挖墙脚、攀比、信息歪曲
    double divorceFixedCost = 0.5;
    double poachProb = 0.3;
    double gamma = 0.25;
    double infoNoiseStd = 0.5;
    boolean useMarketTightness = true;

    // 去责任化与婚姻净产出
    double Y_max = 10.0;              // 完全责任时婚姻净产出
    double rho = 0.8;                 // 责任承担系数 (0~1)，越小越去责任化
    double eta = 0.5;                 // 净产出对责任的弹性

    // 人口动态
    int newMalesPerStep = 20, newFemalesPerStep = 20;

    public Params() {
        this.ageSteps = (int) ((maxFemaleAge - minAge) / dt) + 1;
    }
}

// ============== 个体类 ==============
class Male {
    int id;
    double theta, wealth;
    boolean married;
    int spouseId = -1;
    double P_current, signalSpent;
    int ageStep;
    int remarriageCount = 0;
    double reportedTheta;  // 信息歪曲下的公开类型

    public Male(int id, double theta, double wealth, int ageStep) {
        this.id = id;
        this.theta = theta;
        this.wealth = wealth;
        this.ageStep = ageStep;
    }

    public double realAge(Params p) {
        return p.minAge + ageStep * p.dt;
    }
}

class Female {
    int id;
    int ageStep;
    double beauty, income, education;
    boolean married;
    int spouseId = -1;
    double P_received;
    int marriageCount = 0;
    boolean wasPoached = false;
    double utility;

    public Female(int id, int ageStep, double b, double i, double e) {
        this.id = id;
        this.ageStep = ageStep;
        beauty = b;
        income = i;
        education = e;
    }

    public double realAge(Params p) {
        return p.minAge + ageStep * p.dt;
    }

    public double attractiveness() {
        return 0.5 * beauty + 0.3 * education + 0.2 * Math.log(1 + income);
    }

    public void ageOneStep(Params p) {
        double age = realAge(p);
        if (age > 25) beauty = Math.max(1.0, beauty - p.beautyDecay * p.dt * beauty);
        income *= (1 + p.incomeGrowth * p.dt);
    }
}

// ============== 增强模拟器 (整合去责任化) ==============
public class EnhancedMarriageMarket {
    Params p;
    Random rand = new Random(42);
    List<Male> males = new ArrayList<>();
    List<Female> females = new ArrayList<>();
    int maleId = 0, femaleId = 0;

    // 统计变量
    int totalMatches = 0, totalPoaches = 0, totalDivorces = 0;
    double sumSignal = 0, sumWealthAtMatch = 0, sumAgeAtMatch = 0;
    int highThetaRemarriages = 0;
    int elderFemaleAbandoned = 0;
    List<Double> femaleUtilities = new ArrayList<>();

    double equilibriumY;  // 内生婚姻净产出

    public EnhancedMarriageMarket(Params p) {
        this.p = p;
        computeEquilibrium();
    }

    // 根据去责任化参数计算婚姻净产出 Y
    void computeEquilibrium() {
        equilibriumY = p.Y_max * Math.pow(p.rho, p.eta);
    }

    void initPop(int initM, int initF) {
        for (int i = 0; i < initM; i++) {
            double th = Math.exp(p.thetaMu + p.thetaSigma * rand.nextGaussian());
            th = Math.min(p.thetaMax, Math.max(p.thetaMin, th));
            males.add(new Male(maleId++, th, p.initWealthMale, rand.nextInt(10)));
        }
        for (int i = 0; i < initF; i++) {
            double b = clamp(1, 10, p.initBeautyMean + p.initBeautyStd * rand.nextGaussian());
            double in = Math.max(0.5, p.initIncomeMean + p.initIncomeStd * rand.nextGaussian());
            double ed = clamp(1, 5, p.initEduMean + p.initEduStd * rand.nextGaussian());
            females.add(new Female(femaleId++, rand.nextInt(10), b, in, ed));
        }
    }

    double clamp(double lo, double hi, double x) {
        return Math.max(lo, Math.min(hi, x));
    }

    void injectNewcomers() {
        for (int i = 0; i < p.newMalesPerStep; i++) {
            double th = Math.exp(p.thetaMu + p.thetaSigma * rand.nextGaussian());
            th = Math.min(p.thetaMax, Math.max(p.thetaMin, th));
            males.add(new Male(maleId++, th, p.initWealthMale, 0));
        }
        for (int i = 0; i < p.newFemalesPerStep; i++) {
            double b = clamp(1, 10, p.initBeautyMean + p.initBeautyStd * rand.nextGaussian());
            double in = Math.max(0.5, p.initIncomeMean + p.initIncomeStd * rand.nextGaussian());
            double ed = clamp(1, 5, p.initEduMean + p.initEduStd * rand.nextGaussian());
            females.add(new Female(femaleId++, 0, b, in, ed));
        }
    }

    // 调整后的女性单身价值（降低基础常数，加大年龄坡度）
    double femaleValue(Female f) {
        double age = f.realAge(p);
        double remaining = Math.max(0, p.maxFemaleAge - age);
        double attractiveness = f.attractiveness();
        double attrFactor = 0.6 + 0.4 * attractiveness / 5.0;
        return (0.8 + 0.25 * remaining) * attrFactor;
    }

    double maleValue(Male m) {
        return Math.max(0, m.wealth * 0.1 + m.theta * 0.3);
    }

    double computeSignal(Male m) {
        double affordable = m.wealth * p.signalWealthRatio;
        double desired = Math.sqrt(2 * p.signalCoeff * m.theta * 2.0);
        return Math.min(desired, affordable);
    }

    // 匹配剩余与彩礼计算，使用内生 Y
    double bargainP(Male m, Female f) {
        double Vf = femaleValue(f);
        double Vm = maleValue(m);
        double S = equilibriumY - Vf - Vm + f.attractiveness() * 0.2;
        if (S <= 0) return -1;
        return Vf + p.beta * S;
    }

    double comparisonUtil(double spouseTheta, double avgMarriedTheta) {
        return p.gamma * (spouseTheta - avgMarriedTheta);
    }

    double reportWithNoise(double trueTheta) {
        if (p.infoNoiseStd <= 0) return trueTheta;
        return trueTheta + rand.nextGaussian() * p.infoNoiseStd;
    }

    public void simulate(int steps) {
        initPop(50000, 50000);
        for (int step = 0; step < steps; step++) {
            // 年龄与属性更新
            for (Female f : females) {
                if (!f.married) f.ageStep++;
                f.ageOneStep(p);
            }
            for (Male m : males) {
                m.ageStep++;
                if (!m.married) {
                    double mu = p.muBase + p.muScale * m.theta;
                    m.wealth += m.wealth * (mu * p.dt + p.sigmaW * Math.sqrt(p.dt) * rand.nextGaussian());
                    m.wealth = Math.max(0.1, m.wealth);
                } else {
                    double mu = p.muBase + p.muScale * m.theta;
                    m.wealth += m.wealth * (mu * p.dt + p.sigmaW * Math.sqrt(p.dt) * rand.nextGaussian());
                    m.wealth -= 0.05 * m.P_current * p.dt;  // 彩礼分期支付
                    m.wealth = Math.max(0.1, m.wealth);
                    m.reportedTheta = reportWithNoise(m.theta);
                }
            }
            // 高龄单身女性退出市场
            females.removeIf(f -> !f.married && f.realAge(p) > p.maxFemaleAge);
            injectNewcomers();

            double avgMarriedTheta = males.stream()
                    .filter(m -> m.married)
                    .mapToDouble(m -> m.theta)
                    .average().orElse(2.5);

            List<Male> singleM = males.stream().filter(m -> !m.married).collect(Collectors.toList());
            List<Female> singleF = females.stream().filter(f -> !f.married).collect(Collectors.toList());
            List<Male> marriedM = males.stream().filter(m -> m.married).collect(Collectors.toList());

            // 市场紧度外部性
            double tightness = singleF.isEmpty() ? 1.0 : (double) singleM.size() / singleF.size();
            double effLambda = p.useMarketTightness ? p.lambda * Math.min(tightness, 2.0) : p.lambda;

            // 1. 单身匹配
            int meetings = (int) (effLambda * p.dt * Math.min(singleM.size(), singleF.size()));
            for (int k = 0; k < meetings; k++) {
                if (singleM.isEmpty() || singleF.isEmpty()) break;
                Male m = singleM.get(rand.nextInt(singleM.size()));
                Female f = singleF.get(rand.nextInt(singleF.size()));
                double P_offer = bargainP(m, f);
                if (P_offer < 0) continue;
                double effectiveQuality = m.theta + 0.3 * m.signalSpent;
                double rawThreshold = p.baseThreshold + 0.05 * f.ageStep;
                double adjustedThreshold = rawThreshold - p.gamma * (m.theta - avgMarriedTheta);
                if (effectiveQuality >= adjustedThreshold) {
                    m.married = true;
                    f.married = true;
                    m.spouseId = f.id;
                    f.spouseId = m.id;
                    m.P_current = P_offer;
                    f.P_received = P_offer;
                    m.signalSpent = computeSignal(m);
                    m.wealth -= m.signalSpent;
                    f.marriageCount++;
                    totalMatches++;
                    sumSignal += m.signalSpent;
                    sumWealthAtMatch += m.wealth;
                    sumAgeAtMatch += f.realAge(p);
                    singleM.remove(m);
                    singleF.remove(f);
                }
            }

            // 2. 挖墙脚
            if (p.poachProb > 0 && !singleF.isEmpty() && !marriedM.isEmpty()) {
                int poachAttempts = (int) (effLambda * p.dt * Math.min(marriedM.size(), singleF.size()) * p.poachProb);
                for (int k = 0; k < poachAttempts; k++) {
                    if (singleF.isEmpty() || marriedM.isEmpty()) break;
                    Male m = marriedM.get(rand.nextInt(marriedM.size()));
                    Female newF = singleF.get(rand.nextInt(singleF.size()));
                    Female oldF = females.stream().filter(f -> f.id == m.spouseId).findFirst().orElse(null);
                    if (oldF == null) continue;
                    double Vf_new = femaleValue(newF);
                    double Vf_old = femaleValue(oldF);
                    double compensation = Vf_old;
                    double totalCost = p.divorceFixedCost + compensation;
                    double reportedTheta = m.reportedTheta;
                    double P_offer_new = bargainP(new Male(-1, reportedTheta, m.wealth, 0), newF);
                    if (P_offer_new < 0) continue;
                    if (m.P_current - totalCost >= Vf_new) {
                        oldF.married = false;
                        oldF.spouseId = -1;
                        oldF.wasPoached = true;
                        if (oldF.realAge(p) > 35) elderFemaleAbandoned++;
                        m.spouseId = newF.id;
                        newF.spouseId = m.id;
                        newF.married = true;
                        newF.marriageCount++;
                        m.P_current = P_offer_new;
                        newF.P_received = P_offer_new;
                        m.remarriageCount++;
                        if (m.theta > 3.5) highThetaRemarriages++;
                        totalPoaches++;
                        singleF.remove(newF);
                    }
                }
            }

            // 3. 自发离婚
            for (Male m : new ArrayList<>(males)) {
                if (!m.married) continue;
                Female wife = females.stream().filter(f -> f.id == m.spouseId).findFirst().orElse(null);
                if (wife == null) continue;
                double VfWife = femaleValue(wife);
                double util = wife.P_received + (p.gamma > 0 ? comparisonUtil(m.theta, avgMarriedTheta) : 0);
                if (util < VfWife - 0.2) {
                    wife.married = false;
                    wife.spouseId = -1;
                    if (wife.realAge(p) > 35) elderFemaleAbandoned++;
                    m.married = false;
                    m.spouseId = -1;
                    totalDivorces++;
                }
            }
        }

        // 输出统计
        System.out.println("=== 增强模型结果 (rho=" + p.rho + ", D=" + p.divorceFixedCost
                + ", gamma=" + p.gamma + ", infoNoise=" + p.infoNoiseStd + ") ===");
        System.out.printf("婚姻净产出 Y = %.2f%n", equilibriumY);
        System.out.printf("初婚: %d, 挖墙脚: %d, 离婚: %d%n", totalMatches, totalPoaches, totalDivorces);
        System.out.printf("平均彩礼: %.2f, 匹配平均财富: %.2f%n",
                totalMatches > 0 ? sumSignal / totalMatches : 0,
                totalMatches > 0 ? sumWealthAtMatch / totalMatches : 0);
        System.out.printf("女性初婚平均年龄: %.1f 岁%n",
                totalMatches > 0 ? sumAgeAtMatch / totalMatches : 0);
        System.out.printf("高能力男性再婚次数: %d, 高龄女性被弃次数: %d%n",
                highThetaRemarriages, elderFemaleAbandoned);
        double avgWealthSingle = males.stream().filter(m -> !m.married)
                .mapToDouble(m -> m.wealth).average().orElse(0);
        double avgWealthMarried = males.stream().filter(m -> m.married)
                .mapToDouble(m -> m.wealth).average().orElse(0);
        System.out.printf("男性财富: 单身 %.2f, 已婚 %.2f%n", avgWealthSingle, avgWealthMarried);

        // 女性福利统计
        femaleUtilities.clear();
        for (Female f : females) {
            double u = f.married ? f.P_received : femaleValue(f);
            if (p.gamma > 0 && f.married) {
                Male spouse = males.stream().filter(m -> m.id == f.spouseId).findFirst().orElse(null);
                double avgTh = males.stream().filter(m -> m.married)
                        .mapToDouble(m -> m.theta).average().orElse(2.5);
                u += (spouse != null) ? comparisonUtil(spouse.theta, avgTh) : 0;
            }
            femaleUtilities.add(u);
        }
        double meanU = femaleUtilities.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double varU = femaleUtilities.stream()
                .mapToDouble(u -> (u - meanU) * (u - meanU)).sum() / femaleUtilities.size();
        System.out.printf("女性福利均值: %.2f, 方差: %.2f%n", meanU, varU);
    }

    public static void main(String[] args) {
        //场景1：基准责任 rho=0.8
        Params p1 = new Params();
        p1.rho = 0.8;
        new EnhancedMarriageMarket(p1).simulate(80);

        // 场景2：高度去责任化 rho=0.3
        Params p2 = new Params();
        p2.rho = 0.3;
        new EnhancedMarriageMarket(p2).simulate(80);

        // 场景3：完全责任 rho=1.0
        Params p3 = new Params();
        p3.rho = 1.0;
        new EnhancedMarriageMarket(p3).simulate(80);

        // 场景4：去责任化 + 高攀比 + 信息噪声
        Params p4 = new Params();
        p4.divorceFixedCost = 0.1;
        p4.rho = 0.1;
        p4.gamma = 2;
        p4.infoNoiseStd = 2.0;
        p4.poachProb = 2;
        new EnhancedMarriageMarket(p4).simulate(80);
    }
}