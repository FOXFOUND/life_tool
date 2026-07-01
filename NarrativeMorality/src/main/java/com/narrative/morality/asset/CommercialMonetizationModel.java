package com.narrative.morality.asset;

/**
 * 商业维度变现模型
 * 对应文档：商业维度 - 通用核心商业方程
 */
public class CommercialMonetizationModel {
    // 赛道调节系数 γ，取值范围 [0.7, 1.4]
    private final double gamma;
    // 流量粘性留存指数 R，取值范围 [0, 1]
    private final double retentionRate;
    // 用户爽感生成量 U
    private double userPleasure;
    // 内容付费收入系数 p
    private final double payConversion;
    // 广告分成收入系数 a
    private final double adRevenue;
    // 衍生溢价收入系数 i
    private final double derivativePremium;

    /**
     * 构造商业变现模型
     * @param gamma 赛道调节系数
     * @param retentionRate 粘性留存指数
     * @param userPleasure 用户爽感值
     * @param payConversion 付费转化系数
     * @param adRevenue 广告收益系数
     * @param derivativePremium 衍生溢价系数
     */
    public CommercialMonetizationModel(double gamma, double retentionRate, double userPleasure,
                                       double payConversion, double adRevenue, double derivativePremium) {
        if (gamma < 0.7 || gamma > 1.4) {
            throw new IllegalArgumentException("gamma 应在 [0.7, 1.4] 范围内");
        }
        if (retentionRate < 0 || retentionRate > 1) {
            throw new IllegalArgumentException("留存指数应在 [0, 1] 范围内");
        }

        this.gamma = gamma;
        this.retentionRate = retentionRate;
        this.userPleasure = userPleasure;
        this.payConversion = payConversion;
        this.adRevenue = adRevenue;
        this.derivativePremium = derivativePremium;
    }

    /**
     * 计算流量价值 V
     * 公式：V = γ × R × U
     */
    public double calculateTrafficValue() {
        return gamma * retentionRate * userPleasure;
    }

    /**
     * 计算商业变现总收益 Y
     * 公式：Y = V × (p + a + i)
     */
    public double calculateTotalRevenue() {
        double trafficValue = calculateTrafficValue();
        return trafficValue * (payConversion + adRevenue + derivativePremium);
    }

    /**
     * 正向反馈：计算下一轮总收益 Y_{n+1}
     * 公式：Y_{n+1} = Y_n × (1 + U_n)
     */
    public double iterateNextRevenue() {
        double currentRevenue = calculateTotalRevenue();
        return currentRevenue * (1 + userPleasure);
    }

    /**
     * 多轮迭代收益增长
     * @param rounds 迭代轮次
     * @return 迭代后的总收益
     */
    public double iterateRevenue(int rounds) {
        double currentRevenue = calculateTotalRevenue();
        for (int i = 0; i < rounds; i++) {
            currentRevenue = currentRevenue * (1 + userPleasure);
        }
        return currentRevenue;
    }

    // 联动社会心理模型，更新爽感值
    public void setUserPleasure(double userPleasure) {
        this.userPleasure = userPleasure;
    }
}