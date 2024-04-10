package reverseOddLevels.problem;

class Solution2 {
    public TreeNode reverseOddLevels(TreeNode root) {
        dfs(root.left, root.right, 1);
        return root;
    }

    void dfs(TreeNode l, TreeNode r, int level) {
        if (l == null) {
            return;
        }
        if (level % 2 != 0) {
            int val = r.val;
            r.val = l.val;
            l.val = val;
        }
        dfs(l.left, r.right, level + 1);
        dfs(l.right, r.left, level + 1);
    }
}

//作者：not-a-hero
//        链接：https://leetcode.cn/problems/reverse-odd-levels-of-binary-tree/solution/dfsjie-fa-by-not-a-hero-d8zk/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。