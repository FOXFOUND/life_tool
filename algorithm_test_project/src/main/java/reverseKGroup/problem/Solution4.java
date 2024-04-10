package reverseKGroup.problem;

import java.util.ArrayList;
import java.util.List;

public class Solution4 {
    public ListNode reverseKGroup(ListNode head, int k) {

        List<ListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }

        int n = list.size();
        for (int i = 0; i + k - 1 < n; i = i + k) {

            for (int j = i; j < i + k / 2; j++) {

                ListNode temp = list.get(i);
                list.set(j, list.get(i + k - j - 1));
                list.set(i + k - j - 1, temp);
            }
        }

        for (int i = 0; i < n; i++) {
            if (i + 1 < n) {
                list.get(i).next = list.get(i + 1);
            }
        }
        list.get(n - 1).next = null;
        return list.get(0);

    }
}
