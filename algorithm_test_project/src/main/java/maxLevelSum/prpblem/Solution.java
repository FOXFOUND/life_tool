package maxLevelSum.prpblem;

import delNodes.problem.TreeNode;

import java.util.Arrays;

public class Solution {
    int[] levelNum = new int[10001];
    boolean[] init = new boolean[10001];

    public int maxLevelSum(TreeNode root) {
        Arrays.fill(init,false);
        dfs(root, 0);
        int res = 0;
        for (int i = 0; i < levelNum.length; i++) {
            if (init[i] &&levelNum[i] > levelNum[res]) {
                res = i;
            }
        }
        return res + 1;
    }

    private void dfs(TreeNode root, int i) {

        if (root == null) {
            return;
        }
        init[i] = true;
        levelNum[i] += root.val;
        dfs(root.left, i + 1);
        dfs(root.right, i + 1);

    }
}
