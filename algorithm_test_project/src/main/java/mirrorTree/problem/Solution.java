package mirrorTree.problem;

import delNodes.problem.TreeNode;

public class Solution {
    public TreeNode mirrorTree(TreeNode root) {

        if (root == null) {
            return null;
        }

        reverseTreeNode(root.left, root.right, root, root);
        return root;

    }

    private void reverseTreeNode(TreeNode left, TreeNode right, TreeNode leftRoot, TreeNode rightRoot) {
        if (left == null && right == null) {
            return;
        }

        if (left != null && right != null) {
            int temp = left.val;
            left.val = right.val;
            right.val = temp;
            reverseTreeNode(left.left, right.right, left, right);
            reverseTreeNode(left.right, right.left, left, right);
            return;
        }

        if (left == null) {
            leftRoot.left = right;
            reverseTreeNode(right.left, right.right, right, right);
            rightRoot.right = null;
            return;
        }
        if (right == null) {
            rightRoot.right = left;
            reverseTreeNode(left.left, left.right, left, left);
            leftRoot.left = null;
            return;
        }
    }
}
