package minCostClimbingStairs.problem;

public class Solution {
    public int minCostClimbingStairs(int[] cost) {

        int n = cost.length;

        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return Math.min(cost[0], cost[1]);
        }

        //dp代表到达n时的最小代价
        int[] dp = new int[n + 2];
        dp[0] = 0;
        dp[1] = 0;

        for (int i = 2; i < n + 1; i++) {

            dp[i] = Math.min(dp[i - 2] + cost[i - 2], dp[i - 1] + cost[i - 1]);
        }
        return dp[n];
    }
}
