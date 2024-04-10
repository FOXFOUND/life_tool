package isSymmetric.problem;

import delNodes.problem.TreeNode;

public class Solution {
    public boolean isSymmetric(TreeNode root) {

        if (root == null) {
            return true;
        }

        return judgeNode(root.left, root.right);
    }

    private boolean judgeNode(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left != null && right != null && left.val == right.val) {
            return judgeNode(left.left, right.right) && judgeNode(left.right, right.left);
        }

        return false;

    }

}
