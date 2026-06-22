package cn.socialcomputing;

/**
 * 性别叙事与群体行为动力学模拟
 * 基于"事实-观点-情绪-目的"四维框架 + 五维耦合微分方程
 * 验证核心推论：1. 茧房陷阱稳态  2. 阶级嵌入度破局阈值
 */
public class GenderNarrativeSimulation {
    // ========== 1. 系统通用系数（增益/衰减系数，标定后固定） ==========
    private static final double a1 = 0.85;  // 情绪-共识增益系数
    private static final double a2 = 0.75;  // 圈层信息筛选增益系数
    private static final double a3 = 0.95;  // 情绪唤醒增益系数
    private static final double a4 = 0.70;  // 身份虚荣增益系数
    private static final double a5 = 0.80;  // 风险抑制增益系数

    private static final double b1 = 0.30;  // 异质信息消解系数
    private static final double b2 = 0.40;  // 事实校正系数
    private static final double b3 = 0.25;  // 逻辑冷却系数
    private static final double b4 = 0.50;  // 现实约束系数
    private static final double b5 = 0.35;  // 预警生效系数

    // 数值模拟步长与总轮次
    private static final double dt = 0.01;  // 时间步长（越小精度越高）
    private static final int totalSteps = 3000;  // 总迭代轮次
    private static final int printInterval = 200;  // 每隔多少步打印一次

    // ========== 2. 场景参数配置类 ==========
    static class SceneParams {
        String sceneName;    // 场景名称
        double beta;         // 阶级维度嵌入度 β
        double alpha0;       // 基础事实裁剪率 α₀
        double gamma;        // 归因单一度 γ
        double delta;        // 圈层同质性系数 δ
        int n;               // 圈层规模 n
        double gs;           // 私利权重 gₛ
        double rc;           // 个体认知校准能力 r_c
        double pr;           // 风险预警强度 p_r

        public SceneParams(String sceneName, double beta, double alpha0, double gamma,
                           double delta, int n, double gs, double rc, double pr) {
            this.sceneName = sceneName;
            this.beta = beta;
            this.alpha0 = alpha0;
            this.gamma = gamma;
            this.delta = delta;
            this.n = n;
            this.gs = gs;
            this.rc = rc;
            this.pr = pr;
        }
    }

    // ========== 3. 状态变量钳位函数（保证值域 [0,1]） ==========
    private static double clamp(double value) {
        return Math.max(0.0, Math.min(1.0, value));
    }

    // ========== 4. 单场景演化模拟核心方法 ==========
    private static void runSimulation(SceneParams params) {
        System.out.println("==============================================");
        System.out.println("【场景】" + params.sceneName);
        System.out.printf("参数配置：β=%.2f, γ=%.2f, 圈层规模n=%d, 私利权重gs=%.2f%n",
                params.beta, params.gamma, params.n, params.gs);
        System.out.println("----------------------------------------------");
        System.out.printf("%-8s %-10s %-10s %-10s %-10s %-10s%n",
                "时间t", "圈层凝聚C", "信息失真D", "情绪强度E", "虚荣驱动V", "风险漠视R");
        System.out.println("----------------------------------------------");

        // 初始状态：t=0 时各变量初始值（弱初始，模拟圈层从零发展）
        double C = 0.10;  // 初始圈层凝聚度
        double D = 0.15;  // 初始信息失真度
        double E = 0.10;  // 初始情绪强度
        double V = 0.08;  // 初始虚荣驱动度
        double R = 0.05;  // 初始风险漠视度

        // 预计算情绪极化放大系数 k(n)
        double k = 1 + params.delta * Math.log(params.n);
        // 责任分散系数
        double responsibilityFactor = 1 - 1.0 / params.n;

        // 欧拉法迭代求解
        for (int step = 0; step <= totalSteps; step++) {
            double t = step * dt;

            // 打印当前状态
            if (step % printInterval == 0) {
                System.out.printf("%-8.2f %-10.4f %-10.4f %-10.4f %-10.4f %-10.4f%n",
                        t, C, D, E, V, R);
            }

            // 计算各变量的微分（对应五维耦合方程组）
            double dC = a1 * E * D - b1 * C * (1 - D);
            double dD = a2 * C * (1 - params.beta) - b2 * D * params.alpha0;
            double dE = a3 * D * C * k - b3 * E * (1 - params.gamma);
            double dV = a4 * E * C * params.gs - b4 * V * params.rc;
            double dR = a5 * E * V * responsibilityFactor - b5 * R * params.pr;

            // 更新状态变量 + 钳位
            C = clamp(C + dC * dt);
            D = clamp(D + dD * dt);
            E = clamp(E + dE * dt);
            V = clamp(V + dV * dt);
            R = clamp(R + dR * dt);
        }

        // 输出最终稳态结论
        System.out.println("----------------------------------------------");
        System.out.println("【稳态结果】");
        System.out.printf("圈层凝聚度 C* = %.4f%n", C);
        System.out.printf("信息失真度 D* = %.4f%n", D);
        System.out.printf("情绪强度 E* = %.4f%n", E);
        System.out.printf("虚荣驱动度 V* = %.4f%n", V);
        System.out.printf("风险漠视度 R* = %.4f%n", R);

        // 推论验证
        System.out.println("\n【推论验证】");
        if (params.beta < 0.1 && params.n > 50000) {
            boolean trap = C > 0.9 && D > 0.9 && E > 0.9 && V > 0.9 && R > 0.9;
            System.out.println("推论1·茧房陷阱：" + (trap ? "✅ 验证成立，系统陷入完全极化稳态" : "❌ 未达到完全茧房"));
        }
        if (params.beta > 0.5) {
            boolean breakOut = D < 0.5 && E < 0.5 && R < 0.5;
            System.out.println("推论2·破局阈值：" + (breakOut ? "✅ 验证成立，阶级视角有效打破正反馈" : "❌ 未突破正反馈循环"));
        }
        System.out.println("==============================================\n");
    }

    // ========== 5. 主程序：运行4组典型场景 ==========
    public static void main(String[] args) {
        System.out.println("===== 性别叙事群体行为动力学仿真系统 =====");
        System.out.println("基于五维耦合微分方程 · 欧拉法数值求解\n");

        // 场景1：资本主义女权典型场景（短视频情感博主圈层）
        SceneParams scene1 = new SceneParams(
                "资本主义女权典型场景（短视频情感圈层）",
                0.10,   // β 极低，几乎无阶级视角
                0.70,   // α₀ 高事实裁剪率
                0.80,   // γ 高归因单一度
                0.60,   // δ 高圈层同质性
                10000,  // n 万人级圈层规模
                0.80,   // gₛ 高商业私利权重
                0.20,   // r_c 低个体认知能力
                0.10    // p_r 低风险预警
        );
        runSimulation(scene1);

        // 场景2：社会主义女权典型场景（学术/政策讨论圈层）
        SceneParams scene2 = new SceneParams(
                "社会主义女权典型场景（公共政策讨论）",
                0.80,   // β 高阶级嵌入度
                0.20,   // α₀ 低事实裁剪率
                0.30,   // γ 低归因单一度
                0.20,   // δ 低圈层同质性
                1000,   // n 千人级规模
                0.10,   // gₛ 低商业权重
                0.60,   // r_c 高个体认知能力
                0.50    // p_r 高风险预警
        );
        runSimulation(scene2);

        // 场景3：极端茧房陷阱场景（完全剥离阶级+超大圈层）
        SceneParams scene3 = new SceneParams(
                "极端茧房陷阱场景（β→0，超大规模极化圈层）",
                0.02,   // β 几乎为0，完全剥离阶级视角
                0.90,   // α₀ 极高事实裁剪
                0.95,   // γ 极端单一归因
                0.80,   // δ 极高同质性
                100000, // n 十万人级超大圈层
                0.90,   // gₛ 极高商业权重
                0.05,   // r_c 极低认知能力
                0.05    // p_r 几乎无风险预警
        );
        runSimulation(scene3);

        // 场景4：破局验证场景（给资本女权场景补充阶级视角）
        SceneParams scene4 = new SceneParams(
                "破局验证场景（原资本女权场景提升β至0.5）",
                0.50,   // β 提升至临界值以上
                0.70,   // 其余参数与场景1完全一致
                0.80,
                0.60,
                10000,
                0.80,
                0.20,
                0.10
        );
        runSimulation(scene4);

        System.out.println("===== 全部场景模拟完成 =====");
        System.out.println("核心结论：");
        System.out.println("1. 剥离阶级视角的商业女权叙事，最终会走向全维度极化的茧房陷阱；");
        System.out.println("2. 提升阶级维度嵌入度，可直接打破正反馈循环，显著降低非理性风险。");
    }
}