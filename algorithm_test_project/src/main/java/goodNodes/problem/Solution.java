package goodNodes.problem;

import delNodes.problem.TreeNode;

public class Solution {

    int num;

    public int goodNodes(TreeNode root) {


        dfs(root, root.val);
        return num;
    }

    private void dfs(TreeNode root, int val) {
        if (root == null) {
            return;
        }

        if (val <= root.val) {
            num++;
        }
        int max = Math.max(root.val, val);
        dfs(root.left, max);
        dfs(root.right, max);

    }
}
