package ways.problem;


class Solution {
    public final int MOD = 1000000007;

    public int ways(String[] pizza, int k) {
        int rol = pizza.length;
        int col = pizza[0].length();
        int[][] map = new int[rol + 1][col + 1];
        //由于右下角是最终的结果,所以从右下角进行统计,所以是递减的for循环
        for (int i = rol - 1; i >= 0; i--) {
            for (int j = col - 1; j >= 0; j--) {
                //对于map[i][j]表示i,j在右下加的map[row][col]存在的果子,统计当前位置map[i][j]的果子,可以从下方和右方
                //但是由于统计map[i + 1][j] 和map[i][j + 1] 的时候,都是从map[i + 1][j + 1]演变而来的,导致map[i + 1][j + 1]被统计了2次,因此需要减去
                map[i][j] = map[i + 1][j] + map[i][j + 1] - map[i + 1][j + 1];
                //判断一下,当前位置是不是果子
                if (pizza[i].charAt(j) == 'A') {
                    map[i][j]++;
                }
            }
        }
        int[][][] dp = new int[k][rol][col];
        dp[0][0][0] = 1;
        for (int n = 1; n < k; n++) {   //切n次
            for (int i = 0; i < rol; i++) {
                for (int j = 0; j < col; j++) {
                    //剩下i , j
                    int count = 0;

                    for (int l = 0; l < i; l++) {   //j不变
                        int flag = map[l][j] - map[i][j];
                        //flag !=0 代表着这个区间存在果子,可以分隔
                        //如果map[i][j] == 0 ,此时进行分割,就会出现右侧的行或者列没有苹果的情况
                        if (flag != 0 && map[i][j] != 0) {
                            count += dp[n - 1][l][j];
                            count %= MOD;
                        }
                    }
                    for (int l = 0; l < j; l++) {
                        int flag = map[i][l] - map[i][j];
                        if (flag != 0 && map[i][j] != 0) {
                            count += dp[n - 1][i][l];
                            count %= MOD;
                        }
                    }
                    dp[n][i][j] = count;
                }
            }
        }
        int res = 0;
        for (int i = 0; i < rol; i++) {
            for (int j = 0; j < col; j++) {
                res += dp[k - 1][i][j];
                res %= MOD;
            }
        }
        return res;
    }
}

/**
 * 作者：tu-bo-shu-8
 * 链接：https://leetcode.cn/problems/number-of-ways-of-cutting-a-pizza/solution/java-dong-tai-gui-hua-12ms-bu-zhi-dao-wei-sha-zhe-/
 * 来源：力扣（LeetCode）
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */


