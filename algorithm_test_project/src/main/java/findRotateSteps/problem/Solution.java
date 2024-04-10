package findRotateSteps.problem;

import java.util.ArrayList;

/**
 * 电子游戏“辐射4”中，任务 “通向自由” 要求玩家到达名为 “Freedom Trail Ring” 的金属表盘，并使用表盘拼写特定关键词才能开门。
 *
 * 给定一个字符串 ring ，表示刻在外环上的编码；给定另一个字符串 key ，表示需要拼写的关键词。您需要算出能够拼写关键词中所有字符的最少步数。
 *
 * 最初，ring 的第一个字符与 12:00 方向对齐。您需要顺时针或逆时针旋转 ring 以使 key 的一个字符在 12:00 方向对齐，然后按下中心按钮，以此逐个拼写完 key 中的所有字符。
 *
 * 旋转 ring 拼出 key 字符 key[i] 的阶段中：
 *
 * 您可以将 ring 顺时针或逆时针旋转 一个位置 ，计为1步。旋转的最终目的是将字符串 ring 的一个字符与 12:00 方向对齐，并且这个字符必须等于字符 key[i] 。
 * 如果字符 key[i] 已经对齐到12:00方向，您需要按下中心按钮进行拼写，这也将算作 1 步。按完之后，您可以开始拼写 key 的下一个字符（下一阶段）, 直至完成所有拼写。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/freedom-trail
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int findRotateSteps(String ring, String key) {
        /*
         * 贪心的算法：->举出了反例，是不正确的
         *
         * 最直观的转移方程：
         *
         * dp[i]: 代表到key[i]为止，拼接所需要的最少步数
         * dp[i-1]: 到key[i - 1]为止，拼接所需要的最少步数
         *
         * dp[i] = dp[i - 1] + (从key[i-1]到key[i]在圆盘上所要走的最短距离) + 1 （按button需要的步数为1）
         *
         * 下标x和下标y在圆盘上的最短距离： |x - y| 或者 n - |x - y|
         * 即Math.min(Math.abs(x - y), n - Math.abs(x - y))
         *
         * 但是，
         * key[i-1] 在圆盘上可以出现多次
         * key[i] 在圆盘上可以出现多次
         * 因此一个维度是不够的，再增加一个维度
         *
         * 定义转移方程：
         * dp[i][j] 代表到key[i]为止拼接所需要的最少步数，
         * 并且这个key[i]是第j个在圆盘上出现的key[i]
         *
         * 比如说key[i] = 'd'，在ring圆盘上出现位置的下标：2, 7, 8
         * dp[i][0] 代表到key[i]为止拼接所需要的最少步数，并且这个key[i]是位于下标位置为2的key[i]
         * dp[i][1] 代表到key[i]为止拼接所需要的最少步数，并且这个key[i]是位于下标位置为7的key[i]
         * ...以此类推
         *
         * 上一个字符是key[i-1] = 'a'，在ring圆盘上出现的位置下标是: 4, 9
         * dp[i][0] 代表到key[i - 1]为止拼接所需要的最少步数，并且这个key[i - 1]是位于下标位置为4的key[i]
         * dp[i][1] 代表到key[i - 1]为止拼接所需要的最少步数，并且这个key[i - 1]是位于下标位置为9的key[i]
         *
         * dp[i][j] =
         * Math.min(
         * dp[i-1][0] + 上一个字符key[i-1]（第0个出现的key[i - 1]）到这一个字符key[i]（第j个出现的key[i]）的最短距离,
         * dp[i-1][1] + 上一个字符key[i-1]（第1个出现的key[i - 1]）到这一个字符key[i]（第j个出现的key[i]）的最短距离,
         * ....
         * dp[i-1][k] + 上一个字符key[i-1]（第k个出现的key[i - 1]）到这一个字符key[i]（第j个出现的key[i]）的最短距离,
         * )  + 1 (按button的步数为1)
         * */

        char[] ringChar = ring.toCharArray();
        char[] keyChar = key.toCharArray();

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

                dp[i][j] = minSteps;
            }
        }

        // dp[keyChar.length - 1][0], .... dp[keyChar.length - 1][k] 中的最小值，就是最终拼接key所需要的最少步数

        int ans = Integer.MAX_VALUE;
        for (int k = 0; k < 150; k++) {
            // 当等于0时，说明已经越界了，直接跳出循环
            if (dp[keyChar.length - 1][k] == 0) break;
            ans = Math.min(ans, dp[keyChar.length - 1][k]);
        }

        return ans;

    }

}

//作者：viktorxhzj
//        链接：https://leetcode.cn/problems/freedom-trail/solution/javadong-tai-gui-hua-by-viktorxhzj/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
