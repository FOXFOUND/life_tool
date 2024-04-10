package reverseKGroup.problem;

public class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {


        int num = 0;
        ListNode tempPre = null;
        ListNode tempListNode = head;

        while (tempListNode != null) {
            if (num < k) {
                num++;
                ListNode next = tempListNode.next;
                tempPre = tempListNode;
                tempListNode = next;
                continue;
            }
            num = 0;
            ListNode pre = tempPre;
            ListNode current = tempListNode;
            while (current != null) {
                ListNode next = current.next;
                current.next = pre;
                pre = current;
                current = next;
            }
        }


        return tempListNode;

    }
}
