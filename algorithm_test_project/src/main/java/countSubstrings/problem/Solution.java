package countSubstrings.problem;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = "abc";
        int res = solution.countSubstrings(s);
        System.out.println(res);
    }


    public int countSubstrings(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                if (i == j) {
                    dp[i][j] = 1;
                }

                if (i - 1 >= 0) {
                    dp[i][j] += dp[i - 1][j];
                }

                if (j - 1 >= 0) {
                    dp[i][j] += dp[i][j - 1];
                }

                if (i - 1 >= 0 && j - 1 >= 0) {
                    dp[i][j] += dp[i - 1][j - 1];
                }

            }
        }

        return dp[0][n - 1];
    }
}
