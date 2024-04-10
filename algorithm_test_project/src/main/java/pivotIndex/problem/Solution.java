package pivotIndex.problem;

import com.alibaba.fastjson.JSON;

public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = new int[]{1, 7, 3, 6, 5, 6};
        solution.pivotIndex(nums);
    }

    public int pivotIndex(int[] nums) {

        int[] arrSum = new int[nums.length];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            arrSum[i] = sum;
        }


        if (arrSum[0] == sum) {
            return 0;
        }

        for (int i = 1; i < arrSum.length; i++) {
            int temp = 2 * arrSum[i - 1] + nums[i];
            if (temp == sum) {
                return i;
            }
        }
        return -1;
    }
}
