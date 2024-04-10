package countSubstrings.problem;

public class Solution3 {
    int countSubstrings(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = true;
        }

        int ans = n; // 一位字符一定为回文串
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) != s.charAt(j)) continue;
                if (j == i + 1 || dp[i + 1][j - 1]) {
                    ans++;
                    dp[i][j] = true;
                }
            }
        }
        return ans;
    }

}
