package getProbability.problem;

/**
 * 桌面上有 2n 个颜色不完全相同的球，球上的颜色共有 k 种。给你一个大小为 k 的整数数组 balls ，其中 balls[i] 是颜色为 i 的球的数量。
 *
 * 所有的球都已经 随机打乱顺序 ，前 n 个球放入第一个盒子，后 n 个球放入另一个盒子（请认真阅读示例 2 的解释部分）。
 *
 * 注意：这两个盒子是不同的。例如，两个球颜色分别为 a 和 b，盒子分别为 [] 和 ()，那么 [a] (b) 和 [b] (a) 这两种分配方式是不同的（请认真阅读示例的解释部分）。
 *
 * 请返回「两个盒子中球的颜色数相同」的情况的概率。答案与真实值误差在 10^-5 以内，则被视为正确答案
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/probability-of-a-two-boxes-having-the-same-number-of-distinct-balls
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class Solution {
    public double getProbability(int[] balls) {
        int n = balls.length;
        int sum = 0;
        double ans = 0;
        for (int ball: balls) {
            sum += ball;
        }
        // 阶乘初始化
        double[] fac = new double[sum + 1];
        fac[0] = 1;
        for (int i = 1; i <= sum; i++)
            fac[i] = fac[i - 1] * i;

        sum >>= 1;
        /* dp数组初始化
            dp数组解释：dp[i][j][k]:表示在第i个颜色球时，选择的k个球的与未选择的球的颜色差为j时的方案数，
            此时未考虑k个球的内部排序，且j可能为负数，所以整体偏移了n+1个单位。
         */
        double[][][] dp = new double[n + 1][2 * n + 3][sum + 1];
        dp[0][n + 1][0] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= balls[i - 1]; j++) {
                for (int k = j; k <= sum; k++) {
                    for (int r = 1; r < 2 * n + 2; r++) {
                        if (j == 0) {
                            dp[i][r - 1][k] += dp[i - 1][r][k];
                        } else if (j == balls[i - 1]) {
                            dp[i][r + 1][k] += dp[i - 1][r][k - j];
                        } else {
                            dp[i][r][k] += dp[i - 1][r][k - j] * fac[balls[i - 1]] / fac[balls[i - 1] - j] / fac[j];
                        }
                    }
                }
            }
        }
        //将前n个球和后n个球的内部顺序加上
        ans = dp[n][n + 1][sum] * fac[sum] * fac[sum];
        //除以总的可能数
        for (int i = 1; i <= sum * 2; i++) {
            ans /= i;
        }
        return ans;
    }
}

//作者：qing-xin-h2
//        链接：https://leetcode.cn/problems/probability-of-a-two-boxes-having-the-same-number-of-distinct-balls/solution/java-dong-tai-gui-hua-jian-dan-yi-dong-b-wx77/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
