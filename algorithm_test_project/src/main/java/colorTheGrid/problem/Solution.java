package colorTheGrid.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给你两个整数 m 和 n 。构造一个 m x n 的网格，其中每个单元格最开始是白色。请你用 红、绿、蓝 三种颜色为每个单元格涂色。所有单元格都需要被涂色。
 * <p>
 * 涂色方案需要满足：不存在相邻两个单元格颜色相同的情况 。返回网格涂色的方法数。因为答案可能非常大， 返回 对 109 + 7 取余 的结果。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/painting-a-grid-with-three-different-colors
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class Solution {
    static final int MODULO = 1000000007;

    public int colorTheGrid(int m, int n) {
        int totalStates = 1;
        for (int i = 1; i <= m; i++) {
            totalStates *= 3;
        }
        List<Integer> states = new ArrayList<Integer>();
        for (int i = 0; i < totalStates; i++) {
            if (isValid(i, m)) {
                states.add(i);
            }
        }
        int validStates = states.size();
        List<Integer>[] adjacentArr = new List[validStates];
        for (int i = 0; i < validStates; i++) {
            adjacentArr[i] = new ArrayList<Integer>();
        }
        for (int i = 0; i < validStates; i++) {
            int state1 = states.get(i);
            for (int j = i + 1; j < validStates; j++) {
                int state2 = states.get(j);
                if (canAdjacent(state1, state2, m)) {
                    adjacentArr[i].add(j);
                    adjacentArr[j].add(i);
                }
            }
        }
        long[][] dp = new long[n][validStates];
        Arrays.fill(dp[0], 1);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < validStates; j++) {
                List<Integer> adjacent = adjacentArr[j];
                for (int k : adjacent) {
                    dp[i][j] += dp[i - 1][k];
                }
                dp[i][j] %= MODULO;
            }
        }
        return (int) (Arrays.stream(dp[n - 1]).sum() % MODULO);
    }

    public boolean isValid(int state, int m) {
        int prev = -1;
        for (int i = 0; i < m; i++) {
            int curr = state % 3;
            if (curr == prev) {
                return false;
            }
            prev = curr;
            state /= 3;
        }
        return true;
    }

    public boolean canAdjacent(int state1, int state2, int m) {
        for (int i = 0; i < m; i++) {
            int color1 = state1 % 3, color2 = state2 % 3;
            if (color1 == color2) {
                return false;
            }
            state1 /= 3;
            state2 /= 3;
        }
        return true;
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        int res = solution.colorTheGrid(5,5);
        System.out.println(res);
    }
}

//作者：stormsunshine
//        链接：https://leetcode.cn/problems/painting-a-grid-with-three-different-colors/solution/1931-yong-san-chong-bu-tong-yan-se-wei-w-842g/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。