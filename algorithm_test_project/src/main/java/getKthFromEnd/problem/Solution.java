package getKthFromEnd.problem;

import reverseKGroup.problem.ListNode;

public class Solution {
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode t = head;
        int num = 0;
        while (t != null){
            num++;
            t = t.next;
        }
        int index = 0;
        t = head;
        while (t !=null){
            index++;
            if(num - index + 1 == k ){
                return t;
            }
            t = t.next;
        }

        return null;
    }
}
