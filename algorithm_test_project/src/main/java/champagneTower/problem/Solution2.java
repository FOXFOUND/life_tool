package champagneTower.problem;

class Solution2 {
    public double champagneTower(int k, int n, int m) {
        //double[][] f = new double[n + 2][n + 2];

        double[][] f = new double[n + 10][n + 10];
        f[0][0] = k;
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= i; j++) {
                if (f[i][j] <= 1) continue;
                f[i + 1][j] += (f[i][j] - 1) / 2;
                f[i + 1][j + 1] += (f[i][j] - 1) / 2;
            }
        }
        return Math.min(f[n][m], 1);
    }


    /**
     * 作者：AC_OIer
     *         链接：https://leetcode.cn/problems/champagne-tower/solution/by-ac_oier-c8jn/
     *         来源：力扣（LeetCode）
     *         著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
}


