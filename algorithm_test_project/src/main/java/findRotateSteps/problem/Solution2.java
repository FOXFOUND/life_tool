package findRotateSteps.problem;

import java.util.ArrayList;

class Solution2 {
    public int findRotateSteps(String ring, String key) {

        char[] ringChar = ring.toCharArray();
        char[] keyChar = key.toCharArray();

        //记忆搜索数组,通过记录数组中每个字母在位置,方便计算距离
        ArrayList<Integer>[] lists = new ArrayList[26];

        for (int i = 0; i < 26; i++) {
            lists[i] = new ArrayList<>();
        }

        // 遍历ring，存储每个字符出现的位置，即下标
        int n = ringChar.length, m = keyChar.length;

        for (int i = 0; i < n; i++) {
            char c = ringChar[i];
            // 找到对应的arraylist，存储下标
            lists[c - 'a'].add(i);
        }

        // ring 和 key的长度最多100，所以定个150很安全
        int[][] dp = new int[m][150];

        // dp[0][j] 只需要计算从12点方向到key[0]所需要走的最短距离
        for (int j = 0; j < lists[keyChar[0] - 'a'].size(); j++) {
            // 每一个key[0]字符所在的下标
            int x = lists[keyChar[0] - 'a'].get(j);
            // 第一个12点方向的字符的下标，其实就是0
            int y = lists[ringChar[0] - 'a'].get(0);
            // 顺时针旋转或者逆时针旋转
            dp[0][j] = Math.min(Math.abs(x - y), n - Math.abs(x - y)) + 1;
        }

        for (int i = 1; i < keyChar.length; i++) {
            // 列出当前的字符，和上一个的字符分别是什么
            char cur = keyChar[i], pre = keyChar[i - 1];
            for (int j = 0; j < lists[cur - 'a'].size(); j++) {
                // 当前字符cur出现在ring圆盘上每一个位置的下标
                int x = lists[cur - 'a'].get(j);
                int minSteps = Integer.MAX_VALUE;
                for (int k = 0; k < lists[pre - 'a'].size(); k++) {

                    // 上一个字符pre出现在ring圆盘上每一个位置的下标
                    int y = lists[pre - 'a'].get(k);

                    int steps = dp[i - 1][k] + Math.min(Math.abs(x - y), n - Math.abs(x - y)) + 1;

                    minSteps = Math.min(minSteps, steps);
                }

                //这一步很关键,dfs的问题需要无法兼容全局最优解,通过df[i][j]的值,可以累计算出全局最优解
                dp[i][j] = minSteps;
            }
        }

        // dp[keyChar.length - 1][0], .... dp[keyChar.length - 1][k] 中的最小值，就是最终拼接key所需要的最少步数

        int ans = Integer.MAX_VALUE;
        for (int k = 0; k < 150; k++) {
            //当等于0时，说明已经越界了，直接跳出循环
            //按照题目描述ring中的字符已经包含key的所有字符,即key是ring的子集,也是说key一定你能从ring推导而出,如果为空说明k已经超出ring的范围
            if (dp[keyChar.length - 1][k] == 0) break;
            ans = Math.min(ans, dp[keyChar.length - 1][k]);
        }

        return ans;

    }
}

//作者：anonymous-39
//        链接：https://leetcode.cn/problems/freedom-trail/solution/viktorxhzj-dai-ma-xiang-jie-bu-chong-don-2cw1/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。