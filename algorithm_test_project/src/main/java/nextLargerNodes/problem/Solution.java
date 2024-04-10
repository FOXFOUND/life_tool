package nextLargerNodes.problem;

import reverseKGroup.problem.ListNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solution {
    public int[] nextLargerNodes(ListNode head) {

        Stack<Integer> upStack = new Stack<>();

        int n = 0;
        ListNode temp = head;
        while (temp != null) {
            temp = temp.next;
            n++;
        }
        int[] res = new int[n];
        int[] valTemp = new int[n];
        int index = 0;
        temp = head;
        while (temp != null) {

            if (upStack.isEmpty()) {
                valTemp[index] = temp.val;
                upStack.push(index);
            } else {

                while (!upStack.isEmpty() && temp.val > valTemp[upStack.peek()]) {
                    Integer tempIndex = upStack.pop();
                    res[tempIndex] = temp.val;
                }
                valTemp[index] = temp.val;
                upStack.push(index);
            }
            temp = temp.next;
            index++;
        }

        return res;
    }
}
