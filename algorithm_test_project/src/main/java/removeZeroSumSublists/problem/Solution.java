package removeZeroSumSublists.problem;

import reverseKGroup.problem.ListNode;

import java.util.Stack;

public class Solution {

    //超时
    public ListNode removeZeroSumSublists(ListNode head) {
        Stack<ListNode> stack = new Stack<>();

        ListNode current = head;
        while (current != null) {

            stack.push(current);
            int n = stack.size();
            for (int i = 0; i < n; i++) {
                int sum = 0;
                boolean breakFlag = false;
                for (int j = i; j < n; j++) {
                    sum += stack.get(j).val;

                    if (sum == 0) {
                        int popSize = n - i;
                        breakFlag = true;
                        for (int k = 0; k < popSize; k++) {
                            stack.pop();
                        }
                        break;
                    }
                }

                if (breakFlag) {
                    break;
                }
            }
            current = current.next;
        }

        int n = stack.size();
        for (int i = 0; i < n; i++) {
            if (i + 1 < n) {
                stack.get(i).next = stack.get(i + 1);
            }
        }

        if (!stack.isEmpty()) {
            stack.get(n - 1).next = null;
            return stack.get(0);
        }

        return null;

    }
}
