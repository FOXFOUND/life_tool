package canCross.problem;

/**
 * 一只青蛙想要过河。 假定河流被等分为若干个单元格，并且在每一个单元格内都有可能放有一块石子（也有可能没有）。 青蛙可以跳上石子，但是不可以跳入水中。
 * <p>
 * 给你石子的位置列表 stones（用单元格序号 升序 表示）， 请判定青蛙能否成功过河（即能否在最后一步跳至最后一块石子上）。开始时， 青蛙默认已站在第一块石子上，并可以假定它第一步只能跳跃 1 个单位（即只能从单元格 1 跳至单元格 2 ）。
 * <p>
 * 如果青蛙上一步跳跃了 k 个单位，那么它接下来的跳跃距离只能选择为 k - 1、k 或 k + 1 个单位。 另请注意，青蛙只能向前方（终点的方向）跳跃。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/frog-jump
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {

    /**
     * 采用前缀和+窗口会使得陷入局部最优解中,倒是死循环,题目的要求是全局最优解
     *
     * @param stones
     * @return
     */
    public boolean canCross(int[] stones) {

        int right = 1;
        int pre = 0;
        int i = 0;
        while (true) {
            if (stones[i] > right) {
                return false;
            }
            if (right >= stones[stones.length - 1]) {
                return true;
            }

            pre = i;
            //找到在窗口中最右边的元素
            while (stones[i] <= right) {
                i++;
            }
            i = i - 1;
            int step = stones[i] - stones[pre];
            right = stones[i] + step + 1;

        }

    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] stones = new int[]{0, 1, 3, 5, 6, 8, 12, 17};  //true
        //int[] stones = new int[]{0, 1, 2, 3, 4, 8, 9, 11};  // false
        boolean res = solution.canCross(stones);
        System.out.println(res);
    }
}
