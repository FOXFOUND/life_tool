package cn.umhwm.test;

import java.util.*;
import java.text.DecimalFormat;

/**
 * 人性弱点多维数学建模 - 纵向对照实验扩展（延迟开启版）
 * 前5年两组均处于自然状态，第5年后实验组开始每天看短视频4小时
 */
public class WeaknessModel {

    // ==================== 1. 参数类（支持可变） ====================

    public static class BioParams {
        public double pfcAmyControl;   // 前额叶对杏仁核控制力 [0,1]
        public double dopamineDensity; // 多巴胺受体密度
        public double cortisol;        // 皮质醇浓度
        public double emotionArousal;  // 情绪激烈程度 [0,1]
        public double willpower;       // 剩余自控意志力 [0, Wmax]
        public static final double WILLPOWER_MAX = 100.0;

        public BioParams(double pfcAmyControl, double dopamineDensity, double cortisol,
                         double emotionArousal, double willpower) {
            this.pfcAmyControl = pfcAmyControl;
            this.dopamineDensity = dopamineDensity;
            this.cortisol = cortisol;
            this.emotionArousal = emotionArousal;
            this.willpower = Math.min(willpower, WILLPOWER_MAX);
        }

        public BioParams copy() {
            return new BioParams(this.pfcAmyControl, this.dopamineDensity, this.cortisol,
                    this.emotionArousal, this.willpower);
        }
    }

    public static class PsychoParams {
        public double successProb;     // 自判成功概率
        public double objectiveValue;  // 客观收益
        public double discountK;       // 延迟折扣系数（冲动核心指标）
        public double delayMonths;     // 等待时长
        public double lossAversion;    // 损失厌恶系数
        public double bandwidth;       // 认知算力带宽

        public PsychoParams(double successProb, double objectiveValue, double discountK,
                            double delayMonths, double lossAversion, double bandwidth) {
            this.successProb = successProb;
            this.objectiveValue = objectiveValue;
            this.discountK = discountK;
            this.delayMonths = delayMonths;
            this.lossAversion = lossAversion;
            this.bandwidth = bandwidth;
        }

        public PsychoParams copy() {
            return new PsychoParams(this.successProb, this.objectiveValue, this.discountK,
                    this.delayMonths, this.lossAversion, this.bandwidth);
        }
    }

    public static class SocialParams {
        public double groupRatio;
        public double conformitySensitivity;
        public double socialCost;

        public SocialParams(double groupRatio, double conformitySensitivity, double socialCost) {
            this.groupRatio = groupRatio;
            this.conformitySensitivity = conformitySensitivity;
            this.socialCost = socialCost;
        }

        public SocialParams copy() {
            return new SocialParams(this.groupRatio, this.conformitySensitivity, this.socialCost);
        }
    }

    // ==================== 2. U-DUF 计算器 ====================

    public static class UDUFCalculator {
        private static final Random RANDOM = new Random();

        public static double computeSubjectiveUtility(BioParams bio, PsychoParams psycho,
                                                      SocialParams social, double rationalTheta,
                                                      boolean includeNoise) {
            double term1 = computeTerm1(bio, psycho);
            double term2 = computeTerm2(bio, psycho);
            double term3 = computeTerm3(social);
            double noise = includeNoise ? RANDOM.nextGaussian() * 0.1 : 0.0;
            return term1 + term2 + term3 + noise;
        }

        private static double computeTerm1(BioParams bio, PsychoParams psycho) {
            double lambdaBio = bio.pfcAmyControl / (1.0 + 0.02 * bio.cortisol);
            double value = prospectValue(psycho.objectiveValue, psycho.lossAversion);
            double gamma = psycho.discountK * (1.0 + 0.3 * bio.dopamineDensity + 0.5 * bio.emotionArousal);
            double delayFactor;
            if (psycho.delayMonths > 0) {
                delayFactor = (0.9 + Math.pow(0.9, psycho.delayMonths)) / (1.0 + gamma * psycho.delayMonths);
            } else {
                delayFactor = 1.0;
            }
            return lambdaBio * (value * psycho.successProb * delayFactor);
        }

        private static double prospectValue(double x, double lambdaLoss) {
            if (x >= 0) return Math.pow(x, 0.88);
            else return -lambdaLoss * Math.pow(-x, 0.88);
        }

        private static double computeTerm2(BioParams bio, PsychoParams psycho) {
            double wDiff = BioParams.WILLPOWER_MAX - bio.willpower;
            double phi = (0.1 + Math.exp(0.05 * wDiff)) / Math.max(psycho.bandwidth, 0.01);
            double deltaM = 1.0 - psycho.successProb;
            return -phi * deltaM;
        }

        private static double computeTerm3(SocialParams social) {
            return social.conformitySensitivity * (-0.8 * social.groupRatio + 0.2);
        }

        public static double softmaxProbability(double u, double theta) {
            if (theta <= 0) return 0.5;
            return Math.exp(theta * u) / (Math.exp(theta * u) + 1.0);
        }

        /** 意志力每日自然更新（步长=1天） */
        public static double updateWillpowerDaily(BioParams bio, PsychoParams psycho) {
            double wDiff = BioParams.WILLPOWER_MAX - bio.willpower;
            double phi = (0.1 + Math.exp(0.05 * wDiff)) / Math.max(psycho.bandwidth, 0.01);
            double deltaM = 1.0 - psycho.successProb;
            double dW = -0.01 * phi * deltaM + 0.02 * wDiff;
            double newW = bio.willpower + dW; // dt=1
            return Math.max(0, Math.min(BioParams.WILLPOWER_MAX, newW));
        }
    }

    // ==================== 3. 弱点指数 ====================

    public static class WeaknessIndexCalculator {
        public static double greedIndex(double expectedGainSelf, double expectedGainRational,
                                        double perceivedRisk, double riskAversion) {
            if (expectedGainRational <= 0 || perceivedRisk <= 0 || riskAversion <= 0) return Double.POSITIVE_INFINITY;
            return ((expectedGainSelf - expectedGainRational) / expectedGainRational) / (perceivedRisk * riskAversion);
        }

        public static double fearIndex(double lossSelf, double lossRational, double expectedReturn, double timeHorizon) {
            if (expectedReturn <= 0 || timeHorizon <= 0) return Double.POSITIVE_INFINITY;
            return (lossSelf - lossRational) / (expectedReturn * timeHorizon);
        }

        public static double procrastinationIndex(double taskDifficulty, double escapePleasure,
                                                  double longTermReward, double timeRemainingRatio) {
            if (longTermReward <= 0 || timeRemainingRatio <= 0) return Double.POSITIVE_INFINITY;
            return (taskDifficulty * escapePleasure) / (longTermReward * timeRemainingRatio);
        }

        public static double overconfidenceIndex(double selfAccuracy, double realAccuracy,
                                                 double experience, double failureFocus) {
            if (experience <= 0 || failureFocus <= 0) return Double.POSITIVE_INFINITY;
            return (selfAccuracy - realAccuracy) / (experience * failureFocus);
        }
    }

    // ==================== 4. 纵向实验引擎（支持延迟开启） ====================

    public static class PersonSnapshot {
        public int day;
        public double willpower;
        public double pfcControl;
        public double discountK;
        public double dopamineDensity;
        public double procrastinationScore;
        public double greedScore;
        public double utility;

        public PersonSnapshot(int day, BioParams bio, PsychoParams psycho, double utility) {
            this.day = day;
            this.willpower = bio.willpower;
            this.pfcControl = bio.pfcAmyControl;
            this.discountK = psycho.discountK;
            this.dopamineDensity = bio.dopamineDensity;
            this.procrastinationScore = WeaknessIndexCalculator.procrastinationIndex(7, 8, 5, 0.3);
            this.greedScore = WeaknessIndexCalculator.greedIndex(1.5, 1.0, 0.5, 0.6);
            this.utility = utility;
        }

        @Override
        public String toString() {
            DecimalFormat df = new DecimalFormat("#.##");
            return "Day " + day + " | 意志力:" + df.format(willpower) +
                    " | PFC控制:" + df.format(pfcControl) +
                    " | 冲动k:" + df.format(discountK) +
                    " | 多巴胺D2:" + df.format(dopamineDensity) +
                    " | 拖延指数:" + df.format(procrastinationScore) +
                    " | 效用:" + df.format(utility);
        }
    }

    /**
     * 短视频日效应：每天观看对大脑的冲击
     */
    public static class ShortVideoEffect {
        public static void applyDailyEffect(BioParams bio, PsychoParams psycho, int watchHours) {
            if (watchHours <= 0) return;
            // 多巴胺受体密度上升（长期上调），上限2.5
            bio.dopamineDensity = Math.min(2.5, bio.dopamineDensity + 0.002 * watchHours);
            // 延迟折扣系数k上升（更冲动），上限0.35
            psycho.discountK = Math.min(0.35, psycho.discountK + 0.002 * watchHours);
            // 情绪唤醒度上升
            bio.emotionArousal = Math.min(0.9, bio.emotionArousal + 0.01 * watchHours);
            // 意志力剧烈消耗：每小时消耗2.5个单位
            bio.willpower = Math.max(0, bio.willpower - 2.5 * watchHours);
        }

        /** 每天的自然恢复（所有人） */
        public static void naturalRecovery(BioParams bio, PsychoParams psycho) {
            bio.willpower = UDUFCalculator.updateWillpowerDaily(bio, psycho);
            bio.emotionArousal = Math.max(0.1, bio.emotionArousal - 0.005);
            // 前额叶可塑性：意志力长期饱满则恢复，长期低下则退化
            if (bio.willpower > 70) {
                bio.pfcAmyControl = Math.min(0.85, bio.pfcAmyControl + 0.0001);
            } else if (bio.willpower < 30) {
                bio.pfcAmyControl = Math.max(0.05, bio.pfcAmyControl - 0.00015);
            }
            bio.pfcAmyControl = Math.max(0.01, Math.min(0.95, bio.pfcAmyControl));
        }
    }

    /**
     * 运行单条模拟线
     * @param name 名称
     * @param initBio 初始生物参数
     * @param initPsycho 初始心理参数
     * @param initSocial 初始社会参数
     * @param totalDays 总模拟天数
     * @param dailyWatchHours 每日观看小时数（0=对照组）
     * @param startDay 开始干预的天数（从第几天起生效）
     */
    public static List<PersonSnapshot> runSimulation(String name,
                                                     BioParams initBio,
                                                     PsychoParams initPsycho,
                                                     SocialParams initSocial,
                                                     int totalDays,
                                                     int dailyWatchHours,
                                                     int startDay) {
        System.out.println("开始模拟 [" + name + "] 总天数: " + totalDays +
                ", 每日观看: " + dailyWatchHours + "小时, 从第" + startDay + "天开启");

        BioParams bio = initBio.copy();
        PsychoParams psycho = initPsycho.copy();
        SocialParams social = initSocial.copy();

        List<PersonSnapshot> snapshots = new ArrayList<>();
        double initUtil = UDUFCalculator.computeSubjectiveUtility(bio, psycho, social, 1.0, false);
        snapshots.add(new PersonSnapshot(0, bio, psycho, initUtil));

        int accumulatedWatchDays = 0; // 仅记录实际观看的累积天数

        for (int day = 1; day <= totalDays; day++) {
            // 1. 自然恢复（所有人）
            ShortVideoEffect.naturalRecovery(bio, psycho);

            // 2. 判断是否到达开启日期且需要观看
            if (day >= startDay && dailyWatchHours > 0) {
                ShortVideoEffect.applyDailyEffect(bio, psycho, dailyWatchHours);
                accumulatedWatchDays++;

                // 长期累积效应：持续观看超过1年后，前额叶控制力额外退化
                if (accumulatedWatchDays > 365) {
                    bio.pfcAmyControl = Math.max(0.01, bio.pfcAmyControl - 0.00005);
                }
            }

            // 3. 边界限制
            bio.willpower = Math.max(0, Math.min(BioParams.WILLPOWER_MAX, bio.willpower));
            psycho.discountK = Math.max(0.01, Math.min(0.4, psycho.discountK));
            bio.dopamineDensity = Math.max(0.5, Math.min(2.5, bio.dopamineDensity));
            bio.pfcAmyControl = Math.max(0.01, Math.min(0.95, bio.pfcAmyControl));

            // 每30天记录一次
            if (day % 30 == 0 || day == totalDays) {
                double util = UDUFCalculator.computeSubjectiveUtility(bio, psycho, social, 1.0, false);
                snapshots.add(new PersonSnapshot(day, bio, psycho, util));
            }
        }

        if (totalDays % 30 != 0) {
            double util = UDUFCalculator.computeSubjectiveUtility(bio, psycho, social, 1.0, false);
            snapshots.add(new PersonSnapshot(totalDays, bio, psycho, util));
        }

        return snapshots;
    }

    // ==================== 辅助：查找最近快照 ====================

    private static PersonSnapshot findClosestSnapshot(List<PersonSnapshot> list, int targetDay) {
        PersonSnapshot closest = list.get(0);
        int minDiff = Integer.MAX_VALUE;
        for (PersonSnapshot snap : list) {
            int diff = Math.abs(snap.day - targetDay);
            if (diff < minDiff) {
                minDiff = diff;
                closest = snap;
            }
        }
        return closest;
    }

    // ==================== 5. 主程序：5年后才开启干预 ====================

    public static void main(String[] args) {
        System.out.println("================================================================");
        System.out.println("    人性弱点数字孪生 - 10年对照实验（第5年才开启干预）");
        System.out.println("================================================================");

        // 大众水平初始参数（基于报告健康成年人平均值）
        BioParams baseBio = new BioParams(
                0.42,   // pfcAmyControl
                1.2,    // dopamineDensity
                12.0,   // cortisol
                0.25,   // emotionArousal
                75.0    // willpower
        );

        PsychoParams basePsycho = new PsychoParams(
                0.65,   // successProb
                5000,   // objectiveValue
                0.045,  // discountK
                12,     // delayMonths
                2.5,    // lossAversion
                0.85    // bandwidth
        );

        SocialParams baseSocial = new SocialParams(
                0.4,    // groupRatio
                0.9,    // conformitySensitivity
                30000   // socialCost
        );

        int totalDays = 365 * 10;      // 模拟10年
        int startInterventionDay = 365 * 5; // 第5年（1825天）之后，第1826天开启
        int watchHours = 4;

        // ---- 运行对照组：全程0小时 ----
        List<PersonSnapshot> controlSnapshots = runSimulation(
                "对照组 (全程0小时)",
                baseBio, basePsycho, baseSocial,
                totalDays,
                0,                  // dailyWatchHours
                startInterventionDay
        );

        // ---- 运行实验组：前5年0小时，第5年后4小时 ----
        List<PersonSnapshot> expSnapshots = runSimulation(
                "实验组 (前5年0h，后5年4h/天)",
                baseBio, basePsycho, baseSocial,
                totalDays,
                watchHours,         // dailyWatchHours
                startInterventionDay
        );

        // ===== 输出对比报告（按年展示） =====
        System.out.println("\n==================== 十年对比报告（年节点） ====================");
        DecimalFormat df = new DecimalFormat("#.##");

        System.out.printf("%-10s | %-22s | %-18s | %-18s | %-20s%n",
                "年份", "组别", "意志力", "冲动系数(k)", "前额叶控制力");
        System.out.println("--------------------------------------------------------------------------------------------");

        for (int year = 0; year <= 10; year++) {
            int targetDay = year * 365;
            PersonSnapshot cSnap = findClosestSnapshot(controlSnapshots, targetDay);
            PersonSnapshot eSnap = findClosestSnapshot(expSnapshots, targetDay);

            if (year == 0) {
                System.out.printf("第 %d 年  | %-22s | %-18s | %-18s | %-20s%n",
                        year, "初始基线 (两组相同)",
                        df.format(cSnap.willpower),
                        df.format(cSnap.discountK),
                        df.format(cSnap.pfcControl));
                System.out.println("--------------------------------------------------------------------------------------------");
                continue;
            }

            // 前5年（1~5年）两组数据应高度一致（因为都未开启）
            String groupLabel1 = (year <= 5) ? "对照组 & 实验组 (均0h)" : "对照组 (0h)";
            String groupLabel2 = (year <= 5) ? "" : "实验组 (4h)";

            if (year <= 5) {
                // 前5年两组完全相同，只打印一行
                System.out.printf("第 %d 年  | %-22s | %-18s | %-18s | %-20s  [干预未开启]%n",
                        year, groupLabel1,
                        df.format(cSnap.willpower),
                        df.format(cSnap.discountK),
                        df.format(cSnap.pfcControl));
            } else {
                // 第5年后，实验组启动，开始分化
                System.out.printf("第 %d 年  | %-22s | %-18s | %-18s | %-20s%n",
                        year, "对照组 (0h)",
                        df.format(cSnap.willpower),
                        df.format(cSnap.discountK),
                        df.format(cSnap.pfcControl));
                System.out.printf("         | %-22s | %-18s | %-18s | %-20s  [Δ=%s]%n",
                        "实验组 (4h)",
                        df.format(eSnap.willpower),
                        df.format(eSnap.discountK),
                        df.format(eSnap.pfcControl),
                        df.format(eSnap.pfcControl - cSnap.pfcControl));
            }
            System.out.println("--------------------------------------------------------------------------------------------");
        }

        // 最终结论摘要
        PersonSnapshot finalControl = findClosestSnapshot(controlSnapshots, totalDays);
        PersonSnapshot finalExp = findClosestSnapshot(expSnapshots, totalDays);

        System.out.println("\n==================== 最终结论 (10年后) ====================");
        System.out.println("【前5年】两组均未接触短视频，大脑参数同步自然演化。");
        System.out.println("【后5年】实验组每天观看4小时，对照组保持0小时。");

        System.out.println("\n对照组最终状态: 意志力=" + df.format(finalControl.willpower) +
                ", 冲动k=" + df.format(finalControl.discountK) +
                ", PFC控制=" + df.format(finalControl.pfcControl));
        System.out.println("实验组最终状态: 意志力=" + df.format(finalExp.willpower) +
                ", 冲动k=" + df.format(finalExp.discountK) +
                ", PFC控制=" + df.format(finalExp.pfcControl));

        double deltaWillpower = finalControl.willpower - finalExp.willpower;
        double deltaK = finalExp.discountK - finalControl.discountK;
        double deltaPFC = finalControl.pfcControl - finalExp.pfcControl;

        System.out.println("\n【关键发现】后5年开启每天4小时短视频，相比从未观看的对照组：");
        System.out.println("1. 意志力下降 " + df.format(deltaWillpower) + " 点 (自控力严重损耗)");
        System.out.println("2. 冲动系数 k 上升 " + df.format(deltaK) + " (延迟满足能力变差)");
        System.out.println("3. 前额叶控制力下降 " + df.format(deltaPFC) + " (理性脑对情绪脑支配减弱)");
        System.out.println("4. 多巴胺受体密度: 实验组 " + df.format(finalExp.dopamineDensity) +
                " vs 对照组 " + df.format(finalControl.dopamineDensity) + " (敏感度钝化)");
        System.out.println("\n模拟严格依据 U-DUF 效用理论与神经可塑性假设，与报告实证结论一致。");
        System.out.println("注：因前5年基线完全一致，第1~5年数据在表格中合并显示。");
    }
}