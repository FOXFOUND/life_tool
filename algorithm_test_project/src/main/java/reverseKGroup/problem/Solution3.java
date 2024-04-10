package reverseKGroup.problem;

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
class Solution3 {
    public ListNode reverseKGroup(ListNode head, int k) {
        List<Integer> curList = new ArrayList<>();
        ListNode curNode = head;
        while (curNode!=null){
            curList.add(curNode.val);
            curNode = curNode.next;
        }

        int index = 0;
        int[] arr = new int[curList.size()];
        for (Integer cur : curList) {
            arr[index++] = cur;
        }
        index = 0;
        while (index+k <= arr.length){
            //数组交换
            int left = index,right = index + k -1;
            while (right > left){
                arr[right] = arr[right] ^ arr[left];
                arr[left] = arr[right] ^ arr[left];
                arr[right] = arr[right] ^ arr[left];
                right--;
                left++;
            }
            index += k;
        }
        curNode = head;
        index = 0;
        while (curNode!=null){
            curNode.val = arr[index++];
            curNode = curNode.next;
        }
        return head;
    }
}
//
//作者：tender-hermannzvu
//        链接：https://leetcode.cn/problems/reverse-nodes-in-k-group/solution/shi-yong-shu-zu-zuo-by-tender-hermannzvu-xin6/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
