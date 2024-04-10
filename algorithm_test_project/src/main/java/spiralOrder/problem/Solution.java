package spiralOrder.problem;

import com.alibaba.fastjson.JSON;

public class Solution {

    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Solution solution = new Solution();
        int[] res = solution.spiralOrder(matrix);
        System.out.println(JSON.toJSONString(res));
    }

    public int[] spiralOrder(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int[] res = new int[n * m];
        int index = 0;
        //  -1 左 1 右 2 上 -2 下
        int direct = 1;
        int x = 0, y = 0;
        res[index] = matrix[x][y];
        int mSub = 0;
        int nSub = 1;
        // m, n -1 , m -1 ,n -2 ,n-2
        int temp = 0;
        while (true) {
            if (index == n * m) {
                break;
            }


            index++;
            temp++;
            if (direct == 1) {
                if (temp == m - mSub) {
                    direct = -2;
                    temp = 0;
                    mSub++;
                } else {
                    y++;
                }


            }
            if (direct == -2) {
                if (temp == n - nSub) {
                    direct = -1;
                    temp = 0;
                    nSub++;

                } else {

                    x++;
                }
            }

            if (direct == -1) {
                if (temp == m - mSub) {
                    direct = 2;
                    temp = 0;
                    mSub++;

                } else {

                    y--;
                }

            }

            if (direct == 2) {
                if (temp == n - nSub) {
                    direct = 1;
                    temp = 0;
                    nSub++;
                } else {
                    x--;
                }

            }

            res[index] = matrix[x][y];
        }

        return res;

    }
}
