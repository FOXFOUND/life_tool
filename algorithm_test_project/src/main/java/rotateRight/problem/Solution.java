package rotateRight.problem;

import reverseKGroup.problem.ListNode;

public class Solution {
    public ListNode rotateRight(ListNode head, int k) {

        ListNode tempDummyHead = head;
        int length = 0;
        while (tempDummyHead != null && tempDummyHead.next != null) {
            length++;
            tempDummyHead = tempDummyHead.next;
        }
        length++;

        ListNode dummyTail = tempDummyHead;
        dummyTail.next = head;
        int index = 0;
        ListNode current = head;
        while (index < k && current != null) {
            index++;
            current = current.next;
        }

        ListNode res = null;
        if (current != null) {
            res = current.next;
        }


        while (current != null && length > 0) {
            length--;
            current = current.next;
        }

        if (current != null) {
            current.next = null;
        }

        return res;


    }
}
