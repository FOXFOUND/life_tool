package unique.paths.ii.problem;

public class Solution {

    /**
     * https://leetcode.cn/problems/unique-paths-ii/submissions/
     *
     * 动态规划
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[][] dp = new int[obstacleGrid.length][obstacleGrid[0].length];
        for (int i = 0; i < obstacleGrid.length; i++) {
            dp[i][0] = 1;
            if (obstacleGrid[i][0] == 1) {
                dp[i][0] = 0;
                break;
            }
        }
        for (int j = 0; j < obstacleGrid[0].length; j++) {
            dp[0][j] = 1;
            if (obstacleGrid[0][j] == 1) {
                dp[0][j] = 0;
                break;
            }
        }
        for (int i = 1; i < obstacleGrid.length; i++) {
            for (int j = 1; j < obstacleGrid[0].length; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                }
            }
        }

        /**
         * 时间复杂度和空间复杂度 o(nm)
         */
        return dp[obstacleGrid.length - 1][obstacleGrid[0].length - 1];
    }

    public static void main(String[] args) {
        int[][] obstacleGrid = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        Solution solution = new Solution();
        System.out.println(solution.uniquePathsWithObstacles(obstacleGrid));
    }
}
