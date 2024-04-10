package buildTree.problem;

import delNodes.problem.TreeNode;

class Solution {
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return build(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    TreeNode build(int[] preorder, int[] inorder, int preStart, int preEnd, int inoStart, int inoEnd) {
        //base case
        if (preStart > preEnd || inoStart > inoEnd) {
            return null;
        }
        //树的根节点
        int val = preorder[preStart];
        TreeNode root = new TreeNode(val);
        //查找根节点在中序遍历的位置
        int index = 0;
        for (int i = inoStart; i <= inoEnd; i++) {
            if (inorder[i] == val) {
                index = i;
                break;
            }
        }
        //计算左子树在数组中的长度
        int leftSize = index - inoStart;
        //递归构建。
        root.left = build(preorder, inorder, preStart + 1, preStart + leftSize, inoStart, index - 1);
        root.right = build(preorder, inorder, preStart + leftSize + 1, preEnd, index + 1, inoEnd);
        return root;
    }
}

//作者：LeetCode_xsong
//        链接：https://leetcode.cn/problems/zhong-jian-er-cha-shu-lcof/solution/by-leetcode_xsong-3dg5/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。