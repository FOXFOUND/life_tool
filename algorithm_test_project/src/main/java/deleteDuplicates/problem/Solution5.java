package deleteDuplicates.problem;

public class Solution5 {

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode3 = new ListNode(3);
        ListNode listNode32 = new ListNode(3);
        listNode1.next = listNode3;
        listNode3.next = listNode32;
        Solution5 solution5 = new Solution5();
        solution5.deleteDuplicates(listNode1);
    }

    public ListNode deleteDuplicates(ListNode head) {

        ListNode dummy = new ListNode();
        ListNode newHead = dummy;
        ListNode next = head;
        while (next != null) {
            if (next.next != null && next.val == next.next.val) {
                int x = next.val;
                while (next != null && next.val == x) {
                    next = next.next;
                }
                continue;
            }
            dummy.next = next;
            dummy = dummy.next;
            next = next.next;

        }

        dummy.next = null;

        return newHead.next;
    }
}
