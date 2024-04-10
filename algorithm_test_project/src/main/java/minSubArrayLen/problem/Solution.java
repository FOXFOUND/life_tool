package minSubArrayLen.problem;

public class Solution {
    public int minSubArrayLen(int target, int[] nums) {


        int n = nums.length;

        int left = 0, right = 0;
        int sum = 0;
        int minLength = 0;
        while (right < n) {

            sum += nums[right];

            while (sum >= target) {
                if (minLength == 0) {
                    minLength = right - left + 1;
                } else {

                    minLength = Math.min(minLength, right - left + 1);
                }
                sum -= nums[left];
                left++;
            }

            right++;
        }

        return minLength;
    }
}
