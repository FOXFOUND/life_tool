package reorderList.problem;

import reverseKGroup.problem.ListNode;

public class Solution {
    public void reorderList(ListNode head) {

        int n = 0;
        ListNode tempHead = head;
        while (tempHead != null) {
            n++;
            tempHead = tempHead.next;
        }
        ListNode[] listNodes = new ListNode[n];
        tempHead = head;
        int index = 0;
        while (tempHead != null) {
            listNodes[index] = tempHead;
            tempHead = tempHead.next;
            index++;
        }
        int temp = n / 2;
        ListNode dummy = new ListNode(0);
        ListNode tempDummy = dummy;
        for (int i = 0; i < temp; i++) {
            tempDummy.next = listNodes[i];
            listNodes[i].next = listNodes[n - 1 - i];
            tempDummy = listNodes[n - 1 - i];
        }
        listNodes[temp].next = null;
        head = dummy.next;
    }
}
