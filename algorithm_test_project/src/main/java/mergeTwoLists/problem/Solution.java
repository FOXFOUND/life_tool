package mergeTwoLists.problem;

import reverseKGroup.problem.ListNode;

public class Solution {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        ListNode dummy = new ListNode(0);
        ListNode newHead = dummy;
        ListNode left = l1, right = l2;
        while (left != null && right != null) {
            if(left.val <= right.val){
                dummy.next = left;
                left = left.next;
            }else {
                dummy.next = right;
                right = right.next;
            }
            dummy = dummy.next;
        }

        if (left !=null){
            dummy.next = left;
        }
        if (right!=null){
            dummy.next = right;
        }
        return newHead.next;
    }

}
