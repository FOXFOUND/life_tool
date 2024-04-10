package champagneTower.problem;


/**
 * 我们把玻璃杯摆成金字塔的形状，其中 第一层 有 1 个玻璃杯， 第二层 有 2 个，依次类推到第 100 层，每个玻璃杯 (250ml) 将盛有香槟。
 */
public class Solution {
    public double champagneTower(int poured, int query_row, int query_glass) {

        double result = 0d;
        int queryRowNeed = (1 + query_row) * (query_row) / 2;
        int nNeed = queryRowNeed - poured;
        int queryRowNeedLast = (query_row) * (query_row - 1) / 2;

        if (nNeed <= 0) {
            result = (double) query_glass / poured;
        } else {
            int nNeedLast = queryRowNeedLast - poured;
            if (nNeedLast >= 0) {
                result = 0d;
            } else {
                result = ((double) (poured - queryRowNeedLast) / query_row) * query_glass;
            }

        }
        return result;


    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        double res = solution.champagneTower(100000009, 33, 17);
        System.out.println(res);
    }
}
