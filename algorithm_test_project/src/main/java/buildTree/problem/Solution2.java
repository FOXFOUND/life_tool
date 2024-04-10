package buildTree.problem;

import delNodes.problem.TreeNode;

public class Solution2 {

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        int[] inoder = new int[]{9, 3, 15, 20, 7};
        int[] postoder = new int[]{9, 15, 7, 20, 3};
        solution2.buildTree(inoder, postoder);
    }

    /**
     * 给定两个整数数组 inorder 和 postorder ，其中 inorder 是二叉树的中序遍历，
     * postorder 是同一棵树的后序遍历，请你构造并返回这颗 二叉树 。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode.cn/problems/construct-binary-tree-from-inorder-and-postorder-traversal
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        TreeNode root = buildTreePartition(inorder, postorder, 0, inorder.length - 1, 0, postorder.length - 1);
        return root;
    }

    private TreeNode buildTreePartition(int[] inorder, int[] postorder, int leftInOder, int rightInOrder, int leftPostOrder, int rightPostOrder) {


        if (leftInOder > rightInOrder
                || leftPostOrder > rightPostOrder) {
            return null;
        }

        int root = leftInOder;

        for (int i = leftInOder; i <= rightInOrder; i++) {
            if (inorder[i] == postorder[rightPostOrder]) {
                root = i;
                break;
            }
        }


        TreeNode treeNode = new TreeNode(inorder[root]);

        int leftNodeLength = root - leftInOder - 1;
        TreeNode left = buildTreePartition(inorder, postorder, leftInOder, root - 1, leftPostOrder, leftPostOrder + leftNodeLength);
        TreeNode right = buildTreePartition(inorder, postorder, root + 1, rightInOrder, leftPostOrder + leftNodeLength + 1, rightPostOrder - 1);

        treeNode.left = left;
        treeNode.right = right;

        return treeNode;

    }
}
