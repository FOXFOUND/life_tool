package hIndex.problem;

public class Solution2 {
    public int hIndex(int[] citations) {
        if (citations.length == 1) {
            if (citations[0] > 0) {
                return 1;
            } else {
                return 0;
            }
        }
        int res = 0;
        for (int i = 0; i < citations.length; i++) {
            if (citations[i] == citations.length - i) {
                return citations[i];
            }
            if (res == 0 && citations[i] > 0) {
                res = 1;
            }
        }
        return res;
    }
}
