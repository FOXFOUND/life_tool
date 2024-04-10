package deleteMiddle.problem;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public ListNode deleteMiddle(ListNode head) {


        int length = 0;
        List<ListNode> listNodeList = new ArrayList<>();
        //1.获取链表的长度
        ListNode temp = head;
        while (temp != null) {
            listNodeList.add(temp);
            length++;
            temp = temp.next;
        }
        //2.将中间节点的父节点的next指向中间节点的next
        int middle = length / 2;
        int pre = length / 2 - 1;
        //只有一个元素
        if (pre < 0) {
            return null;
        }
        ListNode middleNode = listNodeList.get(middle);
        ListNode preNode = listNodeList.get(pre);
        preNode.next = middleNode.next;
        return head;

    }




}
