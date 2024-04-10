package countBits.problem;

public class Solution {
    public int[] countBits(int n) {

        int[] res = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            int num = 0;
            int temp = i;
            while (temp != 0  ) {
                if((temp & 1) == 1){

                    num++;
                }
                temp = temp >> 1;
            }
            res[i] = num;
        }
        return res;
    }
}
