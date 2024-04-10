package longestZigZag.problem;

import delNodes.problem.TreeNode;

public class Solution {

    int num = 0;

    public int longestZigZag(TreeNode root) {

        // 0 代表左, 1代表右

        dfs(root.left, 0, 1);
        dfs(root.right, 1, 1);
        return num;
    }

    public void dfs(TreeNode root, int direct, int length) {
        if (root == null) {
            return;
        }

        num = Math.max(length, num);

        if (direct == 0) {
            dfs(root.right, 1, length + 1);
        } else {
            dfs(root.left, 0, length + 1);
        }

        dfs(root.left, 0, 1);
        dfs(root.right, 1, 1);

    }
}
