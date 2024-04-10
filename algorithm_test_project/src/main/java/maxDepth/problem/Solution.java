package maxDepth.problem;

public class Solution {
    public int maxDepth(TreeNode root) {

        int res = partition(root, 0);
        return res;
    }

    private int partition(TreeNode root, int depth) {
        if (root == null) {
            return depth;
        }

        int leftDepth = partition(root.left, depth + 1);
        int rightDepth = partition(root.right, depth + 1);
        return Math.max(leftDepth, rightDepth);
    }
}
