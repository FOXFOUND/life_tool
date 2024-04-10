package combinationSum2.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution {

    private static Set<String> existSet = new HashSet<>();
    private static List<List<Integer>> lists = new ArrayList<>();

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);

        int sum = 0;
        for (int i = 0; i < candidates.length; i++) {
            int current = i;
            List<Integer> integerList = new ArrayList<>();
            comSub(candidates, target, current, sum, integerList);
        }
        return lists;
    }

    private static void comSub(int[] candidates, int target, int current, int sum, List<Integer> integerList) {

        for (int i = current; i < candidates.length; i++) {
            sum += candidates[i];
            integerList.add(candidates[i]);
            if (sum > target) {
                return;
            }
            if (sum == target) {
                String existStr = "";
                for (int j = 0; j < integerList.size(); j++) {
                    existStr += integerList.get(j);
                }
                if (existSet.contains(existStr)) {
                    return;
                }
                lists.add(integerList);
                existSet.add(existStr);
            }
            List<Integer> copList = new ArrayList<>();
            for (int j = 0; j < integerList.size(); j++) {
                copList.add(integerList.get(j));
            }
            comSub(candidates, target, i + 1, sum, copList);
        }
    }


    public static void main(String[] args) {
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;
        Solution solution = new Solution();
        solution.combinationSum2(candidates, target);
    }
}
