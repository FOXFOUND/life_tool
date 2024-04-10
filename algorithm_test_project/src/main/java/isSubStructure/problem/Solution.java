package isSubStructure.problem;

import delNodes.problem.TreeNode;

public class Solution {

    StringBuilder sA = new StringBuilder();
    StringBuilder sB = new StringBuilder();

    public boolean isSubStructure(TreeNode A, TreeNode B) {

        if(A == null && B!= null){
            return false;
        }
        if(A!=null && B == null){
            return false;
        }

        dfs(A, "A");
        dfs(B, "B");
        return sA.toString().contains(sB.toString());
    }

    private void dfs(TreeNode node, String flag) {
        if (node == null) {
            return;
        }

        if (flag.equals("A")) {
            sA.append(node.val);
        } else {
            sB.append(node.val);
        }
        dfs(node.left, flag);
        dfs(node.right, flag);

    }
}
