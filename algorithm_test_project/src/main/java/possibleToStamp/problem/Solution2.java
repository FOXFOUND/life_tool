package possibleToStamp.problem;

class Solution2 {
    public boolean possibleToStamp(int[][] grid, int h, int w) {
        int m = grid.length, n = grid[0].length;
        // 计算 grid 矩阵的二维前缀和（偏移 1 位），以快速判断某一位置是否可以放邮票
        int[][] sum = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum[i + 1][j + 1] = sum[i + 1][j] + sum[i][j + 1] - sum[i][j] + grid[i][j];
            }
        }
        // 枚举邮票的右下角坐标（i, j 已经偏移 1 位），如果当前位置允许放邮票，则利用二维差分数组存储邮票信息
        int[][] diff = new int[m + 2][n + 2];
        for (int i = h; i <= m; i++) {
            for (int j = w; j <= n; j++) {
                int x = i - h + 1, y = j - w + 1;
                // 如果矩阵 (x - 1,y - 1) -> (i - 1,j - 1) 的前缀和为 0，则说明该位置可以放邮票
                if (sum[i][j] - sum[x - 1][j] - sum[i][y - 1] + sum[x - 1][y - 1] == 0) {
                    diff[x][y]++;
                    diff[i + 1][y]--;
                    diff[x][j + 1]--;
                    diff[i + 1][j + 1]++;
                }
            }
        }
        // 计算二维差分数组的前缀和，得到当前位置是否放邮票的信息
        // 如果当前位置为空且没有放邮票，则返回 false
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                diff[i + 1][j + 1] += diff[i + 1][j] + diff[i][j + 1] - diff[i][j];
                if (grid[i][j] == 0 && diff[i + 1][j + 1] == 0) return false;
            }
        }
        return true;
    }
}

//作者：killer-cs
//        链接：https://leetcode.cn/problems/stamping-the-grid/solution/by-killer-cs-2lp8/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
