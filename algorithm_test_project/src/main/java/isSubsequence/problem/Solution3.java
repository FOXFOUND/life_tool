package isSubsequence.problem;


class Solution3 {

    boolean isSubsequence(String s, String t) {

        //dp做法,最长公共子序列
        int[][] dp = new int[102][10002];
        for (int i = 0; i <= s.length(); i++) {
            dp[i][0] = 0;
            dp[0][i] = 0;
        }
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        if (dp[s.length()][t.length()] == s.length()) {
            return true;
        } else {
            return false;
        }
    }
};

//作者：jihuan-b
//        链接：https://leetcode.cn/problems/is-subsequence/solution/shuang-zhi-zhen-dong-tai-gui-hua-liang-c-wecu/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
