package checkStraightLine.problem;

import java.math.BigDecimal;

/**
 * 给定一个数组 coordinates ，其中 coordinates[i] = [x, y] ， [x, y] 表示横坐标为 x、纵坐标为 y 的点。请你来判断，这些点是否在该坐标系中属于同一条直线上。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/check-if-it-is-a-straight-line
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {

    public boolean checkStraightLine(int[][] coordinates) {

        for (int i = 1; i < coordinates.length - 1; i++) {
            int[] pre = coordinates[i - 1];
            int[] current = coordinates[i];
            int[] next = coordinates[i + 1];
            double k1 = (double) (current[1] - pre[1]) / (current[0] - pre[0]);
            BigDecimal one = new BigDecimal(k1);
            double k1Touch = one.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            double k2 = (double) (next[1] - current[1]) / (next[0] - current[0]);
            BigDecimal two = new BigDecimal(k2);
            double k2Touch = two.doubleValue();

            if (Double.compare(k1Touch, k2Touch) != 0) {
                return false;
            }

        }

        return true;
    }
}
