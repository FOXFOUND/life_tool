package twoSum.problem;

import java.util.Arrays;
import java.util.HashMap;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 * <p>
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * <p>
 * 你可以按任意顺序返回答案。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/two-sum
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> position = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            position.put(nums[i], i);
        }
        Arrays.sort(nums);
        int left = 0, right = nums.length - 1;

        while (left < right) {
            if (nums[left] + nums[right] == target) {
                int[] res = new int[2];
                res[0] = position.get(nums[left]);
                res[1] = position.get(nums[right]);
                return res;
            }
            if (nums[left] + nums[right] > target) {
                right--;
                continue;
            }
            left++;

        }

        return null;
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
    }
}
