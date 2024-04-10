package translateNum.problem;

public class Solution {
    public int translateNum(int num) {

        String s = String.valueOf(num);
        int n = s.length();
        //代表到达第i位,有多少种数字方案
        int[] dp = new int[n + 1];
        dp[0] = 1;

        for (int i = 1; i < n; i++) {
            dp[i] = dp[i - 1];
            //兼容 01的情况
            if (Integer.valueOf(s.substring(i - 1, i)) != 0
                    && Integer.valueOf(s.substring(i - 1, i + 1)) <= 25) {

                if (i - 2 >= 0) {
                    dp[i] += dp[i - 2];
                } else {
                    //针对最开始的情况
                    dp[i] += 1;
                }

            }
        }
        return dp[n - 1];
    }
}
