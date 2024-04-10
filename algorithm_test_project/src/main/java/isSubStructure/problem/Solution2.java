package isSubStructure.problem;

import delNodes.problem.TreeNode;

class Solution2 {
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        //利用 || 或的操作,将全局的true向上传递
        return (A != null && B != null) && (recur(A, B) || isSubStructure(A.left, B) || isSubStructure(A.right, B));
    }

    boolean recur(TreeNode A, TreeNode B) {
        //B叶子节点为空,说明已经把B遍历完了,此时返回true
        if (B == null) return true;
        if (A == null || A.val != B.val) return false;
        //当前节点的值A == B,那么判断A和B,的左右节点的值是否相同
        return recur(A.left, B.left) && recur(A.right, B.right);
    }
}
//
//作者：jyd
//        链接：https://leetcode.cn/problems/shu-de-zi-jie-gou-lcof/solution/mian-shi-ti-26-shu-de-zi-jie-gou-xian-xu-bian-li-p/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。