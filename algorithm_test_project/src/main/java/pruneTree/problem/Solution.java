package pruneTree.problem;

import delNodes.problem.TreeNode;

public class Solution {
    public TreeNode pruneTree(TreeNode root) {

        if (root == null) {
            return null;
        }

        if (root.left == null && root.right == null && root.val == 0) {
            return null;
        }
        if (root.left != null && root.right != null
                && root.left.val == 0 && root.right.val == 0) {
            root.left = null;
            root.right = null;
        }
        TreeNode left = pruneTree(root.left);
        TreeNode right = pruneTree(root.right);

        root.left = left;
        root.right = right;
        return root;
    }
}
