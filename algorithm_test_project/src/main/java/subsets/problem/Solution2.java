package subsets.problem;

import java.util.ArrayList;
import java.util.List;

class Solution2 {


    public static void main(String[] args) {

        Solution2 solution2 = new Solution2();
        int[] nums = new int[]{1, 2, 3};
        solution2.subsets(nums);
    }


    List<Integer> cache = new ArrayList();
    List<List<Integer>> res = new ArrayList();

    public List<List<Integer>> subsets(int[] nums) {

        res.add(new ArrayList());

        dfs(nums , 0);
        return res;
    }


    public void dfs(int[] nums, int start) {
        for (int i = start; i < nums.length; i++) {

            if (cache.contains(nums[i])) {
                continue;
            }

            cache.add(nums[i]);
            List temp = new ArrayList(cache);
            res.add(temp);
            dfs(nums, i+1);
            cache.remove(cache.size() - 1);
        }
    }

}