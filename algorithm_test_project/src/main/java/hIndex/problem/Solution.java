package hIndex.problem;

public class Solution {


    /**
     * 给你一个整数数组 citations ，其中 citations[i] 表示研究者的第 i 篇论文被引用的次数，citations 已经按照 升序排列 。计算并返回该研究者的 h 指数。
     * <p>
     * h 指数的定义：h 代表“高引用次数”（high citations），一名科研人员的 h 指数是指他（她）的 （n 篇论文中）总共有 h 篇论文分别被引用了至少 h 次。且其余的 n - h 篇论文每篇被引用次数 不超过 h 次。
     * <p>
     * 提示：如果 h 有多种可能的值，h 指数 是其中最大的那个。
     * <p>
     * 请你设计并实现对数时间复杂度的算法解决此问题。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/h-index-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param citations
     * @return
     */

    public int hIndex(int[] citations) {

        if (citations.length == 1 && citations[0] > 0) {
            return 1;
        }

        int res = 0;

        for (int i = 0; i < citations.length; i++) {

            if (citations[i] == (citations.length - i)) {
                res = citations[i];
                continue;
            }
            if (res != 0) {
                break;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] citations = {0, 1, 3, 5, 6};
        Solution solution = new Solution();
        int res = solution.hIndex(citations);
        System.out.println(res);
    }
}
