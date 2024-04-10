package isBalanced.problem;

import delNodes.problem.TreeNode;

public class Solution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        } else {
            return Math.abs(height(root.left) - height(root.right)) <= 1 && isBalanced(root.left) && isBalanced(root.right);
        }
    }

    public int height(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return Math.max(height(root.left), height(root.right)) + 1;
        }
    }

//    作者：LeetCode-Solution
//    链接：https://leetcode.cn/problems/ping-heng-er-cha-shu-lcof/solution/ping-heng-er-cha-shu-by-leetcode-solutio-6r1g/
//    来源：力扣（LeetCode）
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
