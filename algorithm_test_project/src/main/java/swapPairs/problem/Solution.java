package swapPairs.problem;

import reverseKGroup.problem.ListNode;

public class Solution {
    public ListNode swapPairs(ListNode head) {

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode current = dummy.next;
        ListNode pre = dummy;
        while (current != null) {
            ListNode next = current.next;

            if (next != null) {
                ListNode nextNext = next.next;
                pre.next = next;
                next.next = current;
                current.next = nextNext;
                pre = current;
                current = current.next;
            } else {
                pre = current;
                current = current.next;
            }
        }
        return dummy.next;
    }
}
