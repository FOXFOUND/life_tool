package search.problem;

public class Solution {
    public int search(int[] nums, int target) {

        if (nums.length == 0) {
            return 0;
        }

        int left = 0, right = nums.length - 1;
        while (left < right) {
            int middle = left + (right - left) / 2;
            if (nums[middle] > target) {
                right = middle;
            }
            if (nums[middle] < target) {
                left = middle + 1;
            }
            if (nums[middle] == target) {
                left = middle;
                break;
            }
        }
        int res = 0;
        while (left < nums.length) {
            if (nums[left] == target) {
                res++;
            }
            left++;
        }
        return res;
    }
}
