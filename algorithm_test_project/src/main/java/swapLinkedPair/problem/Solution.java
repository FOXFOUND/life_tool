package swapLinkedPair.problem;

import reverseKGroup.problem.ListNode;

import java.util.*;

/*
 * public class ListNode {
 *   int val;
 *   ListNode next = null;
 *   public ListNode(int val) {
 *     this.val = val;
 *   }
 * }
 */

public class Solution {
    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param head ListNode类
     * @return ListNode类
     */
    public ListNode swapLinkedPair(ListNode head) {


        // write code here
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prePre = dummy;
        ListNode pre = dummy.next;
        ListNode current = pre.next;
        while (current != null && pre != null && prePre != null) {

            ListNode next = current.next;
            System.out.println(prePre.val);
            System.out.println(pre.val);
            System.out.println(current.val);


            prePre.next = current;
            current.next = pre;
            pre.next = next;


            prePre = pre;
            pre = next;
            if (pre != null) {
                current = pre.next;
            }


        }

        return dummy.next;

    }
}