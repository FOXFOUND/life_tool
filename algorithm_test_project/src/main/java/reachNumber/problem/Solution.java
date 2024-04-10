package reachNumber.problem;

/**
 * 在一根无限长的数轴上，你站在0的位置。终点在target的位置。
 * <p>
 * 你可以做一些数量的移动 numMoves :
 * <p>
 * 每次你可以选择向左或向右移动。
 * 第 i 次移动（从  i == 1 开始，到 i == numMoves ），在选择的方向上走 i 步。
 * 给定整数 target ，返回 到达目标所需的 最小 移动次数(即最小 numMoves ) 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/reach-a-number
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int reachNumber(int target) {
        //BFS 广度优先搜索
        int step = 2;
        int minStep = 2;
        int[] direct = new int[4];
        //向左走,左
        direct[0] = -1;
        //向左走,右
        direct[1] = -1;
        //向右走,左
        direct[2] = 1;
        //向右走,右
        direct[3] = 1;
        if (direct[0] == target
                || direct[1] == target
                || direct[2] == target
                || direct[3] == target) {
            return 1;
        }
        while (true) {
            direct[0] = direct[0] - step;
            direct[1] = direct[1] + step;
            direct[2] = direct[2] - step;
            direct[3] = direct[3] + step;
            if (direct[0] == target
                    || direct[1] == target
                    || direct[2] == target
                    || direct[3] == target) {
                break;
            }
            minStep++;
            step++;
        }

        return minStep;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int res = solution.reachNumber(2);
        System.out.println(res);
    }


}
