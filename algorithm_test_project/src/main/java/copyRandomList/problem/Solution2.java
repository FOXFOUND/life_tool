package copyRandomList.problem;

import java.util.HashMap;
import java.util.Map;

class Solution2 {
    public Node copyRandomList(Node head) {
        Node t = head;
        Node dummy = new Node(-10010), cur = dummy;
        Map<Node, Node> map = new HashMap<>();
        while (head != null) {
            Node node = new Node(head.val);
            map.put(head, node);
            cur.next = node;
            cur = cur.next;
            head = head.next;
        }
        cur = dummy.next;
        head = t;
        while (head != null) {
            cur.random = map.get(head.random);
            cur = cur.next;
            head = head.next;
        }
        return dummy.next;
    }
}
//
//作者：AC_OIer
//        链接：https://leetcode.cn/problems/fu-za-lian-biao-de-fu-zhi-lcof/solution/by-ac_oier-6atv/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
