package guessNumber.problem;

import java.util.Random;

public class Solution extends GuessGame {

    /**
     * -1：我选出的数字比你猜的数字小 pick < num
     * 1：我选出的数字比你猜的数字大 pick > num
     * 0：我选出的数字和你猜的数字一样。恭喜！你猜对了！pick == num
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/guess-number-higher-or-lower
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param n
     * @return
     */
    public int guessNumber(int n) {

        /**
         * 问题 : 需要左右收敛,而不是向着集合的一边收敛
         */
        int num = (1 + n) / 2;
        while (true) {
            int res = guess(num);
            switch (res) {
                case -1:
                    num = (1 + num ) / 2;
                    break;
                case 1:
                    num = (num + 1 + n) / 2;
                    break;
                case 0:
                    return num;
                default:
                    break;
            }
        }

    }
}
