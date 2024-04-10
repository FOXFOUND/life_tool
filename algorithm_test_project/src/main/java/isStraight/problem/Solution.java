package isStraight.problem;

import java.util.Arrays;
import java.util.Map;

public class Solution {
    public boolean isStraight(int[] nums) {
        int zeroNum = 0;
        int subSum = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                zeroNum++;
                continue;
            }
            if (i + 1 < nums.length) {

                while (nums[i + 1] - nums[i] != 1) {
                    if (zeroNum > 0) {
                        zeroNum--;
                        nums[i]++;
                        if (nums[i] > nums[i + 1]) {
                            int temp = nums[i];
                            nums[i] = nums[i + 1];
                            nums[i + 1] = temp;
                        }
                    } else {
                        return false;
                    }
                }

            }
        }
        return true;

    }
}
