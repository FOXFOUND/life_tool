package singleNonDuplicate.problem;

public class Solution {
    public int singleNonDuplicate(int[] nums) {

        int n = nums.length;

        int left = 0, right = n - 1;

        while (left < right) {

            int middle = left + (right - left) / 2;

            if ((right - middle) % 2 == 0) {
                right = middle;
            } else {
                left = middle + 1;
            }

        }
        return nums[left];
    }
}
