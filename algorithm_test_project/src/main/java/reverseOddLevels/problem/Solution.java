package reverseOddLevels.problem;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 给你一棵 完美 二叉树的根节点 root ，请你反转这棵树中每个 奇数 层的节点值。
 * <p>
 * 例如，假设第 3 层的节点值是 [2,1,3,4,7,11,29,18] ，那么反转后它应该变成 [18,29,11,7,4,3,1,2] 。
 * 反转后，返回树的根节点。
 * <p>
 * 完美 二叉树需满足：二叉树的所有父节点都有两个子节点，且所有叶子节点都在同一层。
 * <p>
 * 节点的 层数 等于该节点到根节点之间的边数。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/reverse-odd-levels-of-binary-tree
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class Solution {
    public TreeNode reverseOddLevels(TreeNode root) {

        Map<Integer, LinkedList<Integer>> map = new HashMap<>();
        reverseOddLevelsBackOrder(root, 0, map);
        map.forEach((k, v) -> {
            Collections.reverse(v);
        });
        reverseOddLevelsBackOrderNew(root, 0, map);
        return root;

    }

    private void reverseOddLevelsBackOrderNew(TreeNode root, int i, Map<Integer, LinkedList<Integer>> map) {

        if (root == null) {
            return;
        }

        reverseOddLevelsBackOrderNew(root.left, i + 1, map);
        reverseOddLevelsBackOrderNew(root.right, i + 1, map);

        if (i % 2 != 0) {
            LinkedList<Integer> mapValue = map.getOrDefault(i, new LinkedList<>());
            int value = mapValue.removeFirst();
            root.val = value;
            map.put(i, mapValue);
        }
    }

    private void reverseOddLevelsBackOrder(TreeNode root, int i, Map<Integer, LinkedList<Integer>> map) {

        if (root == null) {
            return;
        }

        reverseOddLevelsBackOrder(root.left, i + 1, map);
        reverseOddLevelsBackOrder(root.right, i + 1, map);
        //奇数层
        if (i % 2 != 0) {
            LinkedList mapValue = map.getOrDefault(i, new LinkedList<>());
            mapValue.addLast(root.val);
            map.put(i, mapValue);
        }

    }


}
