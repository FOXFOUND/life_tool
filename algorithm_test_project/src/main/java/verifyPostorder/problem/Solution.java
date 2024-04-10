package verifyPostorder.problem;

import java.util.ArrayList;
import java.util.List;


public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] nums = new int[]{4, 8, 6, 12, 16, 14, 10};
        solution.verifyPostorder(nums);
    }

    List<Integer> res = new ArrayList<>();

    public boolean verifyPostorder(int[] postorder) {

        if (postorder == null) {
            return true;
        }

        int n = postorder.length;
        TreeNode root = new TreeNode(postorder[n - 1]);
        for (int i = 0; i < n - 1; i++) {
            TreeNode temp = root;
            TreeNode pre = null;
            int current = postorder[i];
            boolean leftFlag = true;
            while (temp != null) {
                pre = temp;
                if (current > temp.val) {
                    temp = temp.right;
                    leftFlag = false;
                } else {
                    temp = temp.left;
                    leftFlag = true;

                }
            }
            temp = new TreeNode(current);
            if (leftFlag) {
                pre.left = temp;
            } else {
                pre.right = temp;
            }


        }

        postOrder(root);

        for (int i = 0; i < postorder.length; i++) {
            if (postorder[i] != res.get(i).intValue()) {
                return false;
            }
        }

        return true;

    }

    private void postOrder(TreeNode root) {

        if (root == null) {
            return;
        }
        postOrder(root.left);
        postOrder(root.right);
        res.add(root.val);
    }

}

class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
