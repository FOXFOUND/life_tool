package deleteDuplicates.problem;

public class Solution2 {

    /**
     * 存在 nextNode.next = nextNode.next.next的问题
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {

        ListNode current = head;
        ListNode nextNode = head;

        while (nextNode != null) {
            if (nextNode.val == current.val) {
                nextNode = nextNode.next;
                continue;
            }
            current.next = nextNode;
            current = current.next;
        }
        return head;

    }
}
