package pairSum.problem;

import java.util.ArrayList;
import java.util.List;

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
class Solution {
    public int pairSum(ListNode head) {
        // 链表转换成List
        List<Integer> list = new ArrayList<>();
        ListNode p = head;
        while (p != null) {
            list.add(p.val);
            p = p.next;
        }
        int result = 0;
        int n = list.size();
        for (int i = 0; i < n/2; i++) {
            int v = list.get(i) + list.get(n-1-i);
            result = Math.max(result, v);
        }
        return result;
    }
}

//作者：rang-dan-dan-fei
//        链接：https://leetcode.cn/problems/maximum-twin-sum-of-a-linked-list/solution/zhuan-listzai-qiu-he-kuai-man-zhi-zhen-f-cwym/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。