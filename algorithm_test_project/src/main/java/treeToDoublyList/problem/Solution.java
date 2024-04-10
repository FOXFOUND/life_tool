package treeToDoublyList.problem;

import java.util.ArrayList;
import java.util.List;

class Solution {
    List<Node> list = new ArrayList<>();

    public Node treeToDoublyList(Node root) {
//执行用时：0 ms,在所有 Java 提交中击败了100.00%的用户
        if (root == null) return null;
        addList(root);
        Node head = list.get(0), tail = list.get(list.size() - 1);
        for (int i = 0; i < list.size() - 1; i++) {
            list.get(i).right = list.get(i + 1);
            list.get(i + 1).left = list.get(i);
        }
        head.left = tail;
        tail.right = head;
        return head;
    }

    private void addList(Node root) {
        if (root == null) return;
        addList(root.left);
        list.add(root);
        addList(root.right);
    }
}

//作者：adoring-jepsenvwo
//        链接：https://leetcode.cn/problems/er-cha-sou-suo-shu-yu-shuang-xiang-lian-biao-lcof/solution/javasan-chong-fang-fa-di-gui-zhong-xu-bi-lewz/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。