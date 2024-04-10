package addTwoNumbers.problem;

import reverseKGroup.problem.ListNode;

public class Solution2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode preL1 = null;
        ListNode tempL1 = l1;

        while (tempL1 != null) {
            ListNode next = tempL1.next;
            tempL1.next = preL1;
            preL1 = tempL1;
            tempL1 = next;
        }

        ListNode preL2 = null;
        ListNode tempL2 = l2;
        while (tempL2 != null) {
            ListNode next = tempL2.next;
            tempL2.next = preL2;
            preL2 = tempL2;
            tempL2 = next;
        }


        int bit = 0;
        ListNode current = new ListNode(0);
        ListNode pre = current;
        while (preL1 != null && preL2 != null) {
            int currentSum = current.val + preL1.val + preL2.val;
            int currentVal = currentSum % 10;
            bit = currentSum / 10;
            current.val = currentVal;
            current.next = new ListNode(bit);
            current = current.next;
            preL1 = preL1.next;
            preL2 = preL2.next;
        }

        while (preL1 != null) {
            int currentSum = current.val + preL1.val;
            int currentVal = currentSum % 10;
            bit = currentSum / 10;
            current.val = currentVal;
            current.next = new ListNode(bit);
            current = current.next;
            preL1 = preL1.next;
        }

        while (preL2 != null) {
            int currentSum = current.val + preL2.val;
            int currentVal = currentSum % 10;
            bit = currentSum / 10;
            current.val = currentVal;
            current.next = new ListNode(bit);
            current = current.next;
            preL2 = preL2.next;
        }


        ListNode preNew = null;
        ListNode currentNew = pre;

        while (currentNew != null) {
            ListNode next = currentNew.next;
            currentNew.next = preNew;
            preNew = currentNew;
            currentNew = next;
        }

        if (preNew.val == 0) {
            preNew = preNew.next;
        }
        return preNew;


    }
}
