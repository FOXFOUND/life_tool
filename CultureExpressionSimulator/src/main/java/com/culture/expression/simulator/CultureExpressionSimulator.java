package com.culture.expression.simulator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 文化表达双重困境综合改革模拟器
 * 同时启用三种措施：版权阶梯许可、审查透明化、独立主权(AI内化成本)
 * 对比综合改革与维持现状的情景
 */
public class CultureExpressionSimulator {

    /* ==================== 全局参数 ==================== */
    private static final double COPYRIGHT_BARRIER_INIT = 10.0;   // 外部版权壁垒初始值
    private static final double SEVERITY_S = 2.0;                // 审查严厉程度
    private static final double TRANSPARENCY_LOW = 0.1;         // 现状透明度
    private static final double TRANSPARENCY_HIGH = 0.9;        // 改革后透明度
    private static final double INTERNAL_COST_INIT = 5.0;       // AI内化成本初始值
    private static final double INTERNAL_COST_DECAY = 0.92;     // 每轮衰减系数(技术进步)

    private static final int CREATOR_COUNT = 2000;              // 模拟创作者总数
    private static final int ROUNDS = 20;                       // 模拟轮次
    private static final double EXPRESSION_THRESHOLD = 0.01;    // 有效表达阈值

    private static Random random = new Random(42);

    /* ==================== 创作者类 ==================== */
    static class Creator {
        double resource;      // R_i 自身资源(1~30偏态分布)
        double sigma;         // 内化审查敏感度
        double profit;        // 累计盈利

        double willingness;
        double ability;
        double effective;

        Creator(double resource) {
            this.resource = resource;
            this.sigma = 0.5;
            this.profit = 0;
        }

        /**
         * 根据当前改革开关更新状态
         * @param reformCopyright 是否启用版权阶梯许可
         * @param reformTransp   是否启用审查透明化
         * @param independent    是否启用独立主权(AI内化版权+多渠道分发)
         * @param internalCost   当前内化成本(仅在independent为true时有意义)
         */
        void update(boolean reformCopyright, boolean reformTransp,
                    boolean independent, double internalCost) {
            // --- 版权壁垒计算 ---
            double effectiveBarrier = COPYRIGHT_BARRIER_INIT;

            if (reformCopyright) {
                // 阶梯许可：低盈利(profit<2)时免费或极低成本
                if (profit < 2.0) {
                    effectiveBarrier = 0.01;
                } else {
                    // 盈利越高，成本阶梯上升，但不高于原壁垒的50%
                    effectiveBarrier = Math.min(COPYRIGHT_BARRIER_INIT * 0.5,
                            COPYRIGHT_BARRIER_INIT * (profit / 20.0));
                }
            }
            if (independent) {
                // 独立路径用内化成本替换，取较小值
                effectiveBarrier = Math.min(effectiveBarrier, internalCost);
            }

            // 能力 A_i = R_i / (R_i + C)
            ability = resource / (resource + effectiveBarrier);

            // --- 透明度计算 ---
            double perceivedT = TRANSPARENCY_LOW;
            if (reformTransp) {
                perceivedT = TRANSPARENCY_HIGH;     // 改革后清晰透明
            }
            if (independent) {
                // 多渠道分发带来额外风险分散，感知透明度提升
                perceivedT = Math.min(1.0, perceivedT + 0.25);
            }

            // 意愿 W_i = exp(-S / T)
            double effectiveT = Math.max(perceivedT, 0.001);
            willingness = Math.exp(-SEVERITY_S / effectiveT);

            // 内化审查累积：每轮增加 alpha * (1/T)
            sigma += 0.04 * (1.0 / effectiveT);
            willingness *= Math.exp(-0.15 * sigma);  // 内化恐惧抑制意愿

            // 有效表达
            effective = willingness * ability;

            // 盈利动态：作品被看见才可能盈利
            if (effective > EXPRESSION_THRESHOLD) {
                profit += random.nextDouble() * 0.15;
            }
            profit = Math.max(0, profit);
        }

        /** 深拷贝(仅复制资源，重置状态) */
        Creator copy() {
            return new Creator(this.resource);
        }
    }

    /* ==================== 主模拟 ==================== */
    public static void main(String[] args) {
        // 生成创作者群体(对数正态分布模拟现实资源不均)
        List<Creator> baseCreators = new ArrayList<>();
        for (int i = 0; i < CREATOR_COUNT; i++) {
            double resource = Math.exp(random.nextGaussian() * 0.9 + 1.8);
            resource = Math.min(30, Math.max(0.3, resource));
            baseCreators.add(new Creator(resource));
        }

        System.out.println("========== 综合改革 vs 维持现状 对比模拟 ==========");
        System.out.printf("参数: 版权壁垒C=%.1f, 审查严厉度S=%.1f, 现状透明度=%.1f, 改革透明度=%.1f\n",
                COPYRIGHT_BARRIER_INIT, SEVERITY_S, TRANSPARENCY_LOW, TRANSPARENCY_HIGH);
        System.out.println("创作者总数: " + CREATOR_COUNT + ", 模拟轮次: " + ROUNDS);
        System.out.println("综合改革 = 版权阶梯 + 审查透明化 + AI独立主权");
        System.out.println("--------------------------------------------------");

        // 两种情景
        String[] scenarios = {"维持现状(无改革)", "综合改革(三项全开)"};
        boolean[][] reformSwitches = {
                {false, false, false},   // 现状
                {true,  true,  true}     // 阶梯版权 + 透明化 + 独立路径
        };

        for (int s = 0; s < scenarios.length; s++) {
            boolean refCopyright = reformSwitches[s][0];
            boolean refTransp    = reformSwitches[s][1];
            boolean independent  = reformSwitches[s][2];

            // 深拷贝初始创作者
            List<Creator> simCreators = new ArrayList<>();
            for (Creator c : baseCreators) {
                simCreators.add(c.copy());
            }

            double internalCost = INTERNAL_COST_INIT;

            System.out.println("\n>>> 情景: " + scenarios[s] + " <<<");

            for (int round = 0; round < ROUNDS; round++) {
                // 独立路径下内化成本逐年衰减
                if (independent) {
                    internalCost *= INTERNAL_COST_DECAY;
                }

                double totalE = 0;
                int effectiveCount = 0;
                double[] contributions = new double[simCreators.size()];

                for (int i = 0; i < simCreators.size(); i++) {
                    Creator cr = simCreators.get(i);
                    cr.update(refCopyright, refTransp, independent, internalCost);
                    contributions[i] = cr.effective;
                    totalE += cr.effective;
                    if (cr.effective > EXPRESSION_THRESHOLD) {
                        effectiveCount++;
                    }
                }

                // 表达独特性 U：变异系数(标准差/均值)，反映同质化程度
                double mean = totalE / simCreators.size();
                double variance = 0;
                for (double v : contributions) {
                    variance += (v - mean) * (v - mean);
                }
                variance /= simCreators.size();
                double stdDev = Math.sqrt(variance);
                double uniqueness = (mean > 0.001) ? (stdDev / mean) : 0;

                // 多样性 D = N_eff * U
                double diversity = effectiveCount * uniqueness;

                if (round % 4 == 0 || round == ROUNDS - 1) {
                    System.out.printf("第%2d轮 | 总有效表达E=%.3f | 活跃创作者=%d | 独特性U=%.3f | 多样性D=%.2f",
                            round, totalE, effectiveCount, uniqueness, diversity);
                    if (independent) {
                        System.out.printf(" | 内化成本=%.2f", internalCost);
                    }
                    System.out.println();
                }
            }
        }

        System.out.println("\n========== 模拟结束 ==========");
    }
}