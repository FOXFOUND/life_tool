package getDirections.problem;

import java.util.Stack;

/**
 * 给你一棵 二叉树 的根节点 root ，这棵二叉树总共有 n 个节点。每个节点的值为 1 到 n 中的一个整数，且互不相同。给你一个整数 startValue ，表示起点节点 s 的值，和另一个不同的整数 destValue ，表示终点节点 t 的值。
 * <p>
 * 请找到从节点 s 到节点 t 的 最短路径 ，并以字符串的形式返回每一步的方向。每一步用 大写 字母 'L' ，'R' 和 'U' 分别表示一种方向：
 * <p>
 * 'L' 表示从一个节点前往它的 左孩子 节点。
 * 'R' 表示从一个节点前往它的 右孩子 节点。
 * 'U' 表示从一个节点前往它的 父 节点。
 * 请你返回从 s 到 t 最短路径 每一步的方向。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode.cn/problems/step-by-step-directions-from-a-binary-tree-node-to-another
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
class Solution {
    public String getDirections(TreeNode root, int startValue, int destValue) {

        Stack<Character> pathStack = new Stack<>();

        String resStart = findValue(root, startValue, pathStack);
        pathStack.clear();
        String resDes = findValue(root, destValue, pathStack);


        //二者在同一侧,二者在两侧
        StringBuilder stringBuilder = new StringBuilder();

        //找到最近的父节点
        int index = 0;
        while (index < resStart.length()
                && index < resDes.length()
                && resStart.charAt(index) == resDes.charAt(index)) {
            index++;
        }

        //向上跳转到父节点
        for (int i = resStart.length() - 1; i >= index; i--) {
            stringBuilder.append("U");
        }

        //按照父节点的路径找到des
        for (int i = index; i < resDes.length(); i++) {
            stringBuilder.append(resDes.charAt(i));
        }
        return stringBuilder.toString();


    }

    private String findValue(TreeNode root, int value, Stack<Character> s) {

        if (root == null) {
            return "";
        }

        if (root.val == value) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < s.size(); i++) {
                stringBuilder.append(s.get(i));
            }
            return stringBuilder.toString();
        }

        s.push(new Character('L'));
        String resLeft = findValue(root.left, value, s);
        s.pop();
        if (!resLeft.equals("")) {
            return resLeft;
        }
        s.push(new Character('R'));
        String resRight = findValue(root.right, value, s);
        s.pop();
        if (!resRight.equals("")) {
            return resRight;
        }

        return resRight;

    }


    public static void main(String[] args) {
        Solution solution = new Solution();

    }
}
