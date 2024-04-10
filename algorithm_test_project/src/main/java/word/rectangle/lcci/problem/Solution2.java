package word.rectangle.lcci.problem;

import java.util.Arrays;

public class Solution2 {
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] numsCopy = Arrays.copyOf(nums, nums.length);
        Arrays.sort(numsCopy);
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int resCount = 0;
            for (int j = 0; j < numsCopy.length; j++) {
                if (numsCopy[j] < nums[i]) {
                    resCount++;
                }
            }
            res[i] = resCount;
        }
        return  res;
    }
}
