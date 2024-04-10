package combinationSum2.problem;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class Solution2 {

    private static Set<String> existSet = new HashSet<>();

    private static Stack<Integer> stack = new Stack<>();

    private static List<List<Integer>> lists = new ArrayList<>();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        for (int i = 0; i < candidates.length; i++) {
            comSub(candidates, target, 0, i);
        }
        return lists;
    }

    private static void comSub(int[] candidates, int target, int sum, int current) {
        for (int i = current; i < candidates.length; i++) {
            sum += candidates[i];
            stack.push(candidates[i]);
            if (sum > target) {
                return;
            }
            if (sum == target) {
                List<Integer> res = new ArrayList<>();
                for (int j = 0; j < stack.size(); j++) {
                    res.add(stack.get(i));
                }
                String existStr = "";
                for (int j = 0; j < res.size(); j++) {
                    existStr += res.get(i);
                }
                if (existSet.contains(existStr)) {
                    return;
                }
                lists.add(res);
                existSet.add(existStr);
            }

            comSub(candidates, target, sum, i + 1);
            stack.pop();

        }
    }

    public static void main(String[] args) {
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;
        Solution2 solution2 = new Solution2();
        solution2.combinationSum2(candidates,target);
        System.out.println(JSON.toJSONString(lists));
    }
}
