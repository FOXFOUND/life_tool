package makesquare.problem;

import java.util.Arrays;

/**
 *
 */
public class Solution3 {
    public boolean makesquare(int[] matchsticks) {
        int totalLen = Arrays.stream(matchsticks).sum();
        if (totalLen % 4 != 0) {
            return false;
        }
        int len = totalLen / 4, n = matchsticks.length;
        //dp[s] 表示正方形未放满的边的当前长度，计算如下：
        int[] dp = new int[1 << n];
        Arrays.fill(dp, -1);
        dp[0] = 0;
        for (int s = 1; s < (1 << n); s++) {
            for (int k = 0; k < n; k++) {
                if ((s & (1 << k)) == 0) {
                    continue;
                }
                int s1 = s & ~(1 << k);
                if (dp[s1] >= 0 && dp[s1] + matchsticks[k] <= len) {
                    dp[s] = (dp[s1] + matchsticks[k]) % len;
                    break;
                }
            }
        }
        // dp[(1 << n) - 1] 代表经过了所有的情况,最后判断是否存在
        // dp[(1 << n) - 1] == 0 情况的分析  dp[s1] + matchsticks[k] == len

        return dp[(1 << n) - 1] == 0;
    }

    public static void main(String[] args) {
        Solution3 solution3 = new Solution3();
        //int[] matchsticks = {1, 1, 2, 2, 2};
        //int[] matchsticks = {3, 3, 3, 3, 4};   // false
        int[] matchsticks = {10, 6, 5, 5, 5, 3, 3, 3, 2, 2, 2, 2}; //true
        boolean res = solution3.makesquare(matchsticks);
        System.out.println(res);
    }

//    作者：LeetCode-Solution
//    链接：https://leetcode.cn/problems/matchsticks-to-square/solution/huo-chai-pin-zheng-fang-xing-by-leetcode-szdp/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
