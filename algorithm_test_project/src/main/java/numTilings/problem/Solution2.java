package numTilings.problem;

class Solution2 {
    private static final long MOD = (long) 1e9 + 7;

    public int numTilings(int n) {
        if (n == 1) return 1;
        long[] f = new long[n + 1];
        f[0] = f[1] = 1;
        f[2] = 2;
        for (int i = 3; i <= n; ++i)
            f[i] = (f[i - 1] * 2 + f[i - 3]) % MOD;
        return (int) f[n];
    }
}

//作者：endlesscheng
//        链接：https://leetcode.cn/problems/domino-and-tromino-tiling/solution/by-endlesscheng-umpp/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
