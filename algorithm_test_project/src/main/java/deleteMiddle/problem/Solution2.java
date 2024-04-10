package deleteMiddle.problem;

class Solution2 {
    public ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode middle = middleNode(head);
        middle.next = middle.next.next;
        return head;
    }
    public ListNode middleNode(ListNode head) {
        //需要将head增加一个虚拟的前置节点,那么当移除的时候,middle才会指向中间节点的父节点
        ListNode dummp = new ListNode(0, head);
        ListNode slow = dummp;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}

//作者：glodknife
//        链接：https://leetcode.cn/problems/delete-the-middle-node-of-a-linked-list/solution/tu-jie-jing-dian-si-lu-zhi-kuai-man-zhi-6o21w/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
