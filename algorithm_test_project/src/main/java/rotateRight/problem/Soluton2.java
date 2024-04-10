package rotateRight.problem;

import reverseKGroup.problem.ListNode;

class Solution2 {
    public ListNode rotateRight(ListNode head, int k) {
        if (k == 0 || head == null || head.next == null) {
            return head;
        }
        int n = 1;
        ListNode iter = head;
        while (iter.next != null) {
            iter = iter.next;
            n++;
        }

        // k&n表示尾部节点在当前数组的位置
        //  n - k%n 代表 找到新的尾部节点需要移动的次数
        int add = n - k % n;
        if (add == n) {
            return head;
        }
        iter.next = head;
        //找到新的尾部节点
        while (add-- > 0) {
            iter = iter.next;
        }
        //由于是环,新的尾部节点的next就是新的头节点
        ListNode ret = iter.next;
        iter.next = null;
        return ret;
    }
}
//
//作者：LeetCode-Solution
//        链接：https://leetcode.cn/problems/rotate-list/solution/xuan-zhuan-lian-biao-by-leetcode-solutio-woq1/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。