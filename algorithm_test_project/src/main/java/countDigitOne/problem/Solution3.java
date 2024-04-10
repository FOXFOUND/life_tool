package countDigitOne.problem;

import java.util.Arrays;

class Solution3 {
    //dp[i][j]表示在不受is_limit限制且第i位之前有j位为1的条件下，后面几位中可以产生1的个数
    //为什么要有一个is_limit的限制?设一个数字为303，如果第一位小于3，那么后面几位就没有任何限制，
    // 可以任意取，在这个过程中就会给dp[i][j]进行赋值，如果第一位取3，那么第二位就只能取0，
    // 如果仍然按照没有is_limit的限制去取值，就会取到第二位为1的情况，此时数字就会大于原数字，
    // 所以需要在is_limit的限制下保存值
    int[][] dp;

    public int countDigitOne(int n) {
        String s = n + "";
        dp = new int[s.length()][s.length()];
        //填充为-1 用来标记当前状态没有被赋值
        for (int i = 0; i < s.length(); ++i) Arrays.fill(dp[i], -1);
        return f(s, 0, 0, true);
    }



    /*
        s就是n的字符串形式
        i表示当前处于第几位
        cnt表示已经填写的前i位中有几个1
        is_limit表示当前位的数字是否有限制 例如:s为123 假如前两位填写了12那么第三位就限制为0~3
    */


    /*
     *  f方法是自(0,0)开始向(s.length,s.length) 进行展开的,当触底(s.length,s.length)之后,向上收敛
     *
     * */
    public int f(String s, int i, int cnt, boolean is_limit) {
        //如果每一个位都填写好了数字 就将统计好的1的个数返回
        if (i == s.length()) return cnt;
        //如果当前位没有收到限制 且之前已经计算过了当前的dp情况 就直接返回
        if (!is_limit && dp[i][cnt] >= 0) return dp[i][cnt];
        //计算当前位的上界
        int up = is_limit ? s.charAt(i) - '0' : 9;
        int res = 0;

        /**
         * 转移方程 : dp[n][m] = (k-1) * dp[n+1][m] + dp[n+1][m+1]
         * 含义 : 对于从左n位时,存在m个1的个数是,从左n+1位的m个1和从左n+1的m+1个1的和
         */

        //遍历小于当前位的所有数 ,例如  xxx4xxxx, 则遍历xxx0xxxx,xxx1xxxx,xxx2xxxx,xxx3xxxx,xxx4xxxx
        for (int k = 0; k <= up; ++k) {
            res += f(s, i + 1, cnt + (k == 1 ? 1 : 0), is_limit && k == up);
        }


        //如果当前位没有收到限制就赋值
        if (!is_limit) dp[i][cnt] = res;
        return res;
    }
}
