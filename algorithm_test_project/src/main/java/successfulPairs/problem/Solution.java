package successfulPairs.problem;

import java.util.Arrays;

public class Solution {
    public int[] successfulPairs(int[] spells, int[] potions, long success) {

        int n = spells.length;
        int m = potions.length;
        int[] res = new int[n];
        Arrays.sort(potions);

        long[] arr = new long[potions.length];//必须要转发为long类型，不然超范围
        for (int i = 0; i < arr.length; i++) {
            arr[i] = potions[i];
        }

        for (int i = 0; i < n; i++) {

            int left = 0, right = m;
            while (left < right) {
                int middle = left + (right - left) / 2;
                if (spells[i] * arr[middle] >= success) {
                    right = middle;
                } else {
                    left = middle + 1;
                }
            }
            res[i] = m - right;
        }

        return res;

    }
}
