package combine.problem;

import java.util.ArrayList;
import java.util.List;

class Solution {
    private int k;
    private final List<Integer> path = new ArrayList<>();
    private final List<List<Integer>> ans = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        this.k = k;
        dfs(n);
        return ans;
    }

    private void dfs(int i) {
        int d = k - path.size(); // 还要选 d 个数
        if (d == 0) {
            ans.add(new ArrayList<>(path));
            return;
        }
        // 不选 i
        if (i > d) dfs(i - 1);
        // 选 i
        path.add(i);
        dfs(i - 1);
        path.remove(path.size() - 1);
    }
}

//作者：endlesscheng
//        链接：https://leetcode.cn/problems/uUsW3B/solution/hui-su-bu-hui-xie-tao-lu-zai-ci-pythonja-6zca/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。