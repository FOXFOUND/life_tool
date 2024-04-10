package subarraySum.problem;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int [] nums = new int[] {28,54,7,-70,22,65,-6};
        int k = 100;
        solution.subarraySum(nums,k);
    }


    public int subarraySum(int[] nums, int k) {

        int n = nums.length;
        int left = 0, right = 0;

        int sum = 0;
        int res = 0;
        while (right < n) {
            sum += nums[right];

            if (sum == k) {
                res++;
            }

            while (sum > k && left < right) {
                sum -= nums[left];
                if (sum == k) {
                    res++;
                }
                left++;

            }

            right++;

        }

        while (left < n - 1) {

            sum -= nums[left];
            if (sum == k) {
                res++;
            }
            left++;
        }

        return res;
    }
}
