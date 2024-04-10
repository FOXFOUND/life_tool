package deleteDuplicates.problem;

public class Solution4 {


    public static void main(String[] args) {
        Solution4 solution4 = new Solution4();
        ListNode listNode1 = new ListNode();
        listNode1.val = 1;
        ListNode listNode2 = new ListNode();
        listNode2.val = 2;
        listNode1.next = listNode2;
        ListNode listNode3 = new ListNode();
        listNode3.val = 2;
        listNode2.next = listNode3;
        ListNode listNode = solution4.deleteDuplicates(listNode1);
        while (listNode != null){
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }

    public ListNode deleteDuplicates(ListNode head) {

        ListNode current = new ListNode(-101);
        ListNode nextNode = head;
        ListNode newHead = current;

        while (nextNode != null) {
            if (current.val == nextNode.val) {
                nextNode = nextNode.next;
                continue;
            }

            if (nextNode.next != null
                    && nextNode.val == nextNode.next.val) {

                int val = nextNode.val;
                while (nextNode != null && nextNode.val == val) {
                    nextNode = nextNode.next;
                }
                continue;
            }

            current.next = new ListNode(nextNode.val);
            current = current.next;

        }

        return newHead.next;
    }
}
