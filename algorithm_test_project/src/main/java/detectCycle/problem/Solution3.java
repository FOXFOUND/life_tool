package detectCycle.problem;

import reverseKGroup.problem.ListNode;

import java.util.HashSet;
import java.util.Set;

public class Solution3 {
    public ListNode detectCycle(ListNode head) {
        Set<ListNode> listNode = new HashSet<>();

        while (head != null) {
            if (listNode.contains(head)) {
                return head;
            }
            listNode.add(head);
            head = head.next;

        }

        return null;
    }
}
