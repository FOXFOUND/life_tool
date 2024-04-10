package subarrayLCM.problem;

/**
 * 给你一个整数数组 nums 和一个整数 k ，请你统计并返回 nums 的 子数组 中满足 元素最小公倍数为 k 的子数组数目。
 * <p>
 * 子数组 是数组中一个连续非空的元素序列。
 * <p>
 * 数组的最小公倍数 是可被所有数组元素整除的最小正整数
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/number-of-subarrays-with-lcm-equal-to-k
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int subarrayLCM(int[] nums, int k) {


        int position = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == k) {
                position = i;
                break;
            }
        }

        if (position == -1) {
            return 0;
        }

        int left = position, right = position;
        while (k % nums[left] == 0 && (left - 1) >= 0 && k % nums[left - 1] == 0) {
            left--;
        }
        while (k % nums[right] == 0 && (right + 1) <= nums.length - 1 && k % nums[right + 1] == 0) {

            right++;
        }

        int leftPoint = position - left, rightPoint = right - position;
        int leftSum = 0, rightSum = 0;
        for (int i = 1; i <= leftPoint; i++) {
            leftSum += 1;
        }

        for (int i = 1; i <= rightPoint; i++) {
            rightSum += 1;
        }
        return leftSum + rightSum + leftSum * rightSum + 1;

    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        //int[] nums = new int[]{3, 6, 2, 7, 1};  //4
        //int k = 6;
        int[] nums = new int[]{5,1,1,1,2};  //6
        int k = 1;
        int res = solution.subarrayLCM(nums, k);
        System.out.println(res);
    }
}
