package deleteDuplicates.problem;

public class ListNode {
    int val;
    ListNode next = null;

    public ListNode(int i, ListNode head) {
        this.val = i;
        this.next = head;
    }

    public ListNode() {

    }

    public ListNode(int val) {
        this.val = val;
    }
}