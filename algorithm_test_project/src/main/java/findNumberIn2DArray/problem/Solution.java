package findNumberIn2DArray.problem;

import java.util.Arrays;

/**
 * 在一个 n * m 的二维数组中，每一行都按照从左到右 非递减 的顺序排序，每一列都按照从上到下 非递减 的顺序排序。请完成一个高效的函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/er-wei-shu-zu-zhong-de-cha-zhao-lcof
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public boolean findNumberIn2DArray(int[][] matrix, int target) {


        int left = 0, right = matrix.length - 1;
        int middle = (right + left) / 2;
        while (left < right && matrix[middle][0] > target) {
            if (matrix[middle][0] > target) {
                right = middle - 1;
                middle = (right + left) / 2;
            }

        }

        for (int i = 0; i <= right; i++) {
            int res = Arrays.binarySearch(matrix[i], target);
            if (res >= 0) {
                return true;
            }
        }

        return false;
    }
}
