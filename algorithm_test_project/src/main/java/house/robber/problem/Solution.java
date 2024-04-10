package house.robber.problem;


import java.util.Arrays;

public class Solution {
    /**
     * 你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。
     *
     * 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/house-robber-ii
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */

    /**
     * 题目分析:
     * 1.每个房子都有偷和不偷两种状态
     * 2.如果第i个房子,如果偷的话,那么i-1,i+1的房子就不能偷,如果不偷则和i-1的最大值一致
     * dp[i] 代表到达第i个房子,可以取得的最大值
     * dp[i] = max(dp[i-1],dp[i-2]+num[i]) , 并且 i-1
     *
     * @param nums
     * @return
     */

//    public int rob(int[] nums) {
//        int[] dp = new int[nums.length];
//        int[] robFlag = new int[nums.length];
//        int left = 0;
//        dp[0] = nums[0];
//        for (int i = 1; i < nums.length; i++) {
//            if (i - 2 < 0) {
//                left = nums.length + (i - 2);
//            } else {
//                left = i - 2;
//            }
//            if (dp[left] + nums[i] > dp[i - 1]) {
//                robFlag[left] = 1;
//                robFlag[i] = 1;
//                dp[i] = dp[left] + nums[i];
//            } else {
//                robFlag[i] = 0;
//                dp[i] = dp[i - 1];
//            }
//
//        }
//        return dp[nums.length - 1];
//    }
    public int rob(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        return Math.max(myRob(Arrays.copyOfRange(nums, 0, nums.length - 1)),
                myRob(Arrays.copyOfRange(nums, 1, nums.length)));
    }

    private int myRob(int[] nums) {
        int preTwo = 0, preOne = 0,max = 0;
        for (int num : nums) {
            max = Math.max(preTwo + num, preOne);
            preTwo = preOne;
            preOne = max;
        }
        return max;
    }
/**
 * 作者：jyd
 * 链接：https://leetcode.cn/problems/house-robber-ii/solution/213-da-jia-jie-she-iidong-tai-gui-hua-jie-gou-hua-/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 *
 */
}


