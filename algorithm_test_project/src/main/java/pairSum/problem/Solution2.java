package pairSum.problem;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution2 {
    public int pairSum(ListNode head) {
        // 使用快慢指针，找出链表的前部分和后半部分
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 反转链表后半部分
        ListNode curr = slow;
        ListNode pre = null;
        while (curr != null) {
            ListNode temp = curr.next;
            curr.next = pre;
            pre = curr;
            curr = temp;
        }
        // 同时遍历链表的前半部分和反转后的后半部分，对两部分节点求和
        int result = 0;
        while (head != null && pre != null) {
            result = Math.max(result, head.val + pre.val);
            head = head.next;
            pre = pre.next;
        }
        return result;
    }
}

//作者：rang-dan-dan-fei
//        链接：https://leetcode.cn/problems/maximum-twin-sum-of-a-linked-list/solution/zhuan-listzai-qiu-he-kuai-man-zhi-zhen-f-cwym/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
