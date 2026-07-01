package com.narrative.morality.asset;

/**
 * 道德资产模型验证演示
 * 复现文档中的所有案例计算
 */
public class MoralityAssetModelDemo {
    public static void main(String[] args) {
        // ==================== 1. 叙事维度案例验证 ====================
        System.out.println("===== 叙事维度案例 =====");

        // 案例1：男频《斗破苍穹》
        NarrativeMoralityAsset doupo = new NarrativeMoralityAsset(
                0.9, 8, 10, 0.04, 0.95, 0, 36, 37
        );
        System.out.println("《斗破苍穹》道德资产：");
        System.out.printf("  初始面值 M(t1): %.2f%n", doupo.calculateInitialAsset());
        System.out.printf("  增值后市值 M(t2): %.2f%n", doupo.calculateAppreciatedAsset());
        System.out.printf("  最终总价值 M_total: %.2f%n", doupo.calculateTotalValue());

        // 案例2：经典戏剧《窦娥冤》
        NarrativeMoralityAsset doue = new NarrativeMoralityAsset(
                1.0, 7, 12, 0.08, 0.9, 0, 5, 6
        );
        System.out.println("\n《窦娥冤》道德资产：");
        System.out.printf("  初始面值 M(t1): %.2f%n", doue.calculateInitialAsset());
        System.out.printf("  增值后市值 M(t2): %.2f%n", doue.calculateAppreciatedAsset());
        System.out.printf("  最终总价值 M_total: %.2f%n", doue.calculateTotalValue());

        // ==================== 2. 社会心理维度案例验证 ====================
        System.out.println("\n===== 社会心理维度案例 =====");

        // 案例1：男频读者群体
        SocialPsychologyModel maleReaders = new SocialPsychologyModel(
                0.9, 7.6, 7.8, 0.85, 0.93, 0.88
        );
        System.out.println("男频读者群体心理指数：");
        System.out.printf("  共情指数 E: %.2f%n", maleReaders.calculateEmpathyIndex());
        System.out.printf("  爽感值 U: %.2f%n", maleReaders.calculatePleasureValue());

        // 案例2：《狂飙》大众受众
        SocialPsychologyModel generalAudience = new SocialPsychologyModel(
                1.0, 6.8, 8.2, 0.78, 0.9, 0.85
        );
        System.out.println("\n《狂飙》大众受众心理指数：");
        System.out.printf("  共情指数 E: %.2f%n", generalAudience.calculateEmpathyIndex());
        System.out.printf("  爽感值 U: %.2f%n", generalAudience.calculatePleasureValue());

        // ==================== 3. 商业维度案例验证 ====================
        System.out.println("\n===== 商业维度案例 =====");

        // 案例1：《斗罗大陆》IP变现
        CommercialMonetizationModel douluo = new CommercialMonetizationModel(
                1.2, 0.9, 0.68, 0.1, 0.15, 0.5
        );
        System.out.println("《斗罗大陆》IP商业变现：");
        System.out.printf("  流量价值 V: %.2f%n", douluo.calculateTrafficValue());
        System.out.printf("  总收益 Y: %.2f%n", douluo.calculateTotalRevenue());

        // 案例2：短视频正能量剧情号
        CommercialMonetizationModel shortVideo = new CommercialMonetizationModel(
                1.1, 0.85, 0.6, 0.05, 0.25, 0.1
        );
        System.out.println("\n短视频正能量剧情号变现：");
        System.out.printf("  流量价值 V: %.2f%n", shortVideo.calculateTrafficValue());
        System.out.printf("  总收益 Y: %.2f%n", shortVideo.calculateTotalRevenue());

        // ==================== 4. 全链路联动演示 ====================
        System.out.println("\n===== 全链路联动演示（叙事→心理→商业） =====");
        // 1. 叙事产出资产价值
        double narrativeAsset = doupo.calculateTotalValue();
        // 2. 代入心理模型计算爽感
        SocialPsychologyModel linkModel = new SocialPsychologyModel(
                0.9, 7.6, narrativeAsset / 10, 0.85, 0.93, 0.88
        );
        double pleasure = linkModel.calculatePleasureValue();
        // 3. 代入商业模型计算收益
        CommercialMonetizationModel linkBusiness = new CommercialMonetizationModel(
                1.2, 0.9, pleasure, 0.1, 0.15, 0.5
        );
        System.out.printf("联动计算总收益: %.2f%n", linkBusiness.calculateTotalRevenue());
    }
}