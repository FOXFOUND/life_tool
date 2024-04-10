package reverseWords.problem;

import reverseKGroup.problem.ListNode;

public class ListUtil {
    public static ListNode buildLinkedList(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        for (int i = 0; i < nums.length; i++) {
            ListNode cur = new ListNode(nums[i]);
            tail.next = cur;
            tail = tail.next;
        }

        return dummy.next;
    }
}
