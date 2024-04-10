package levelOrder.problem;

import delNodes.problem.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    List<Integer>[] resArr = new List[1001];

    public List<List<Integer>> levelOrder(TreeNode root) {

        for (int i = 0; i < resArr.length; i++) {
            resArr[i] = new ArrayList<>();
        }

        dfs(root, 0);

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < resArr.length; i++) {
            if (resArr[i].size() > 0) {
                res.add(resArr[i]);
            }
        }
        return res;

    }

    private void dfs(TreeNode root, int level) {

        if (root == null) {
            return;
        }

        resArr[level].add(root.val);
        dfs(root.left, level + 1);
        dfs(root.right, level + 1);
    }
}
