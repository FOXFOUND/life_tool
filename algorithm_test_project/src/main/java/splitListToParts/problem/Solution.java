package splitListToParts.problem;

import reverseKGroup.problem.ListNode;

import java.util.Arrays;

public class Solution {
    public ListNode[] splitListToParts(ListNode head, int k) {

        ListNode tempHead = head;
        int n = 0;
        while (tempHead != null) {
            n++;
            tempHead = tempHead.next;
        }

        int[] nums = new int[k];
        ListNode[] res = new ListNode[k];

        int avg = n / k;
        int reHave = n % k;
        Arrays.fill(nums, avg);
        for (int i = 0; i < reHave; i++) {
            nums[i]++;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int index = 0;
        while (head != null) {
            tempHead = new ListNode(0);
            ListNode tempHeadO = tempHead;
            ListNode tempPre = null;
            for (int i = 0; i < nums[index]; i++) {
                tempHead.next = head;
                tempPre = head;
                tempHead = tempHead.next;
                head = head.next;
            }
            if (tempPre != null) {
                tempPre.next = null;
            }
            res[index] = tempHeadO.next;
            index++;
        }

        return res;

    }
}
