package maxValue.problem;

public class Solution {
    public int maxValue(int[][] grid) {

        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = Math.max(dp[i][j - 1] + grid[i - 1][j - 1], dp[i - 1][j] + grid[i - 1][j - 1]);
            }
        }
        return dp[n][m];

    }
}
