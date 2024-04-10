package rearrangeArray.problem;

import com.alibaba.fastjson.JSON;

import java.util.Stack;

/**
 * 给你一个 下标从 0 开始 的数组 nums ，数组由若干 互不相同的 整数组成。你打算重新排列数组中的元素以满足：重排后，数组中的每个元素都 不等于 其两侧相邻元素的 平均值 。
 * <p>
 * 更公式化的说法是，重新排列的数组应当满足这一属性：对于范围 1 <= i < nums.length - 1 中的每个 i ，(nums[i-1] + nums[i+1]) / 2 不等于 nums[i] 均成立 。
 * <p>
 * 返回满足题意的任一重排结果。
 * <p>
 * <p>
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/array-with-elements-not-equal-to-average-of-neighbors
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public int[] rearrangeArray(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int[] res = rearrangeArraySub(nums, stack);
        return res;
    }

    private int[] rearrangeArraySub(int[] nums, Stack<Integer> stack) {
        //终止条件
        if (stack.size() >= 3) {
            for (int i = 1; i < stack.size() - 1; i++) {
                if (stack.get(i - 1).intValue() + stack.get(i + 1).intValue() == stack.get(i).intValue() * 2) {
                    return null;
                }
            }
        }

        //符合要求
        if (stack.size() == nums.length) {
            int[] res = new int[nums.length];
            for (int i = 0; i < stack.size(); i++) {
                res[i] = stack.get(i);
            }
            return res;
        }


        //递归条件
        for (int i = 0; i < nums.length; i++) {
            if (stack.contains(nums[i])) {
                continue;
            }
            stack.push(nums[i]);
            int[] res = rearrangeArraySub(nums, stack);
            stack.pop();

            if (res != null) {
                return res;
            }

        }

        return null;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = new int[]{1, 2, 3, 4, 5};
        int[] res = solution.rearrangeArray(nums);
        System.out.println(JSON.toJSONString(res));
    }
}
