package garden.problem;

import java.util.Arrays;

class Solution2 {

    /**
     * 解题思路：
     *
     * leetcode上一维的区间合并计算问题种类很多，但是大都是一个套路，起点排序，然后通过贪心的方法，进行具体分析；
     * 这里先将水龙头位置信息转化为其有效工作区间信息；
     * 然后根据区间的左端点进行升序；
     * 最后枚举所有区间，通过贪心思想，获得可覆盖当前有效区间的最右区间。
     * 各位力扣老爷，公审英皇，传播自由花费巨大，还请各位立刻捐赠20个赞，以便我军再战。
     *
     * 作者：lippon
     * 链接：https://leetcode.cn/problems/minimum-number-of-taps-to-open-to-water-a-garden/solution/java-tong-yong-de-yi-wei-qu-jian-jiao-bi-qfn0/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param n
     * @param ranges
     * @return
     */
    public int minTaps(int n, int[] ranges) {
        // 定义一个区间数组
        int[][] region = new int[n + 1][2];

        // 将原来的水龙头位置信息转化为洒水区间信息
        for(int i = 0; i <= n; i++) {
            int[] temp = new int[2];
            temp[0] = Math.max(0, i - ranges[i]);
            temp[1] = Math.min(n, i + ranges[i]);
            region[i] = temp;
        }
        // 以左端点为标准进行升序
        Arrays.sort(region, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        // 初始化答案，当前可用最右位置
        int res = 0, right = 0;
        // 初始化当前区间
        int cur = 0;
        // 遍历所有区间
        while(cur < n + 1) {
            // 当前区间无法覆盖到最右的有效工作范围，那么就会存在覆盖不到的间隙
            if(region[cur][0] > right) break;
            // 遍历可以覆盖到已经可用的最右点的下一个可用的最右边点
            int rt = right;
            while(cur < n + 1 && region[cur][0] <= right) {
                rt = Math.max(rt, region[cur][1]);
                cur ++;
            }
            res ++;
            right = rt;
            // 已经遍历到整个范围
            if(right == n) break;
        }

        return right == n ? res : -1;
    }

    /**
     * 作者：lippon
     *         链接：https://leetcode.cn/problems/minimum-number-of-taps-to-open-to-water-a-garden/solution/java-tong-yong-de-yi-wei-qu-jian-jiao-bi-qfn0/
     *         来源：力扣（LeetCode）
     *         著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
}


