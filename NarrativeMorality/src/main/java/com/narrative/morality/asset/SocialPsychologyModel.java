package com.narrative.morality.asset;

/**
 * 社会心理维度认同模型
 * 对应文档：社会文化维度 - 通用核心心理方程
 */
public class SocialPsychologyModel {
    // 群体策略调节系数 β，取值范围 [0.8, 1.2]
    private final double beta;
    // 个体道德落差感知度 W，取值范围 [0, 10]
    private final double moralGapPerception;
    // 叙事道德资产感知量 M'
    private double narrativeAssetPerception;
    // 受众认同阈值 t'
    private final double identityThreshold;
    // 交易场景匹配系数 s，取值范围 [0, 1]
    private final double sceneMatchCoefficient;
    // 现实价值补偿效率系数 l，取值范围 [0, 1]
    private final double compensationEfficiency;

    /**
     * 构造社会心理模型
     * @param beta 群体策略调节系数
     * @param moralGapPerception 道德落差感知度
     * @param narrativeAssetPerception 叙事资产感知量
     * @param identityThreshold 认同阈值
     * @param sceneMatchCoefficient 场景匹配系数
     * @param compensationEfficiency 补偿效率系数
     */
    public SocialPsychologyModel(double beta, double moralGapPerception, double narrativeAssetPerception,
                                 double identityThreshold, double sceneMatchCoefficient, double compensationEfficiency) {
        if (beta < 0.8 || beta > 1.2) {
            throw new IllegalArgumentException("beta 应在 [0.8, 1.2] 范围内");
        }
        if (moralGapPerception < 0 || moralGapPerception > 10) {
            throw new IllegalArgumentException("道德落差感知度应在 [0, 10] 范围内");
        }
        if (sceneMatchCoefficient < 0 || sceneMatchCoefficient > 1) {
            throw new IllegalArgumentException("场景匹配系数应在 [0, 1] 范围内");
        }
        if (compensationEfficiency < 0 || compensationEfficiency > 1) {
            throw new IllegalArgumentException("补偿效率系数应在 [0, 1] 范围内");
        }

        this.beta = beta;
        this.moralGapPerception = moralGapPerception;
        this.narrativeAssetPerception = narrativeAssetPerception;
        this.identityThreshold = identityThreshold;
        this.sceneMatchCoefficient = sceneMatchCoefficient;
        this.compensationEfficiency = compensationEfficiency;
    }

    /**
     * 计算共情体验指数 E
     * 公式：E = β × (W × M') / t'
     */
    public double calculateEmpathyIndex() {
        return beta * (moralGapPerception * narrativeAssetPerception) / identityThreshold;
    }

    /**
     * 计算受众爽感生成量 U
     * 公式：U = E × s × l
     */
    public double calculatePleasureValue() {
        double empathyIndex = calculateEmpathyIndex();
        return empathyIndex * sceneMatchCoefficient * compensationEfficiency;
    }

    /**
     * 正向反馈：计算下一轮叙事资产感知量 M'_{n+1}
     * 公式：M'_{n+1} = M'_n × (1 + U_n)
     */
    public double iterateNextAssetPerception() {
        double pleasure = calculatePleasureValue();
        this.narrativeAssetPerception = narrativeAssetPerception * (1 + pleasure);
        return this.narrativeAssetPerception;
    }

    /**
     * 多轮迭代正向反馈
     * @param rounds 迭代轮次
     * @return 迭代后的叙事资产感知量
     */
    public double iterateAssetPerception(int rounds) {
        for (int i = 0; i < rounds; i++) {
            iterateNextAssetPerception();
        }
        return this.narrativeAssetPerception;
    }

    // Getter
    public double getNarrativeAssetPerception() {
        return narrativeAssetPerception;
    }
}