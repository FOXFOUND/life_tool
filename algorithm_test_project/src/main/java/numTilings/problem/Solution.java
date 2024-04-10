package numTilings.problem;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int n = 4;
        int res = solution.numTilings(n);
        System.out.println(res);
    }

    public int numTilings(int n) {

        if (n == 0) {
            return 0;
        }

        if (n == 1) {
            return 1;
        }

        if (n == 2) {
            return 2;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 5;

        for (int i = 4; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                //问题,可能出现重复列 ,例如 : 1 (2,3,4,1) 和 (1,2,3,4),1是同一种
                dp[i] += dp[j] * dp[i - j];
            }
        }

        return dp[n];
    }
}
