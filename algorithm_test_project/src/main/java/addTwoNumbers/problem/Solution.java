package addTwoNumbers.problem;

import reverseKGroup.problem.ListNode;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 * int val;
 * ListNode next;
 * ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode dummyL1 = new ListNode(0);
        ListNode dummyL2 = new ListNode(0);
        dummyL1.next = l1;
        dummyL2.next = l2;

        ListNode preL1 = dummyL1;
        ListNode preL2 = dummyL2;
        ListNode currentL1 = preL1.next;
        ListNode currentL2 = preL2.next;

        ListNode dummyCurrent = new ListNode(0);
        ListNode dummyCurrentPre = new ListNode(0);
        dummyCurrentPre.next = dummyCurrent;


        int above = 0;
        while (currentL1 != null && currentL2 != null) {

            int sum = currentL1.val + currentL2.val + dummyCurrent.val;

            int dummyCurrentVal = sum;
            if (sum >= 10) {
                dummyCurrentVal = dummyCurrentVal - 10;
                above = 1;
            } else {
                above = 0;
            }
            dummyCurrent.val = dummyCurrentVal;
            dummyCurrent.next = new ListNode(above);
            dummyCurrent = dummyCurrent.next;
            currentL1 = currentL1.next;
            currentL2 = currentL2.next;
        }

        while (currentL1 != null) {
            int sum = currentL1.val + dummyCurrent.val;
            int dummyCurrentVal = sum;
            if (sum >= 10) {
                dummyCurrentVal = dummyCurrentVal - 10;
                above = 1;
            } else {
                above = 0;
            }
            dummyCurrent.val = dummyCurrentVal;
            dummyCurrent.next = new ListNode(above);
            dummyCurrent = dummyCurrent.next;
            currentL1 = currentL1.next;

        }


        while (currentL2 != null) {
            int sum = currentL2.val + dummyCurrent.val;
            int dummyCurrentVal = sum;
            if (sum >= 10) {
                dummyCurrentVal = dummyCurrentVal - 10;
                above = 1;
            } else {
                above = 0;
            }
            dummyCurrent.val = dummyCurrentVal;
            dummyCurrent.next = new ListNode(above);
            dummyCurrent = dummyCurrent.next;
            currentL2 = currentL2.next;
        }

        ListNode tempCurrent = dummyCurrentPre.next;
        ListNode tempCurrentPre = dummyCurrentPre;
        while (tempCurrent != null) {
            ListNode next = tempCurrent.next;
            if (next == null && tempCurrent.val == 0) {
                tempCurrentPre.next = null;
                break;
            }

            tempCurrentPre = tempCurrent;
            tempCurrent = tempCurrent.next;

        }

        return dummyCurrentPre.next;

        // ListNode tempDummyCurrentPre = null;
        // ListNode tempCurrent  = dummyCurrentPre.next;
        // while(tempCurrent != null){
        //     ListNode next = tempCurrent.next;
        //     tempCurrent.next = tempDummyCurrentPre;
        //     tempDummyCurrentPre = tempCurrent;
        //     tempCurrent = next;
        // }

        // return tempDummyCurrentPre.next;


    }
}