package com.marriagemarket.test_two;

import java.util.*;
import java.util.stream.*;

// ============== 全局参数 ==============
class Params {
    double r = 0.05, dt = 0.5, minAge = 20.0, maxFemaleAge = 45.0;
    int ageSteps;
    double lambda = 3.0;          // 基准相遇强度
    double B = 10.0, beta = 0.5;

    double thetaMin = 0.5, thetaMax = 5.0;
    double thetaMu = 0.8, thetaSigma = 0.6;
    double muBase = 0.02, muScale = 0.03, sigmaW = 0.2, initWealthMale = 1.0;

    double initBeautyMean = 6.0, initBeautyStd = 1.5;
    double initIncomeMean = 2.0, initIncomeStd = 0.8;
    double initEduMean = 3.0, initEduStd = 1.0;
    double beautyDecay = 0.08, incomeGrowth = 0.02;

    double signalCoeff = 0.5, signalWealthRatio = 0.4;
    double baseThreshold = 2.5;

    // === 核心扩展参数 ===
    double divorceFixedCost = 0.5;   // 离婚成本 D，调低可激活挖墙脚
    double poachProb = 0.5;          // 挖墙脚尝试概率
    double gamma = 0.3;              // 攀比强度
    double infoNoiseStd = 0.5;       // 信息歪曲噪声标准差（0=完美信息，大=混同）
    boolean useMarketTightness = true; // 是否启用市场紧度外部性

    int newMalesPerStep = 20, newFemalesPerStep = 20;

    public Params() {
        this.ageSteps = (int) ((maxFemaleAge - minAge) / dt) + 1;
    }
}

// ============== 个体类 (略作修改) ==============
class Male {
    int id;
    double theta, wealth;
    boolean married;
    int spouseId = -1;
    double P_current, signalSpent;
    int ageStep;
    int remarriageCount = 0;
    // 信息歪曲：已婚男性有一个被妻子报告的“公开类型” m
    double reportedTheta;

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
    int marriageCount = 0; // 结婚次数（包括再婚）
    boolean wasPoached = false; // 是否曾被挖墙脚而离婚
    double utility; // 用于计算福利方差

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

// ============== 增强模拟器 ==============
public class EnhancedMarriageMarket {
    Params p;
    Random rand = new Random(42);
    List<Male> males = new ArrayList<>();
    List<Female> females = new ArrayList<>();
    int maleId = 0, femaleId = 0;

    // 统计
    int totalMatches = 0, totalPoaches = 0, totalDivorces = 0;
    double sumSignal = 0, sumWealthAtMatch = 0, sumAgeAtMatch = 0;
    int highThetaRemarriages = 0;   // 高能力(>3.5)男性再婚次数
    int elderFemaleAbandoned = 0;   // 高龄(>35岁)被挖墙脚/离婚的女性
    List<Double> femaleUtilities = new ArrayList<>(); // 用于计算方差

    public EnhancedMarriageMarket(Params p) {
        this.p = p;
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

    double femaleValue(Female f) {
        double age = f.realAge(p);
        double remaining = Math.max(0, p.maxFemaleAge - age);
        return (2.0 + 0.5 * remaining) * (0.7 + 0.3 * f.attractiveness() / 5.0);
    }

    double maleValue(Male m) {
        return Math.max(0, m.wealth * 0.1 + m.theta * 0.3);
    }

    double computeSignal(Male m) {
        double affordable = m.wealth * p.signalWealthRatio;
        double desired = Math.sqrt(2 * p.signalCoeff * m.theta * 2.0);
        return Math.min(desired, affordable);
    }

    double bargainP(Male m, Female f) {
        double Vf = femaleValue(f), Vm = maleValue(m);
        double S = p.B - Vf - Vm + f.attractiveness() * 0.2;
        return S > 0 ? Vf + p.beta * S : -1;
    }

    // 攀比调整效用
    double comparisonUtil(double spouseTheta, double avgMarriedTheta) {
        return p.gamma * (spouseTheta - avgMarriedTheta);
    }

    // 信息歪曲：已婚女性报告丈夫类型 m = theta + noise
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
                    m.wealth -= 0.05 * m.P_current * p.dt;
                    m.wealth = Math.max(0.1, m.wealth);
                    // 信息歪曲：每步更新报告的公开类型（噪声）
                    m.reportedTheta = reportWithNoise(m.theta);
                }
            }
            females.removeIf(f -> !f.married && f.realAge(p) > p.maxFemaleAge);
            injectNewcomers();

            double avgMarriedTheta = males.stream().filter(m -> m.married).mapToDouble(m -> m.theta).average().orElse(2.5);

            List<Male> singleM = males.stream().filter(m -> !m.married).collect(Collectors.toList());
            List<Female> singleF = females.stream().filter(f -> !f.married).collect(Collectors.toList());
            List<Male> marriedM = males.stream().filter(m -> m.married).collect(Collectors.toList());

            // 市场紧度外部性：有效相遇率 = lambda * (单身男性数 / 单身女性数)
            double tightness = singleF.isEmpty() ? 1.0 : (double) singleM.size() / singleF.size();
            double effLambda = p.useMarketTightness ? p.lambda * Math.min(tightness, 2.0) : p.lambda;

            // 1. 单身相遇匹配
            int meetings = (int) (effLambda * p.dt * Math.min(singleM.size(), singleF.size()));
            for (int k = 0; k < meetings; k++) {
                if (singleM.isEmpty() || singleF.isEmpty()) break;
                Male m = singleM.get(rand.nextInt(singleM.size()));
                Female f = singleF.get(rand.nextInt(singleF.size()));
                double P_offer = bargainP(m, f);
                if (P_offer < 0) continue;
                double effectiveQuality = m.theta + 0.3 * m.signalSpent;
                // 攀比调整保留阈值：原阈值加上 gamma*(avgMarriedTheta - theta)
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
                    double Vf_new = femaleValue(newF), Vf_old = femaleValue(oldF);
                    double compensation = Vf_old;
                    double totalCost = p.divorceFixedCost + compensation;
                    // 使用报告的类型 m.reportedTheta 来评估剩余（信息歪曲影响）
                    double reportedTheta = m.reportedTheta;
                    double P_offer_new = bargainP(new Male(-1, reportedTheta, m.wealth, 0), newF); // 简化：用报告类型计算
                    if (P_offer_new < 0) continue;
                    if (m.P_current - totalCost >= Vf_new) {
                        // 挖墙脚成功
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
        System.out.println("=== 增强模型结果 (D=" + p.divorceFixedCost + ", gamma=" + p.gamma + ", infoNoise=" + p.infoNoiseStd + ") ===");
        System.out.printf("初婚: %d, 挖墙脚: %d, 离婚: %d%n", totalMatches, totalPoaches, totalDivorces);
        System.out.printf("平均彩礼: %.2f, 匹配平均财富: %.2f%n", totalMatches > 0 ? sumSignal / totalMatches : 0, totalMatches > 0 ? sumWealthAtMatch / totalMatches : 0);
        System.out.printf("女性初婚平均年龄: %.1f 岁%n", totalMatches > 0 ? sumAgeAtMatch / totalMatches : 0);
        System.out.printf("高能力男性再婚次数: %d, 高龄女性被弃次数: %d%n", highThetaRemarriages, elderFemaleAbandoned);
        double avgWealthSingle = males.stream().filter(m -> !m.married).mapToDouble(m -> m.wealth).average().orElse(0);
        double avgWealthMarried = males.stream().filter(m -> m.married).mapToDouble(m -> m.wealth).average().orElse(0);
        System.out.printf("男性财富: 单身 %.2f, 已婚 %.2f%n", avgWealthSingle, avgWealthMarried);

        // 女性福利方差
        femaleUtilities.clear();
        for (Female f : females) {
            double u = f.married ? f.P_received : femaleValue(f);
            if (p.gamma > 0 && f.married) {
                double avgTh = males.stream().filter(m -> m.married).mapToDouble(m -> m.theta).average().orElse(2.5);
                u += comparisonUtil(males.get(f.spouseId).theta, avgTh);
            }
            femaleUtilities.add(u);
        }
        double meanU = femaleUtilities.stream().mapToDouble(Double::doubleValue).average().orElse(0);
        double varU = femaleUtilities.stream().mapToDouble(u -> (u - meanU) * (u - meanU)).sum() / femaleUtilities.size();
        System.out.printf("女性福利均值: %.2f, 方差: %.2f%n", meanU, varU);
    }

    public static void main(String[] args) {
        // 场景1：低离婚成本，激活挖墙脚
        Params p1 = new Params();
        p1.divorceFixedCost = 0.2;
        p1.gamma = 0.3;
        p1.infoNoiseStd = 0.5;
        new EnhancedMarriageMarket(p1).simulate(80);

        // 场景2：高攀比强度
        Params p2 = new Params();
        p2.divorceFixedCost = 0.5;
        p2.gamma = 0.8;
        p2.infoNoiseStd = 0.5;
        new EnhancedMarriageMarket(p2).simulate(80);

        // 场景3：信息完全透明 vs 完全噪声（混同）
        Params p3 = new Params();
        p3.infoNoiseStd = 0.0;
        new EnhancedMarriageMarket(p3).simulate(80);
        Params p4 = new Params();
        p4.infoNoiseStd = 2.0;
        new EnhancedMarriageMarket(p4).simulate(80);

        //场景4: 完全噪声（混同）,高攀比强度, 激活挖墙脚
        Params p5 = new Params();
        p5.infoNoiseStd = 4.0;
        p5.gamma = 2;
        p5.poachProb = 2;
        new EnhancedMarriageMarket(p5).simulate(80);
    }
}