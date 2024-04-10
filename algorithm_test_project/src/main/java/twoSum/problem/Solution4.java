package twoSum.problem;

public class Solution4 {

    public static void main(String[] args) {
        Solution4 solution4 = new Solution4();
        int[] nums = new int[]{16, 16, 18, 24, 30, 32};
        int target = 48;
        solution4.twoSum(nums, target);
    }

    public int[] twoSum(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {

            int left = nums[i];
            int right = target - left;
            if (exist(right, nums)) {
                return new int[]{left, right};
            }

        }
        return null;
    }

    private boolean exist(int value, int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int pivot = left + (right - left) /2 ;
            if (nums[pivot] == value) {
                return true;
            }

            if (nums[pivot] > value) {
                right = pivot;
            } else {
                left = pivot + 1;
            }
        }

        return false;
    }
}
