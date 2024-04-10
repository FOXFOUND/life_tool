package isStraight.problem;

import java.util.Arrays;

public class Solution2 {
    public boolean isStraight(int[] nums) {

        boolean[] check = new boolean[15];
        int numOfZero = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                numOfZero++;
                continue;
            }
            check[nums[i]] = true;
        }

        int left = 0, right = check.length - 1;
        while (left < check.length - 1) {
            if (check[left]) {
                break;
            }
            left++;
        }

        while (right >= 0) {
            if (check[right]) {
                break;
            }
            right--;
        }


        if (right - left > 5) {
            return false;
        }

        int checkNum = 0;
        for (int i = left; i <= right; i++) {
            if (check[i]) {
                checkNum++;

            } else {
                if (numOfZero > 0) {
                    checkNum++;
                    numOfZero--;
                }
            }
        }
        return checkNum == 5;


    }
}
