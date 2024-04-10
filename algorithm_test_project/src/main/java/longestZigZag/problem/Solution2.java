package longestZigZag.problem;

import delNodes.problem.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class Solution2 {

    int num = 0;
    Map<TreeNode, Integer> heightMap = new HashMap<>();

    public int longestZigZag(TreeNode root) {

        // 0 代表左, 1代表右

        dfs(root.left, 0);
        dfs(root.right, 1);
        return num;
    }

    public int dfs(TreeNode root, int direct) {
        if (root == null) {
            return 0;
        }

        if (heightMap.get(root) != null) {
            return heightMap.get(root);
        }

        int maxRes = 0;


        int resLeft = dfs(root.left, 0);
        int resRight = dfs(root.right, 1);

        maxRes = Math.max(resLeft + 1, maxRes);
        maxRes = Math.max(resRight + 1, maxRes);


        if (direct == 0) {
            int res = dfs(root.right, 1);
            maxRes = Math.max(res + 1, maxRes);
        } else {
            int res = dfs(root.left, 0);
            maxRes = Math.max(res + 1, maxRes);
        }


        heightMap.put(root, maxRes);
        num = Math.max(maxRes, num);

        return maxRes;
    }
}
