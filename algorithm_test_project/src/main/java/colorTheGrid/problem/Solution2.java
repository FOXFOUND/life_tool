package colorTheGrid.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution2 {
    public int colorTheGrid(int m, int n) {
        final int MOD = 1_000_000_007;
        // key 为有效的 mask 值，value 为 mask 值对应的 3 进制数
        Map<Integer, int[]> valid = new HashMap<>();
        int maskEnd = (int) Math.pow(3, m);

        // 初始化 valid
        for (int mask = 0; mask < maskEnd; ++mask) {
            int[] color = new int[m];
            int mm = mask;
            // 分析三进制值，如果相邻两个值相同，则不符合要求，舍弃
            boolean check = true;
            for (int i = 0; i < m; ++i) {
                color[i] = mm % 3;
                if (i > 0 && color[i - 1] == color[i]) {
                    check = false;
                    break;
                }
                mm /= 3;
            }
            if (check) valid.put(mask, color);
        }

        // 预处理，分析所有可能相邻的二元组，将该信息放置在 map 中
        // key 值为 mask 值，value 为可与该 mask 相邻的 mask 值列表
        Map<Integer, List<Integer>> adjacent = new HashMap<>();
        for (Integer mask1 : valid.keySet()) {
            List<Integer> list = new ArrayList<>();
            for (Integer mask2 : valid.keySet()) {
                boolean check = true;
                for (int i = 0; i < m; ++i) {
                    if (valid.get(mask1)[i] == valid.get(mask2)[i]) {
                        check = false;
                        break;
                    }
                }
                if (check) list.add(mask2);
            }
            adjacent.put(mask1, list);
        }

        // 由于 dp[i][mask] 仅与 dp[i-1][mask'] 有关，因此，可以使用一维数组实现 dp
        int[] dp = new int[maskEnd];

        // 边界条件：dp[i][mask], i == 0 时
        for (Integer mask : valid.keySet()) {
            dp[mask] = 1;
        }

        // 动态规划
        for (int i = 1; i < n; ++i) {
            int[] tmpDp = new int[maskEnd];
            for (Integer mask : valid.keySet()) {
                for (Integer index : adjacent.get(mask)) {
                    tmpDp[mask] += dp[index];
                    tmpDp[mask] %= MOD;
                }
            }
            dp = tmpDp;
        }

        int ans = 0;
        for (Integer mask : valid.keySet()) {
            ans += dp[mask];
            ans %= MOD;
        }
        return ans;
    }
}
