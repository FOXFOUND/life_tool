package orangesRotting.problem;

/**
 * 在给定的 m x n 网格 grid 中，每个单元格可以有以下三个值之一：
 * <p>
 * 值 0 代表空单元格；
 * 值 1 代表新鲜橘子；
 * 值 2 代表腐烂的橘子。
 * 每分钟，腐烂的橘子 周围 4 个方向上相邻 的新鲜橘子都会腐烂。
 * <p>
 * 返回 直到单元格中没有新鲜橘子为止所必须经过的最小分钟数。如果不可能，返回 -1 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/rotting-oranges
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {

    public int orangesRotting(int[][] grid) {

        boolean rottingFlag = true;
        int res = -1;
        while (true) {

            if (!rottingFlag) {
                break;
            }
            rottingFlag = false;

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[0].length; j++) {

                    if (grid[i][j] == 2) {
                        //左
                        if ((j - 1) >= 0 && grid[i][j - 1] == 1) {
                            grid[i][j - 1] = 2;
                            rottingFlag = true;
                        }
                        //右
                        if ((j + 1) < grid[0].length && grid[i][j + 1] == 1) {
                            grid[i][j + 1] = 2;
                            rottingFlag = true;
                        }
                        //上
                        if ((i - 1) >= 0 && grid[i - 1][j] == 1) {
                            grid[i - 1][j] = 2;
                            rottingFlag = true;
                        }
                        //下
                        if ((i + 1) < grid.length && grid[i + 1][j] == 1) {
                            grid[i + 1][j] = 2;
                            rottingFlag = true;
                        }


                    }


                }
            }

            if (rottingFlag) {
                res++;
            }
        }

        return res;
    }
}
