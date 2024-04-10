package makesquare.problem;

public class Solution2 {
    public boolean makesquare(int[] matchsticks) {

        int[] numTimes = new int[16];
        int num = 0;
        for (int i = 0; i < matchsticks.length; i++) {
            numTimes[matchsticks[i]]++;
            num += matchsticks[i];
        }
        if (num % 4 != 0) {
            return false;
        }

        int sideLength = num / 4;
        for (int j = 0; j < 4; j++) {
            int temp = sideLength;
            for (int i = numTimes.length - 1; i >= 1; i--) {
                if (temp == 0) {
                    break;
                }
                if (numTimes[i] > 0 && i <= temp) {
                    temp -= i ;
                    numTimes[i]--;
                    //回退
                    i++;
                }
            }
        }

        for (int i = 0; i < numTimes.length; i++) {
            if (numTimes[i] != 0) {
                return false;
            }
        }
        return true;


    }

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        //int[] matchsticks = {1, 1, 2, 2, 2}; // true
        //int [] matchsticks = {3,3,3,3,4};   // false
        int[] matchsticks = {10, 6, 5, 5, 5, 3, 3, 3, 2, 2, 2, 2}; //true
        boolean res = solution2.makesquare(matchsticks);
        System.out.println(res);
    }
}
