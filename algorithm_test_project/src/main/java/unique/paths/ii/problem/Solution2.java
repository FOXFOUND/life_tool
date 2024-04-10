package unique.paths.ii.problem;

public class Solution2 {

    /**
     * https://leetcode.cn/problems/unique-paths-ii/submissions/
     * <p>
     * 动态规划  + 滚动数组
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int[] dp = new int[obstacleGrid[0].length];

        //初始化条件
        dp[0] = obstacleGrid[0][0] == 1 ? 0 : 1;

        //转移方程
        for (int i = 0; i < obstacleGrid.length; i++) {
            for (int j = 0; j < obstacleGrid[0].length; j++) {
                if(j> 0){
                    dp[j] += dp[j - 1];
                }

                //限制条件
                if (obstacleGrid[i][j] == 1) {
                    dp[j] = 0;
                }
            }
        }

        return dp[obstacleGrid[0].length - 1];
    }

    public static void main(String[] args) {
        int[][] obstacleGrid = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        Solution2 solution = new Solution2();
        System.out.println(solution.uniquePathsWithObstacles(obstacleGrid));
    }
}
