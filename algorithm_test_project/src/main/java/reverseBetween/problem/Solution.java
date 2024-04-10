package reverseBetween.problem;

import reverseKGroup.problem.ListNode;

public class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode pre = dummy;
        ListNode current = dummy.next;

        ListNode prePre = null;
        int n = 0;
        while (current != null) {

            n++;
            if (n < left || n > right) {
                prePre = pre;
                pre = current;
                current = current.next;
            } else {

                ListNode tempPre = pre;
                while (current != null && n <= right) {
                    n++;
                    ListNode next = current.next;


                    if (n == right) {
                        current.next = pre;
                        prePre.next = current;
                        tempPre.next = next;
                    } else {
                        current.next = pre;
                        pre = current;
                        current = next;
                    }

                }
            }
        }

        return dummy.next;
    }
}
