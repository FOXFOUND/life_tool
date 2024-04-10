package deleteMiddle.problem;

public class Solution3 {
    public ListNode deleteMiddle(ListNode head) {

        ListNode slow = new ListNode(0, head);
        ListNode fast = head;

        int moveSize = 0;

        while (fast != null && fast.next != null) {
            moveSize++;
            fast = fast.next.next;
            slow = slow.next;
        }
        if (moveSize == 0) {
            return null;
        }

        slow.next = slow.next.next;
        return head;
    }
}
