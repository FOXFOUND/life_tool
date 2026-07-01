package com.narrative.morality.asset;

/**
 * 叙事维度道德资产模型
 * 对应文档：文学批评维度 - 通用分阶段量化方程
 */
public class NarrativeMoralityAsset {
    // 叙事类型调节系数 α，取值范围 [0.5, 1.5]
    private final double alpha;
    // 施害者信用系数 C，取值范围 [0, 10]
    private final double perpetratorCredit;
    // 冲突不对称系数 K，取值范围 [1, +∞)
    private final double conflictAsymmetry;
    // 资产增值系数 r，取值范围 [0, 0.15]
    private final double appreciationRate;
    // 兑现行为效率系数 e，取值范围 [0, 1]
    private final double redemptionEfficiency;
    // 资产发行节点 t1
    private final int issueTime;
    // 增值结束节点 t2
    private final int appreciationEndTime;
    // 兑现完成节点 t3
    private final int redemptionEndTime;

    /**
     * 构造叙事道德资产模型
     * @param alpha 叙事类型调节系数
     * @param perpetratorCredit 施害者信用系数
     * @param conflictAsymmetry 冲突不对称系数
     * @param appreciationRate 资产增值系数
     * @param redemptionEfficiency 兑现效率系数
     * @param issueTime 资产发行时间节点
     * @param appreciationEndTime 增值结束时间节点
     * @param redemptionEndTime 兑现完成时间节点
     */
    public NarrativeMoralityAsset(double alpha, double perpetratorCredit, double conflictAsymmetry,
                                  double appreciationRate, double redemptionEfficiency,
                                  int issueTime, int appreciationEndTime, int redemptionEndTime) {
        // 参数合法性校验
        if (alpha < 0.5 || alpha > 1.5) {
            throw new IllegalArgumentException("alpha 应在 [0.5, 1.5] 范围内");
        }
        if (perpetratorCredit < 0 || perpetratorCredit > 10) {
            throw new IllegalArgumentException("施害者信用系数应在 [0, 10] 范围内");
        }
        if (conflictAsymmetry < 1) {
            throw new IllegalArgumentException("冲突不对称系数应 ≥ 1");
        }
        if (appreciationRate < 0 || appreciationRate > 0.15) {
            throw new IllegalArgumentException("增值系数应在 [0, 0.15] 范围内");
        }
        if (redemptionEfficiency < 0 || redemptionEfficiency > 1) {
            throw new IllegalArgumentException("兑现效率应在 [0, 1] 范围内");
        }
        if (issueTime >= appreciationEndTime || appreciationEndTime >= redemptionEndTime) {
            throw new IllegalArgumentException("时间节点需满足 t1 < t2 < t3");
        }

        this.alpha = alpha;
        this.perpetratorCredit = perpetratorCredit;
        this.conflictAsymmetry = conflictAsymmetry;
        this.appreciationRate = appreciationRate;
        this.redemptionEfficiency = redemptionEfficiency;
        this.issueTime = issueTime;
        this.appreciationEndTime = appreciationEndTime;
        this.redemptionEndTime = redemptionEndTime;
    }

    /**
     * 冲突赋值阶段：计算初始资产面值 M(t1)
     * 公式：M(t1) = α × C × K
     */
    public double calculateInitialAsset() {
        return alpha * perpetratorCredit * conflictAsymmetry;
    }

    /**
     * 苦难持有阶段：计算任意时间t的资产价值 M(t)
     * 公式：M(t) = M(t1) × (1 + r)^(t - t1)
     * @param t 目标时间节点
     */
    public double calculateAssetAtTime(int t) {
        if (t < issueTime) {
            return 0;
        }
        double initialAsset = calculateInitialAsset();
        int deltaTime = t - issueTime;
        return initialAsset * Math.pow(1 + appreciationRate, deltaTime);
    }

    /**
     * 计算增值结束时的账面资产 M(t2)
     */
    public double calculateAppreciatedAsset() {
        return calculateAssetAtTime(appreciationEndTime);
    }

    /**
     * 兑现阶段：计算最终变现资产 M(t3)
     * 公式：M(t3) = M(t2) × e
     */
    public double calculateFinalRedeemedAsset() {
        return calculateAppreciatedAsset() * redemptionEfficiency;
    }

    /**
     * 全叙事周期总价值核算
     * 公式：M_total = α × C × K × (1 + r)^(t2 - t1) × e
     */
    public double calculateTotalValue() {
        return calculateFinalRedeemedAsset();
    }
}