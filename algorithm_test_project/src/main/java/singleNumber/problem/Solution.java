package singleNumber.problem;

import java.util.Arrays;

public class Solution {
    public int singleNumber(int[] nums) {

        Arrays.sort(nums);
        int res = 0;
        int n = nums.length;
        boolean flag = true;
        for (int i = 0; i < n; i++) {
            if (flag) {
                res += nums[i];
                flag = false;
            } else {
                res -= nums[i];
                flag = true;
            }
        }
        return res;
    }
}
