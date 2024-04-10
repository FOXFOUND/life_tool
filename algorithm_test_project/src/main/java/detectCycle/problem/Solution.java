package detectCycle.problem;

import reverseKGroup.problem.ListNode;

public class Solution {
    public ListNode detectCycle(ListNode head) {

        if (head == null || head.next == null) {
            return null;
        }


        ListNode fast = head.next.next;
        ListNode slow = head;

        ListNode position = slow;

        while (fast != null) {

            if (fast == position) {
                return position;
            }

            if (fast == slow) {
                while (true) {

                    if (fast == position) {
                        return position;
                    }
                    position = position.next;
                    ListNode lastFastpotion = fast;
                    fast = fast.next;
                    while (lastFastpotion != fast) {
                        fast = fast.next;
                    }
                }
            }

            fast = fast.next.next;
            slow = slow.next;

        }

        return null;
    }
}
