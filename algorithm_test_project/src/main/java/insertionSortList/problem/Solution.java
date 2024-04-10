package insertionSortList.problem;

import reverseKGroup.problem.ListNode;

public class Solution {
    public ListNode insertionSortList(ListNode head) {

        ListNode dummy = new ListNode(-10001);
        dummy.next = head;
        ListNode res = dummy;
        ListNode pre = dummy;

        ListNode current = dummy;
        while (current.next != null) {
            ListNode next = current.next;

            if (next != null && current.val > next.val) {
                ListNode nextNext = next.next;
                pre.next = next;
                current.next = nextNext;
                next.next = current;
                pre = current;
            } else {
                pre = current;
                current = current.next;
            }
        }

        return res.next;
    }
}
