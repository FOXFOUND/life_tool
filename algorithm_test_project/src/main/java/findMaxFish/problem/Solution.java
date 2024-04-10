package findMaxFish.problem;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * 给你一个下标从 0 开始大小为 m x n 的二维整数数组 grid ，其中下标在 (r, c) 处的整数表示：
 * <p>
 * 如果 grid[r][c] = 0 ，那么它是一块 陆地 。
 * 如果 grid[r][c] > 0 ，那么它是一块 水域 ，且包含 grid[r][c] 条鱼。
 * 一位渔夫可以从任意 水域 格子 (r, c) 出发，然后执行以下操作任意次：
 * <p>
 * 捕捞格子 (r, c) 处所有的鱼，或者
 * 移动到相邻的 水域 格子。
 * 请你返回渔夫最优策略下， 最多 可以捕捞多少条鱼。如果没有水域格子，请你返回 0 。
 * <p>
 * 格子 (r, c) 相邻 的格子为 (r, c + 1) ，(r, c - 1) ，(r + 1, c) 和 (r - 1, c) ，前提是相邻格子在网格图内。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/maximum-number-of-fish-in-a-grid
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int findMaxFish(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;
        //访问数组
        int[][] visitFlag = new int[m][n];

        int maxFish = 0;
        Queue<String> findQueue = new ArrayDeque<>();

        String split = "_";
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int tempMaxFish = 0;
                if (grid[i][j] > 0 && visitFlag[i][j] == 0) {
                    findQueue.add(i + split + j);
                }
                while (!findQueue.isEmpty()) {
                    String key = findQueue.poll();
                    String[] position = key.split("_");
                    int x = Integer.parseInt(position[0]);
                    int y = Integer.parseInt(position[1]);

                    if (visitFlag[x][y] == 1) {
                        continue;
                    }

                    visitFlag[x][y] = 1;
                    tempMaxFish += grid[x][y];
                    //左
                    if (x - 1 >= 0 && grid[x - 1][y] > 0 && visitFlag[x - 1][y] == 0) {
                        findQueue.add((x - 1) + split + y);
                    }
                    //右
                    if (x + 1 < m && grid[x + 1][y] > 0 && visitFlag[x + 1][y] == 0) {
                        findQueue.add((x + 1) + split + y);
                    }
                    //下
                    if (y - 1 >= 0 && grid[x][y - 1] > 0 && visitFlag[x][y - 1] == 0) {
                        findQueue.add((x) + split + (y - 1));
                    }
                    //上
                    if (y + 1 < n && grid[x][y + 1] > 0 && visitFlag[x][y + 1] == 0) {
                        findQueue.add((x) + split + (y + 1));
                    }
                }
                maxFish = tempMaxFish > maxFish ? tempMaxFish : maxFish;

            }


        }


        return maxFish;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        //int [][] grids = new int[][]{{0,2,1,0},{4,0,0,3},{1,0,0,4},{0,3,2,0}};
        int[][] grids = new int[][]{{8, 6}, {2, 6}};
        int res = solution.findMaxFish(grids);
        System.out.println(res);
    }
}
