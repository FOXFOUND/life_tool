package kthSmallest.problem;

import delNodes.problem.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    List<Integer> valueList = new ArrayList<>();

    public int kthSmallest(TreeNode root, int k) {

        preOrder(root);

        return valueList.get(k - 1);
    }

    private void preOrder(TreeNode root) {

        if (root == null) {
            return;
        }

        preOrder(root.left);
        valueList.add(root.val);
        preOrder(root.right);
    }
}
