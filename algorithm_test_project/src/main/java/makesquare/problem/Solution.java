package makesquare.problem;

import java.util.Arrays;

/**
 * 你将得到一个整数数组 matchsticks ，其中 matchsticks[i] 是第 i 个火柴棒的长度。你要用 所有的火柴棍 拼成一个正方形。你 不能折断 任何一根火柴棒，但你可以把它们连在一起，而且每根火柴棒必须 使用一次 。
 * <p>
 * 如果你能使这个正方形，则返回 true ，否则返回 false 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/matchsticks-to-square
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public boolean makesquare(int[] matchsticks) {

        Arrays.sort(matchsticks);
        int num = 0;
        for (int i = 0; i < matchsticks.length; i++) {
            num += matchsticks[i];
        }
        if (num % 4 != 0) {
            return false;
        }

        int sizeLength = matchsticks[matchsticks.length - 1];
        int leftPoint = 0, rightPoint = matchsticks.length - 1;
        int sizeNum = 0;
        int sum = matchsticks[leftPoint] + matchsticks[rightPoint];
        while (leftPoint <= rightPoint) {
            //状态机
            if (sum == sizeLength) {
                sizeNum++;
                rightPoint--;
                leftPoint++;
                sum = matchsticks[leftPoint] + matchsticks[rightPoint];
            }
            if (matchsticks[rightPoint] == sizeLength) {
                sizeNum++;
                rightPoint--;
                sum = matchsticks[leftPoint] + matchsticks[rightPoint];
            } else {
                leftPoint++;
                sum += matchsticks[leftPoint] ;
            }
        }

        if (sizeNum != 4) {
            return false;
        }
        return true;


    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        //int[] matchsticks = {1, 1, 2, 2, 2};
        //int [] matchsticks = {3,3,3,3,4};
        int[] matchsticks = {10, 6, 5, 5, 5, 3, 3, 3, 2, 2, 2, 2};
        boolean res = solution.makesquare(matchsticks);
        System.out.println(res);
    }
}
