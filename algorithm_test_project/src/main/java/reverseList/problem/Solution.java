package reverseList.problem;

public class Solution {
    public ListNode reverseList(ListNode head) {

        ListNode pre = null;
        ListNode current = head;
        while (current != null ) {
            ListNode temp = current.next;
            current.next = pre;
            pre = current;
            current = temp;
        }
        return pre;

    }
}
