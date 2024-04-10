package sumSubarrayMins.problem;

import java.util.Stack;

/**
 * 给定一个整数数组 arr，找到 min(b) 的总和，其中 b 的范围为 arr 的每个（连续）子数组。
 * <p>
 * 由于答案可能很大，因此 返回答案模 10^9 + 7 。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/sum-of-subarray-minimums
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int sumSubarrayMins(int[] arr) {

        Stack<Integer> stack = new Stack<>();
        int res = 0;

        for (int i = 0; i < arr.length; i++) {
            stack.push(arr[i]);
            int capacity = stack.size();
            int temp = 0;
            for (int j = capacity - 1; j >= 0; j--) {
                temp += stack.get(j);
                res += temp;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] arr = new int[]{3, 1, 2, 4};  //17
        int res = solution.sumSubarrayMins(arr);
        System.out.println(res);
    }
}
