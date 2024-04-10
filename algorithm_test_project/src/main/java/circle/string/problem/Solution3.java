package circle.string.problem;

class Solution3 {
    public boolean checkPartitioning(String s) {
        int n = s.length();
        char[] chs = s.toCharArray();
        boolean[][] dp = new boolean[n][n];
        for (int i = n - 1; i >= 0 ; i--) {
            for (int j = i; j < n; j++) {
                if (i == j || (j - i == 1 && chs[i] == chs[j])) dp[i][j] = true;
                else if (chs[i] == chs[j]) dp[i][j] = dp[i + 1][j - 1];
            }
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                if (dp[0][i] && dp[i + 1][j] && dp[j + 1][n - 1]) return true;
            }
        }
        return false;
    }

    /**
     * 作者：bloom-
     *         链接：https://leetcode.cn/problems/palindrome-partitioning-iv/solution/hui-wen-chuan-fen-ge-by-mei-56-b98t/
     *         来源：力扣（LeetCode）
     *         著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
}

