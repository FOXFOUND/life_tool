package productExceptSelf.problem;

import java.util.Arrays;

public class Solution {
    public int[] productExceptSelf(int[] nums) {

        int[][] quare = new int[nums.length][nums.length];
        for (int i = 0; i < quare.length; i++) {
            Arrays.fill(quare[i], nums[i]);
        }

        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int temp = 1;
            for (int j = 0; j < nums.length; j++) {
                if (i == j) {
                    continue;
                }
                temp *= quare[j][i];
            }
            res[i] = temp;

        }
        return res;
    }
}
