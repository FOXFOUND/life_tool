package subsets.problem;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 给定一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。
 * <p>
 * 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。
 */


public class Solution {



    public List<List<Integer>> subsets(int[] nums) {
        Stack<Integer> stack = new Stack();

        List<List<Integer>> res = new ArrayList<>();
        sub(nums, 0,stack,res);
        res.add(new ArrayList<>());
        return res;
    }

    private void sub(int[] nums, int n, Stack<Integer> stack, List<List<Integer>> res) {
        for (int i = n; i < nums.length; i++) {
            stack.push(nums[i]);
            //当前栈
            List<Integer> list = getList(stack);
            res.add(list);
            sub(nums, i + 1, stack, res);
            stack.pop();
        }
    }

    private List<Integer> getList(Stack<Integer> stack) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < stack.size(); i++) {
            list.add(stack.get(i));
        }
        return list;
    }

    public static void main(String[] args) {
        //int[] nums = {1, 2, 3};
        int[] nums = {0};
        Solution solution = new Solution();
        solution.subsets(nums);
        // [[1],[1,2],[1,2,3],[1,3],[2],[2,3],[3]]
        //System.out.println(JSON.toJSONString(res));
    }
}
