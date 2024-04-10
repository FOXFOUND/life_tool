package widthOfBinaryTree.problem;

import delNodes.problem.TreeNode;

public class Solution {

    int[][] height = new int[3001][2];

    public int widthOfBinaryTree(TreeNode root) {

        if (root == null) {
            return 0;
        }


        dfs(root.left, 1, 1);
        dfs(root.right, 1, 2);

        int max = 0;

        for (int i = 0; i < height.length; i++) {
            max = Math.max(max, height[i][1] - height[i][0] + 1);
        }
        return max;
    }

    private void dfs(TreeNode root, int level, int index) {

        if (root == null) {
            return;
        }

        if (height[level][0] == 0) {
            height[level][0] = index;
        } else {
            if (index < height[level][0]) {
                height[level][0] = index;
            }
        }


        if (height[level][1] == 0) {
            height[level][1] = index;
        } else {
            if (index > height[level][1]) {
                height[level][1] = index;
            }
        }


        dfs(root.left, level + 1, 2 * index + 1);
        dfs(root.right, level + 1, 2 * index + 2);
    }
}
