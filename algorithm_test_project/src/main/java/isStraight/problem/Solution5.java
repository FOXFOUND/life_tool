package isStraight.problem;

import java.util.Arrays;

class Solution5 {
    public boolean isStraight(int[] nums) {
        Arrays.sort(nums);
        int count0 = 0;
        int need0 = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == 0) {
                count0++;
                continue;
            }
            if (nums[i + 1] == nums[i]) {
                return false;
            }
            if (nums[i + 1] - nums[i] >= 1) {
                need0 = need0 + (nums[i + 1] - nums[i] - 1);
            }
        }
        return need0 <= count0;
    }
}