package longestConsecutive.problem;

import java.util.Arrays;

public class Solution {
    public int longestConsecutive(int[] nums) {

        if(nums == null || nums.length == 0){
            return 0;
        }

        Arrays.sort(nums);

        int temp = 0;
        int max = 1;
        int current = 0;
        while (current < nums.length - 1) {

            if (current + 1 < nums.length && nums[current] == nums[current + 1]) {
                current++;
                continue;
            }

            if (current + 1 < nums.length && nums[current] > nums[current + 1]) {
                current++;
                temp = 1;
                continue;
            }


            if (current + 1 < nums.length && nums[current + 1] - nums[current] > 1) {
                current++;
                temp = 1;
                continue;
            }


            temp++;
            current++;
            max = Math.max(max, temp);

        }

        return max;
    }
}
