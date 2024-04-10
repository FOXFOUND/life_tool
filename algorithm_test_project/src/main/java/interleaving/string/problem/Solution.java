package interleaving.string.problem;

public class Solution {


    public boolean isInterleave(String s1, String s2, String s3) {
        int n = s1.length(), m = s2.length(), t = s3.length();
        if (n + m != t) {
            return false;
        }
        boolean[][] f = new boolean[2][m + 1];
        f[0][0] = true;
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= m; ++j) {
                int p = i + j - 1;
                if (i > 0) {
                    f[i & 1][j] = f[(i - 1) & 1][j] && s1.charAt(i - 1) == s3.charAt(p);
                }
                if (j > 0) {
                    f[i & 1][j] = f[i & 1][j] || (f[i & 1][j - 1] && s2.charAt(j - 1) == s3.charAt(p));
                }
            }
        }
        return f[n & 1][m];
    }



    public boolean isInterleave2(String s1, String s2, String s3) {
        int n = s1.length(), m = s2.length(), t = s3.length();
        if (n + m != t) {
            return false;
        }
        boolean[] f = new boolean[m + 1];
        f[0] = true;
        for (int i = 0; i <= n; ++i) {
            for (int j = 0; j <= m; ++j) {
                int p = i + j - 1;
                if (i > 0) {
                    // 这里我们获取到的f[j]就是上一轮外层循环中的f[i - 1][j]
                    f[j] = f[j] && s1.charAt(i - 1) == s3.charAt(p);
                }
                if (j > 0) {
                    f[j] = f[j] || (f[j - 1] && s2.charAt(j - 1) == s3.charAt(p));
                }
            }
        }
        return f[m];
    }

    /**
     *      作者：xiaomaizi
     *     链接：https://leetcode.cn/problems/interleaving-string/solution/gun-dong-shu-zu-jie-shi-by-xiaomaizi-d53p/
     *     来源：力扣（LeetCode）
     *     著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */


}
