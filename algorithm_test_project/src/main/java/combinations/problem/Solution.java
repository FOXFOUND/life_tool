package combinations.problem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;


/**
 * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
 * <p>
 * 你可以按 任何顺序 返回答案。
 */
public class Solution {

    List<List<Integer>> res = new ArrayList();
    HashMap<String, Integer> hashMap = new HashMap<>();

    public List<List<Integer>> combine(int n, int k) {

        Stack<Integer> stack = new Stack<>();
        combineSub(n, k, stack);
        return res;
    }

    private void combineSub(int n, int k, Stack<Integer> stack) {
        for (int i = 1; i <= n; i++) {
            //栈中存在重复数据
            if (stack.contains(i)) {
                continue;
            }
            stack.push(i);

            if (stack.size() == k) {
                List<Integer> result = new ArrayList<>();
                for (int j = 0; j < stack.size(); j++) {
                    result.add(stack.get(j));
                }
                result.sort(new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o1.intValue() - o2.intValue();
                    }
                });
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < result.size(); j++) {
                    stringBuilder.append(result.get(j));
                    stringBuilder.append("-");
                }
                if (hashMap.get(stringBuilder.toString()) == null) {
                    res.add(result);
                    hashMap.put(stringBuilder.toString(), 1);
                }
            }
            //减枝
            if (stack.size() > k) {
                stack.pop();
                return;
            }else {
                //递归
                combineSub(n, k, stack);
                stack.pop();
            }

        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        //solution.combine(4, 2);
        solution.combine(2, 2);
    }
}
