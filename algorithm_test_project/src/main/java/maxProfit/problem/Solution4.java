package maxProfit.problem;

public class Solution4 {
    public int maxProfit(int[] prices) {


        int n = prices.length;
        if (n == 0) {
            return 0;
        }
        int[] dp = new int[n];
        int min = 0;
        for (int i = 0; i < n; i++) {
            if (prices[i] < prices[min]) {
                min = i;
            }
            if (i - 1 >= 0) {
                dp[i] = Math.max(dp[i - 1], prices[i] - prices[min]);
            }
        }
        return dp[n - 1];
    }
}
