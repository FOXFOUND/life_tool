package findMaxAverage.problem;

public class Solution {
    public double findMaxAverage(int[] nums, int k) {

        int sum = 0;
        int n = nums.length;
        double max = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        max = sum;
        for (int i = k; i < n; i++) {
            sum = sum + nums[i] - nums[i -k];
            max = Math.max(max,sum);
        }
        return max/k;
    }
}
