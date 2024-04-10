package twoSum.problem;

public class Solution3 {

    //无法兼容
    public int[] twoSum(int[] nums, int target) {

        int left = 0, right = nums.length - 1;


        if (nums[left] + nums[right] < target) {
            return null;
        }

        while (left < right && left >= 0 && right < nums.length) {

            while (nums[left] + nums[right] > target) {
                right--;
            }

            while (nums[left] + nums[right] < target) {
                right++;
            }

            if (nums[left] + nums[right] == target) {
                return new int[]{nums[left], nums[right]};
            }

            left++;

        }
        return null;
    }
}
