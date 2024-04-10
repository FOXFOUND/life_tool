package reverseKGroup.problem;

import com.alibaba.fastjson.JSON;
import reverseWords.problem.ListUtil;

import java.util.ArrayList;
import java.util.List;

public class Solution2 {

    public static void main(String[] args) {
        Solution2 solution2 = new Solution2();
        int[] nums = new int[]{1, 2, 3, 4, 5};
        System.out.println(JSON.toJSONString(nums));
        int k = 2;
        solution2.reverseKGroup(ListUtil.buildLinkedList(nums), 2);
    }


    public ListNode reverseKGroup(ListNode head, int k) {

        List<ListNode> list = new ArrayList<>();
        while (head != null) {
            list.add(head);
            head = head.next;
        }

        int n = list.size();
        for (int i = 0; i + k - 1 < n; i = i + k) {


            for (int j = 0; j < k / 2; j++) {

                int left = j + i;
                int right = i + k - j - 1;
                ListNode temp = list.get(left);
                list.set(left, list.get(right));
                list.set(right, temp);
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
