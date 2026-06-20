package com.marriagemarket.test;

import java.util.*;
import java.util.stream.*;

// ============== 参数类 ==============
class Params {
    // 时间与折现
    double r = 0.05;           // 年折现率
    double dt = 0.5;           // 时间步长（半年）
    double minAge = 20.0;      // 进入市场的最小年龄（岁）
    double maxFemaleAge = 45.0;// 女性红利期终点
    int T;                     // 红利期总步数，由minAge,maxAge,dt算出

    double lambda = 3.0;       // 相遇强度（每单位时间）
    double B = 10.0;           // 婚姻共同收益
    double beta = 0.5;         // 女性议价能力

    // 男性类型分布
    double thetaMin = 0.5, thetaMax = 5.0;
    double thetaMu = 0.8, thetaSigma = 0.6;

    // 财富过程
    double muBase = 0.02;
    double muScale = 0.03;     // mu = muBase + muScale * theta
    double sigmaW = 0.2;
    double initWealthMale = 1.0;

    // 女性属性
    double initBeautyMean = 6.0, initBeautyStd = 1.5;
    double initIncomeMean = 2.0, initIncomeStd = 0.8;
    double initEduMean = 3.0, initEduStd = 1.0;
    double beautyDecayRate = 0.08;  // 每年衰减比例
    double incomeGrowthRate = 0.02;

    // 信号成本：C(s,theta) = coeff * s^2 / theta
    double signalCoeff = 0.5;
    double signalWealthRatio = 0.4; // 最多拿出财富的比例发信号

    // 匹配门槛
    double baseThreshold = 2.5;

    // 挖墙脚
    boolean enablePoaching = true;
    double divorceFixedCost = 0.5;   // 降低离婚成本使挖墙脚更可行
    double poachProbFactor = 0.4;    // 相遇中挖墙脚尝试的比例

    // 攀比
    boolean enableComparison = true;
    double gamma = 0.3;

    // 自发离婚
    boolean enableDivorce = true;
    double divorceUtilGap = 0.2;     // 当已婚效用 < 单身价值 - gap 时离婚

    // 人口更新
    int newMalesPerStep = 20;        // 每步新进入的男性（20岁）
    int newFemalesPerStep = 20;

    // 社交网络
    double networkRadius = 3.0;

    // 内部计算
    int ageSteps;                    // 总离散步数

    public Params() {
        this.T = (int) ((maxFemaleAge - minAge) / dt);
        this.ageSteps = T + 1;
    }
}

// ============== 个体类 ==============
class Male {
    int id;
    double theta;
    double wealth;
    boolean married;
    int spouseId = -1;
    double P_current;        // 当前转移支付现值
    double signalSpent;      // 已花费的信号成本
    int ageStep;             // 年龄索引(0对应minAge)

    public Male(int id, double theta, double wealth, int ageStep) {
        this.id = id; this.theta = theta; this.wealth = wealth; this.ageStep = ageStep;
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

    public Female(int id, int ageStep, double beauty, double income, double education) {
        this.id = id; this.ageStep = ageStep;
        this.beauty = beauty; this.income = income; this.education = education;
    }

    public double realAge(Params p) {
        return p.minAge + ageStep * p.dt;
    }

    public double attractiveness() {
        return 0.5 * beauty + 0.3 * education + 0.2 * Math.log(1 + income);
    }

    // 衰减与增长
    public void ageOneStep(Params p) {
        double age = realAge(p);
        if (age > 25) {
            beauty = Math.max(1.0, beauty - p.beautyDecayRate * p.dt * beauty);
        }
        income *= (1 + p.incomeGrowthRate * p.dt);
    }
}

// ============== 模拟器 ==============
public class OptimizedMarriageMarket {
    Params p;
    Random rand = new Random(42);
    List<Male> males = new ArrayList<>();
    List<Female> females = new ArrayList<>();
    int maleIdCounter = 0, femaleIdCounter = 0;

    // 统计
    int totalMatches = 0, totalPoaches = 0, totalDivorces = 0;
    double sumSignal = 0, sumWealthAtMatch = 0, sumAgeAtMatch = 0;

    public OptimizedMarriageMarket(Params p) {
        this.p = p;
    }

    // 生成初始人口（年龄分布在20-25之间）
    void initializePopulation(int initMales, int initFemales) {
        for (int i = 0; i < initMales; i++) {
            double th = Math.exp(p.thetaMu + p.thetaSigma * rand.nextGaussian());
            th = Math.max(p.thetaMin, Math.min(p.thetaMax, th));
            int ageIdx = rand.nextInt(10); // 0~10步 => 20~25岁
            males.add(new Male(maleIdCounter++, th, p.initWealthMale, ageIdx));
        }
        for (int i = 0; i < initFemales; i++) {
            double beauty = Math.max(1, Math.min(10, p.initBeautyMean + p.initBeautyStd * rand.nextGaussian()));
            double income = Math.max(0.5, p.initIncomeMean + p.initIncomeStd * rand.nextGaussian());
            double edu = Math.max(1, Math.min(5, p.initEduMean + p.initEduStd * rand.nextGaussian()));
            int ageIdx = rand.nextInt(10);
            females.add(new Female(femaleIdCounter++, ageIdx, beauty, income, edu));
        }
    }

    // 每步注入新人口（全部20岁）
    void injectNewcomers() {
        for (int i = 0; i < p.newMalesPerStep; i++) {
            double th = Math.exp(p.thetaMu + p.thetaSigma * rand.nextGaussian());
            th = Math.max(p.thetaMin, Math.min(p.thetaMax, th));
            males.add(new Male(maleIdCounter++, th, p.initWealthMale, 0));
        }
        for (int i = 0; i < p.newFemalesPerStep; i++) {
            double beauty = Math.max(1, Math.min(10, p.initBeautyMean + p.initBeautyStd * rand.nextGaussian()));
            double income = Math.max(0.5, p.initIncomeMean + p.initIncomeStd * rand.nextGaussian());
            double edu = Math.max(1, Math.min(5, p.initEduMean + p.initEduStd * rand.nextGaussian()));
            females.add(new Female(femaleIdCounter++, 0, beauty, income, edu));
        }
    }

    // 女性单身价值函数 (基于剩余生命与平均匹配质量)
    double femaleValue(Female f) {
        double age = f.realAge(p);
        double remaining = Math.max(0, p.maxFemaleAge - age);
        // 简化：剩余年限越长、吸引力越高，价值越大
        double base = 2.0 + 0.5 * remaining;
        return base * (0.7 + 0.3 * f.attractiveness() / 5.0);
    }

    // 男性单身价值（简化）
    double maleValue(Male m) {
        return Math.max(0, m.wealth * 0.1 + m.theta * 0.3);
    }

    // 信号函数
    double computeSignal(Male m) {
        double affordable = m.wealth * p.signalWealthRatio;
        double desired = Math.sqrt(2 * p.signalCoeff * m.theta * 2.0);
        return Math.min(desired, affordable);
    }

    // 纳什议价转移支付 P
    double bargainP(Male m, Female f) {
        double Vf = femaleValue(f);
        double Vm = maleValue(m);
        double attrBonus = f.attractiveness() * 0.2;
        double S = p.B - Vf - Vm + attrBonus;
        if (S <= 0) return -1;
        return Vf + p.beta * S;
    }

    // 攀比调整项
    double compareUtil(double spouseTheta, double avgTheta) {
        return p.gamma * (spouseTheta - avgTheta);
    }

    // 主模拟
    public void simulate(int totalSteps) {
        initializePopulation(500, 500); // 初始各500人

        for (int step = 0; step < totalSteps; step++) {
            // 年龄增长
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
                    // 已婚男性支付转移给妻子（从财富中扣除部分）
                    double mu = p.muBase + p.muScale * m.theta;
                    m.wealth += m.wealth * (mu * p.dt + p.sigmaW * Math.sqrt(p.dt) * rand.nextGaussian());
                    m.wealth -= 0.05 * m.P_current * p.dt; // 婚姻持续支出
                    m.wealth = Math.max(0.1, m.wealth);
                }
            }
            // 移除超龄单身女性（ > maxFemaleAge 且单身则退出市场）
            females.removeIf(f -> !f.married && f.realAge(p) > p.maxFemaleAge);
            // 注入新人口
            injectNewcomers();

            // 计算已婚男性平均能力
            double avgMarriedTheta = males.stream().filter(m -> m.married).mapToDouble(m -> m.theta).average().orElse(2.5);

            // 获取各类池子
            List<Male> singleMales = males.stream().filter(m -> !m.married).collect(Collectors.toList());
            List<Female> singleFemales = females.stream().filter(f -> !f.married).collect(Collectors.toList());
            List<Male> marriedMales = males.stream().filter(m -> m.married).collect(Collectors.toList());

            // 1. 单身男女相遇匹配
            int meetings = (int) (p.lambda * p.dt * Math.min(singleMales.size(), singleFemales.size()));
            for (int k = 0; k < meetings; k++) {
                if (singleMales.isEmpty() || singleFemales.isEmpty()) break;
                Male m = singleMales.get(rand.nextInt(singleMales.size()));
                Female f = singleFemales.get(rand.nextInt(singleFemales.size()));

                // 社交过滤
                double socialDist = Math.abs(m.wealth / 2 - f.attractiveness());
                if (socialDist > p.networkRadius) continue;

                double P_offer = bargainP(m, f);
                if (P_offer < 0) continue;

                double effectiveQuality = m.theta + 0.3 * m.signalSpent;
                double threshold = p.baseThreshold + 0.05 * f.ageStep; // 年龄越大，门槛越高
                if (effectiveQuality >= threshold) {
                    // 匹配
                    m.married = true; f.married = true;
                    m.spouseId = f.id; f.spouseId = m.id;
                    m.P_current = P_offer; f.P_received = P_offer;
                    m.signalSpent = computeSignal(m);
                    m.wealth -= m.signalSpent;
                    totalMatches++;
                    sumSignal += m.signalSpent;
                    sumWealthAtMatch += m.wealth;
                    sumAgeAtMatch += f.realAge(p);
                    singleMales.remove(m);
                    singleFemales.remove(f);
                }
            }

            // 2. 挖墙脚
            if (p.enablePoaching && !singleFemales.isEmpty() && !marriedMales.isEmpty()) {
                int poachAttempts = (int) (p.lambda * p.dt * Math.min(marriedMales.size(), singleFemales.size()) * p.poachProbFactor);
                for (int k = 0; k < poachAttempts; k++) {
                    if (singleFemales.isEmpty() || marriedMales.isEmpty()) break;
                    Male m = marriedMales.get(rand.nextInt(marriedMales.size()));
                    Female newF = singleFemales.get(rand.nextInt(singleFemales.size()));
                    Female oldF = females.stream().filter(f -> f.id == m.spouseId).findFirst().orElse(null);
                    if (oldF == null) continue;
                    double Vf_new = femaleValue(newF);
                    double Vf_old = femaleValue(oldF);
                    double compensation = Vf_old; // 补偿原配单身价值
                    double totalCostForMale = p.divorceFixedCost + compensation;
                    // 挖墙脚条件：原转移支付减去总成本后仍能给新女性提供不低于其单身价值的收益
                    if (m.P_current - totalCostForMale >= Vf_new) {
                        oldF.married = false; oldF.spouseId = -1;
                        m.spouseId = newF.id; newF.spouseId = m.id;
                        newF.married = true;
                        double newP = bargainP(m, newF);
                        m.P_current = newP; newF.P_received = newP;
                        totalPoaches++;
                        singleFemales.remove(newF);
                    }
                }
            }

            // 3. 自发离婚
            if (p.enableDivorce) {
                Iterator<Male> it = males.iterator();
                while (it.hasNext()) {
                    Male m = it.next();
                    if (!m.married) continue;
                    Female wife = females.stream().filter(f -> f.id == m.spouseId).findFirst().orElse(null);
                    if (wife == null) continue;
                    double VfWife = femaleValue(wife);
                    double marriedUtil = wife.P_received;
                    if (p.enableComparison) {
                        marriedUtil += compareUtil(m.theta, avgMarriedTheta);
                    }
                    if (marriedUtil < VfWife - p.divorceUtilGap) {
                        // 离婚
                        wife.married = false; wife.spouseId = -1;
                        m.married = false; m.spouseId = -1;
                        totalDivorces++;
                    }
                }
            }
        }

        // 输出结果
        System.out.println("=== 优化后模拟结果 ===");
        System.out.printf("总初婚匹配: %d, 挖墙脚成功: %d, 自发离婚: %d%n", totalMatches, totalPoaches, totalDivorces);
        System.out.printf("平均信号: %.2f, 匹配时平均财富: %.2f%n", totalMatches>0 ? sumSignal/totalMatches : 0, totalMatches>0 ? sumWealthAtMatch/totalMatches : 0);
        System.out.printf("女性匹配平均年龄: %.1f 岁%n", totalMatches>0 ? sumAgeAtMatch/totalMatches : 0);
        long marriedF = females.stream().filter(f -> f.married).count();
        long marriedM = males.stream().filter(m -> m.married).count();
        System.out.printf("最终已婚女性: %d/%d, 已婚男性: %d/%d%n", marriedF, females.size(), marriedM, males.size());
        double avgWealthSingleM = males.stream().filter(m -> !m.married).mapToDouble(m -> m.wealth).average().orElse(0);
        double avgWealthMarriedM = males.stream().filter(m -> m.married).mapToDouble(m -> m.wealth).average().orElse(0);
        System.out.printf("男性平均财富: 单身 %.2f, 已婚 %.2f%n", avgWealthSingleM, avgWealthMarriedM);
    }

    public static void main(String[] args) {
        Params params = new Params();
        OptimizedMarriageMarket sim = new OptimizedMarriageMarket(params);
        sim.simulate(80); // 模拟40年 (80个半步)
    }
}