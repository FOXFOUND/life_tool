package longestZigZag.problem;

import delNodes.problem.TreeNode;

class Solution3 {
    int maxLength = 0;

    public int longestZigZag(TreeNode root) {
        getLengths(root);
        return maxLength;
    }

    public int[] getLengths(TreeNode node) {
        int[] lengths = new int[2];
        if (node.left != null) {
            int[] leftLengths = getLengths(node.left);
            lengths[0] = leftLengths[1] + 1;
        }
        if (node.right != null) {
            int[] rightLengths = getLengths(node.right);
            lengths[1] = rightLengths[0] + 1;
        }
        maxLength = Math.max(maxLength, Math.max(lengths[0], lengths[1]));
        return lengths;
    }
}

//作者：stormsunshine
//        链接：https://leetcode.cn/problems/longest-zigzag-path-in-a-binary-tree/solution/1372-er-cha-shu-zhong-de-zui-chang-jiao-wavxh/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
