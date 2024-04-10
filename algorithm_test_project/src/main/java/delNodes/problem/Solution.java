package delNodes.problem;

import com.alibaba.fastjson.JSON;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */

/**
 * 给出二叉树的根节点 root，树上每个节点都有一个不同的值。
 * <p>
 * 如果节点值在 to_delete 中出现，我们就把该节点从树上删去，最后得到一个森林（一些不相交的树构成的集合）。
 * <p>
 * 返回森林中的每棵树。你可以按任意顺序组织答案。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/delete-nodes-and-return-forest
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */

class Solution {
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        Set<Integer> integerSet = new HashSet<>();
        for (int i = 0; i < to_delete.length; i++) {
            integerSet.add(to_delete[i]);
        }
        List<TreeNode> treeNodeList = new ArrayList<>();
        Queue<TreeNode> treeNodeQueue = new ArrayDeque<>();
        Map<Integer, TreeNode> treeNodeMap = new HashMap<>();
        treeNodeQueue.add(root);
        if (!integerSet.contains(root.val)) {
            treeNodeList.add(root);
        }
        while (true) {
            TreeNode treeNode = treeNodeQueue.poll();
            if (treeNode == null) {
                break;
            }


            if (treeNode.left != null) {
                treeNodeMap.put(treeNode.left.val, treeNode);
                treeNodeQueue.add(treeNode.left);

            }
            if (treeNode.right != null) {
                treeNodeMap.put(treeNode.right.val, treeNode);
                treeNodeQueue.add(treeNode.right);
            }

            if (integerSet.contains(treeNode.val)) {
                if (treeNode.left != null && !integerSet.contains(treeNode.left.val)) {

                    treeNodeList.add(treeNode.left);
                }
                if (treeNode.right != null && !integerSet.contains(treeNode.right.val)) {

                    treeNodeList.add(treeNode.right);
                }
                TreeNode treeNodeFather = treeNodeMap.get(treeNode.val);
                if (treeNodeFather != null) {
                    if (treeNodeFather.left != null && treeNodeFather.left.val == treeNode.val) {
                        treeNodeFather.left = null;
                    } else {
                        treeNodeFather.right = null;
                    }
                }

            }
        }


        return treeNodeList;

    }


    public static void main(String[] args) {
        TreeNode root = TreeUtil.createTree(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        //System.out.println(root.val);
        int[] to_delete = {3, 5};
        Solution solution = new Solution();
        List<TreeNode> treeNodeList = solution.delNodes(root, to_delete);
        System.out.println(JSON.toJSONString(treeNodeList));
    }
}