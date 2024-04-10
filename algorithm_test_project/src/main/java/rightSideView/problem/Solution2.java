package rightSideView.problem;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution2 {

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        List<Integer> res = solution2.rightSideView(treeNode1);
        System.out.println(res);
    }


    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Set<Integer> levelSet = new HashSet<>();
        partition(root, 0, levelSet, res);
        return res;
    }

    private void partition(TreeNode root, int level, Set<Integer> levelSet, List<Integer> res) {

        if (root == null) {
            return;
        }

        if (levelSet.contains(level)) {
            partition(root.right, level + 1, levelSet, res);
            partition(root.left, level + 1, levelSet, res);
            return;
        }

        levelSet.add(level);
        res.add(root.val);
        partition(root.right, level + 1, levelSet, res);
        partition(root.left, level + 1, levelSet, res);

    }
}
