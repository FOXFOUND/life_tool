package com.flexcorehr.model;

import java.util.ArrayList;
import java.util.List;

public class LaborMixModelFull {

    // ---------- 参数类 ----------
    static class Params {
        double wp, f, wr, m, gamma, eta, beta, sigma;

        public Params(double wp, double f, double wr, double m, double gamma,
                      double eta, double beta, double sigma) {
            this.wp = wp;
            this.f = f;
            this.wr = wr;
            this.m = m;
            this.gamma = gamma;
            this.eta = eta;
            this.beta = beta;
            this.sigma = sigma;
        }
    }

    // ---------- 核心公式（同前） ----------
    public static double cEff(double phi, Params p) {
        return numeratorCeff(phi, p) / denominatorCeff(phi, p);
    }

    public static double numeratorCeff(double phi, Params p) {
        return (p.wp + p.f) * (1 - phi)
                + ((p.wr + p.m) / (4 * p.gamma * p.sigma)) * Math.pow(p.sigma + phi, 2);
    }

    public static double denominatorCeff(double phi, Params p) {
        return 1 + p.eta * (1 - phi) - p.beta * phi * phi;
    }

    public static double mSafe(double phi, Params p) {
        double ceff = cEff(phi, p);
        double mcp = (p.wp + p.f) - (p.wr + p.m) * (p.sigma + phi) / (2 * p.gamma * p.sigma);
        double mlr = p.eta + 2 * p.beta * phi;
        return mcp / mlr / ceff;
    }

    public static double findOptimalPhi(Params p) {
        double bestPhi = 0, bestCost = Double.MAX_VALUE;
        double step = 1e-4;
        for (double phi = 0; phi <= 1.0; phi += step) {
            double cost = cEff(phi, p);
            if (cost < bestCost) {
                bestCost = cost;
                bestPhi = phi;
            }
        }
        return bestPhi;
    }

    // ---------- 动态收敛路径图 ----------
    public static void printConvergencePath(Params p) {
        double optPhi = findOptimalPhi(p);
        double optCost = cEff(optPhi, p);

        // 以 2.5% 为步长采样，兼顾精度和可读性
        double step = 0.025;
        List<Double> phis = new ArrayList<>();
        List<Double> costs = new ArrayList<>();
        double minCost = Double.MAX_VALUE, maxCost = Double.MIN_VALUE;
        for (double phi = 0; phi <= 1.0 + step / 2; phi += step) {
            double cost = cEff(phi, p);
            phis.add(phi);
            costs.add(cost);
            if (cost < minCost) minCost = cost;
            if (cost > maxCost) maxCost = cost;
        }

        // 防止除数0，当min==max时曲线平坦
        double range = maxCost - minCost;
        if (range < 1e-6) range = 1.0;

        System.out.println("\n【成本收敛路径图】 (派遣比例 → 有效成本率)");
        System.out.println("横轴: 派遣比例 (0~100%)，纵轴: C_eff 相对高度 (* 代表成本)");
        System.out.println("◆ = 最优派遣比例，成本最低点");
        System.out.println("--------------------------------------------------");

        for (int i = 0; i < phis.size(); i++) {
            double phi = phis.get(i);
            double cost = costs.get(i);
            int barLength = (int) ((cost - minCost) / range * 40); // 最大40个星号
            StringBuilder bar = new StringBuilder();
            for (int j = 0; j < barLength; j++) bar.append("*");
            if (Math.abs(phi - optPhi) < step / 2) {
                bar.append(" ◆"); // 标记最优
            }
            System.out.printf("  φ=%4.0f%%  C_eff=%.2f |%s%n", phi * 100, cost, bar.toString());
        }
        System.out.println("--------------------------------------------------");
        System.out.printf("★ 最低成本出现在 φ=%.1f%% ，C_eff=%.2f%n%n", optPhi * 100, optCost);
    }

    // ---------- 人性化最优方案输出 ----------
    public static void printOptimalSolution(Params p, String caseName) {
        double optPhi = findOptimalPhi(p);
        double optCeff = cEff(optPhi, p);
        double optDen = denominatorCeff(optPhi, p);
        double ceff0 = cEff(0, p), den0 = denominatorCeff(0, p);
        double ceff1 = cEff(1, p), den1 = denominatorCeff(1, p);

        System.out.println("=============================================");
        System.out.println("   " + caseName);
        System.out.println("=============================================");
        System.out.println("▶ 输入的成本参数：");
        System.out.printf("   正式工：工资 %.0f + 制度分摊 %.0f = %.0f/人\n", p.wp, p.f, p.wp + p.f);
        System.out.printf("   派遣工：工资 %.0f + 中介费 %.0f = %.0f/人，效率折扣 %.0f%%\n",
                p.wr, p.m, p.wr + p.m, (1 - p.gamma) * 100);
        System.out.printf("   行业特征：需求波动 σ=%.2f，技能积累弹性 η=%.2f，协调摩擦 β=%.2f\n",
                p.sigma, p.eta, p.beta);

        System.out.println("\n▶ 推荐用工方案：");
        System.out.printf("   ▎派遣工占比：  %.1f%%  （约 %d 人当中有 %d 人为派遣）\n",
                optPhi * 100, 100, (int) Math.round(optPhi * 100));
        System.out.printf("   ▎正式工占比：  %.1f%%  （稳定核心，负责组织知识积累）\n", (1 - optPhi) * 100);
        System.out.printf("   ▎单位有效产出的期望成本 C_eff = %.4f\n", optCeff);
        System.out.printf("   ▎有效产出乘数（名义→有效） = %.4f\n", optDen);

        System.out.println("\n▶ 与其他极端方案对比：");
        System.out.printf("   · 全部用正式工（φ=0%%）   : C_eff = %8.2f，有效产出乘数 %.4f\n", ceff0, den0);
        System.out.printf("   · 全部用派遣工（φ=100%%） : C_eff = %8.2f，有效产出乘数 %.4f\n", ceff1, den1);
        System.out.printf("   · 本模型推荐混合比例       : C_eff = %8.2f  ← 最低成本\n", optCeff);

        // 成本节约分析
        double extremeMinCost = Math.min(ceff0, ceff1);
        if (optCeff < extremeMinCost - 0.01) {
            double saving = (1 - optCeff / extremeMinCost) * 100;
            System.out.printf("\n   ★ 混合用工相比最佳极端方案，可再节省成本 %.1f%%\n", saving);
        } else if (optPhi < 0.001) {
            System.out.println("\n   ★ 该场景下全正式工已是最优解，无需使用派遣");
        } else if (optPhi > 0.999) {
            System.out.println("\n   ★ 该场景下全派遣工已是最优解，无需保留正式工");
        }

        // 动态收敛过程图
        printConvergencePath(p);
    }

    // ---------- 主程序 ----------
    public static void main(String[] args) {
        Params logistics = new Params(40, 10, 30, 5, 0.9, 0.15, 0.2, 0.4);
        printOptimalSolution(logistics, "物流分拣中心（旺季脉冲型）");

        Params restaurant = new Params(42, 12, 32, 6, 0.85, 0.08, 0.25, 0.45);
        printOptimalSolution(restaurant, "餐饮酒店业（高波动、低技能依赖）");

        Params manufacturing = new Params(50, 15, 35, 7, 0.88, 0.15, 0.20, 0.25);
        printOptimalSolution(manufacturing, "制造业（中等波动、中等技能）");

        Params itServices = new Params(60, 20, 45, 8, 0.80, 0.22, 0.35, 0.20);
        printOptimalSolution(itServices, "信息技术服务业（低波动、高技能）");


        // 新增行业
        Params banking = new Params(55, 20, 40, 8, 0.78, 0.22, 0.30, 0.18);
        Params government = new Params(50, 25, 35, 5, 0.70, 0.25, 0.40, 0.08);
        Params education = new Params(45, 18, 32, 6, 0.72, 0.30, 0.35, 0.12);
        Params healthcare = new Params(52, 22, 38, 7, 0.75, 0.28, 0.32, 0.20);
        Params retail = new Params(38, 10, 28, 5, 0.92, 0.06, 0.22, 0.50);
        Params construction = new Params(48, 15, 35, 6, 0.85, 0.10, 0.28, 0.55);
        Params agriculture = new Params(30, 8, 22, 4, 0.90, 0.04, 0.18, 0.60);

        printOptimalSolution(banking, "银行业（低波动，高技能积累）");
        printOptimalSolution(government, "政府部门（极低波动，极高制度成本）");
        printOptimalSolution(education, "教育行业（低波动，极高技能弹性）");
        printOptimalSolution(healthcare, "医疗行业（中低波动，高技能专用性）");
        printOptimalSolution(retail, "零售业（高波动，低技能依赖）");
        printOptimalSolution(construction, "建筑业（极高波动，中等技能）");
        printOptimalSolution(agriculture, "农业（极高波动，极低技能弹性）");
    }
}