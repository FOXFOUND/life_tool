package hIndex.problem;

import java.util.Arrays;

public class Solution4 {
    public int hIndex(int[] citations) {

        Arrays.sort(citations);

        int n = citations.length;
        int left = 0, right = n - 1;
        int res = 0;
        boolean findFlag = false;
        while (left < right) {
            int middle = left + (right - left) / 2;

            if (citations[middle] == n - middle) {
                res = citations[middle];
                left = middle + 1;
                findFlag = true;

            } else if (citations[middle] < n - middle + 1) {
                left = middle + 1;
            } else {
                right = middle;
            }

        }

        if (!findFlag) {

            for (int i = 0; i < citations.length; i++) {
                if (citations[i] != 0) {
                    res++;
                }
            }
            return res;
        } else {
            return res;
        }


    }
}
