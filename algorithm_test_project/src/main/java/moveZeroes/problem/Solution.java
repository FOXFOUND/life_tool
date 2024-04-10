package moveZeroes.problem;

public class Solution {
    public void moveZeroes(int[] nums) {

        int n = nums.length;

        for (int i = 0; i < n; i++) {

            if (nums[i] != 0) {
                continue;
            }

            int current = i;
            while (current < n) {
                if (nums[current] != 0) {
                    break;
                }
                current++;

            }

            if(current < n) {
                nums[i] = nums[current];
                nums[current] = 0;
            }
        }


    }
}
