package singleNumbers.problem;

import java.util.Arrays;

public class Solution {
    public int[] singleNumbers(int[] nums) {

        Arrays.sort(nums);

        int resA = 0;
        int n = nums.length;


        for (int i = 0; i < n; i++) {
            resA ^= nums[i];
        }

        for (int i = n - 1; i > 0; i--) {
            resA ^= nums[i];
            if (resA == nums[i - 1]) {
                if ((i + 1 < n && nums[i] == nums[i + 1])) {
                    break;
                }
            }
        }

        int resB = 0;
        for (int i = 0; i < n; i++) {
            resB ^= nums[i];
        }
        resB ^= resA;
        return new int[]{resA, resB};

    }
}
