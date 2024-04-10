package longestCommonSubsequence.problem;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();


        String s1 = "bsbininm";
        String s2 = "jmjkbkjkv";
        int res = solution.longestCommonSubsequence(s1, s2);
        System.out.println(res);
    }

    public int longestCommonSubsequence(String text1, String text2) {

        int n = text1.length();
        int m = text2.length();
        int[][] dp = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {

                if (i - 1 >= 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                }

                if (j - 1 >= 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
                }

                //出现 b..b 重复的问题
                if (text1.charAt(i) == text2.charAt(j)) {
                    if (i - 1 >= 0 && j - 1 >= 0) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = 1;
                    }
                }
            }

        }

        return dp[n - 1][m - 1];

    }
}
