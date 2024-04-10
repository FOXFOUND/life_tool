package getIntersectionNode.problem;

import reverseKGroup.problem.ListNode;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        Set<ListNode> exist = new HashSet<>();
        while (headA != null) {
            exist.add(headA);
            headA = headA.next;
        }
        while (headB != null) {
            if (exist.contains(headB)) {
                return headB;
            }
            headB = headB.next;
        }

        return null;

    }
}
