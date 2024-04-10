package oddEvenList.problem;

public class Solution {
    public ListNode oddEvenList(ListNode head) {

        ListNode first = new ListNode();
        ListNode second = new ListNode();
        ListNode firstHead = first;
        ListNode secondHead = second;

        int num = 0;
        while (head != null) {
            ListNode temp = head;
            head = head.next;
            if (num % 2 == 0) {
                first.next = temp;
                first = first.next;
                first.next = null;
            } else {
                second.next = temp;
                second = second.next;
                second.next = null;
            }
            num++;

        }

        first.next = secondHead.next;
        return firstHead.next;

    }
}
