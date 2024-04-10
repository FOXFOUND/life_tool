package k.problem;

class Solution {
    final int MOD = (int) 1e9 + 7;
    public int kInversePairs(int n, int k) {
        long[][] dp = new long[n + 1][k + 1];
        dp[0][0] = 1;
        for(int i = 1; i <= n; i++){
            dp[i][0] = 1;
            for(int j = 1; j <= k; j++){
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                if(j >= i)dp[i][j] -= dp[i - 1][j - i];
                if(dp[i][j] < 0)dp[i][j] += MOD;
                dp[i][j] = dp[i][j] % MOD;
            }
        }
        return (int)dp[n][k];
    }
    /**
     * 作者：LittleSongFly
     *         链接：https://leetcode.cn/problems/k-inverse-pairs-array/solution/xiao-song-man-bu-qing-xi-yi-dong-cong-qi-kdlf/
     *         来源：力扣（LeetCode）
     *         著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
}

