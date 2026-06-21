package com.flexcorehr.model;

public class OptimalDispatchRatio {

    static class Industry {
        String name;
        double wp;   // 正式工工资率
        double f;    // 正式工制度分摊
        double wr;   // 派遣工工资率
        double m;    // 派遣中介溢价
        double gamma;// 派遣相对效率
        double sigma;// 需求波动率
        double eta;  // 正式工组织资本弹性
        double beta; // 协调摩擦系数

        public Industry(String name, double wp, double f, double wr, double m,
                        double gamma, double sigma, double eta, double beta) {
            this.name = name;
            this.wp = wp; this.f = f; this.wr = wr; this.m = m;
            this.gamma = gamma; this.sigma = sigma; this.eta = eta; this.beta = beta;
        }
    }

    public static double cEff(double phi, Industry ind) {
        double numerator = (ind.wp + ind.f) * (1 - phi)
                + ((ind.wr + ind.m) / (4 * ind.gamma * ind.sigma)) * Math.pow(ind.sigma + phi, 2);
        double denominator = 1 + ind.eta * (1 - phi) - ind.beta * phi * phi;
        return numerator / denominator;
    }

    public static double marginalCost(double phi, Industry ind) {
        double ce = cEff(phi, ind);
        double part1 = ce * (ind.eta + 2 * ind.beta * phi);
        double part2 = (ind.wr + ind.m) * (ind.sigma + phi) / (2 * ind.gamma * ind.sigma);
        double part3 = ind.wp + ind.f;
        return part1 + part2 - part3;
    }

    public static double findOptimalPhi(Industry ind, double step) {
        double bestPhi = 0;
        double minCeff = Double.MAX_VALUE;
        for (double phi = 0; phi <= 1.0; phi += step) {
            double ce = cEff(phi, ind);
            if (ce < minCeff) {
                minCeff = ce;
                bestPhi = phi;
            }
        }
        return bestPhi;
    }

    public static void main(String[] args) {
        // 定义主流行业参数（工资指数为相对值）
        Industry[] industries = new Industry[] {
                new Industry("物流仓储",      40, 10, 30, 5,  0.90, 0.40, 0.15, 0.20),
                new Industry("餐饮酒店",      42, 12, 32, 6,  0.85, 0.45, 0.08, 0.25),
                new Industry("制造业(一般)",  50, 15, 35, 7,  0.88, 0.25, 0.15, 0.20),
                new Industry("信息技术",      60, 20, 45, 8,  0.80, 0.20, 0.22, 0.35),
                new Industry("零售业",        38, 9,  28, 4,  0.92, 0.50, 0.06, 0.18),
                new Industry("建筑业",        55, 16, 38, 6,  0.85, 0.55, 0.12, 0.22),
                new Industry("金融业",        70, 25, 50, 10, 0.75, 0.15, 0.25, 0.40),
                new Industry("医疗护理",      48, 14, 35, 7,  0.78, 0.30, 0.20, 0.30),
                new Industry("教育业",        45, 13, 33, 6,  0.82, 0.18, 0.18, 0.28),
                new Industry("季节性农业加工",30, 6,  22, 3,  0.95, 0.60, 0.03, 0.10)
        };

        double step = 0.0001; // 扫描步长
        System.out.println("=================================================");
        System.out.println("      主流行业最优派遣比例 (φ*) 计算");
        System.out.println("=================================================");
        System.out.printf("%-16s  %8s  %10s  %12s\n", "行业", "φ*", "C_eff_min", "MC(φ*)");
        System.out.println("-------------------------------------------------");

        for (Industry ind : industries) {
            double phiStar = findOptimalPhi(ind, step);
            double ceMin = cEff(phiStar, ind);
            double mcAtOpt = marginalCost(phiStar, ind);
            System.out.printf("%-16s  %8.4f  %10.2f  %12.6f\n",
                    ind.name, phiStar, ceMin, mcAtOpt);
        }
        System.out.println("-------------------------------------------------");
        System.out.println("注：MC(φ*) 应接近 0，验证最优性。");
        System.out.println("=================================================");
    }
}