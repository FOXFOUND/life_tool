package rightSideView.problem;

import delNodes.problem.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    //完全二叉树可以
    public List<Integer> rightSideView(TreeNode root) {

        List<Integer> res = new ArrayList<>();

        while (root != null){
            res.add(root.val);
            root = root.right;
        }
        return res;
    }
}
