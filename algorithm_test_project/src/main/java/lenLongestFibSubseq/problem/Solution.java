package lenLongestFibSubseq.problem;

import java.util.HashSet;
import java.util.Set;

/**
 * 如果序列 X_1, X_2, ..., X_n 满足下列条件，就说它是 斐波那契式 的：
 * <p>
 * n >= 3
 * 对于所有 i + 2 <= n，都有 X_i + X_{i+1} = X_{i+2}
 * 给定一个严格递增的正整数数组形成序列 arr ，找到 arr 中最长的斐波那契式的子序列的长度。如果一个不存在，返回  0 。
 * <p>
 * （回想一下，子序列是从原序列  arr 中派生出来的，它从 arr 中删掉任意数量的元素（也可以不删），而不改变其余元素的顺序。例如， [3, 5, 8] 是 [3, 4, 5, 6, 7, 8] 的一个子序列）
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/Q91FMA
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {


    public int lenLongestFibSubseq(int[] arr) {

        Set<Integer> numSet = new HashSet<>();

        for (int i = 0; i < arr.length; i++) {
            numSet.add(arr[i]);
        }

        //dfs
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            //设置开始位置
            int res = lenLongestFibSubseqSub(arr, i, i, 1, numSet);
            if (res > max) {
                max = res;
            }

        }
        return max;
    }

    private int lenLongestFibSubseqSub(int[] arr, int index, int pre, int num, Set<Integer> numSet) {


        if (index >= arr.length) {
            return num;
        }

        int max = num, res = num;
        for (int j = index + 1; j < arr.length; j++) {
            if (numSet.contains(arr[pre] + arr[index])) {
                System.out.println(index + "_" + pre + "_" + num + "_" + (arr[pre] + arr[index]) + "_" + arr[pre] + "_" + arr[index]);

                int next =num + 1;
                res = lenLongestFibSubseqSub(arr, j, index,next , numSet);
                if (res > max) {
                    max = res;
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        //int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8};  //5
        //int[] arr = new int[]{1, 3, 7, 11, 12, 14, 18}; //3
        int[] arr = new int[]{2, 4, 5, 6, 7, 8, 11, 13, 14, 15, 21, 22, 34};  //5
        int res = solution.lenLongestFibSubseq(arr);
        System.out.println(res);
    }
}
