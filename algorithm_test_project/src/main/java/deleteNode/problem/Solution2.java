package deleteNode.problem;

import delNodes.problem.TreeNode;

class Solution2 {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;
        if (root.val == key) {
            if (root.right == null) return root.left;
            TreeNode node = leftest(root.right);
            node.left = root.left;
            return root.right;
        } else {
            if (root.left != null && root.left.val == key) {
                root.left = deleteNode(root.left, key);
            } else if (root.right != null && root.right.val == key) {
                root.right = deleteNode(root.right, key);
            } else {
                deleteNode(root.left, key);
                deleteNode(root.right, key);
            }
        }
        return root;
    }

    public TreeNode leftest(TreeNode root) {
        while (root.left != null) root = root.left;
        return root;
    }
}

//作者：5FBLHq4BXL
//        链接：https://leetcode.cn/problems/delete-node-in-a-bst/solution/javashan-chu-er-cha-shu-jie-dian-by-5fbl-yx0n/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
