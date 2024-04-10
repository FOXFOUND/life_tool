package deleteDuplicates.problem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



public class Solution {

    public static void main(String[] args) {


    }

    public ListNode deleteDuplicates(ListNode head) {
        // write code here

        List<ListNode> listNodeList = new ArrayList();
        Map<Integer, Integer> nodeSizeMap = new HashMap();
        ListNode temp = head;
        while (temp != null) {
            int value = nodeSizeMap.getOrDefault(temp.val, 0);
            nodeSizeMap.put(temp.val, value + 1);
            temp = temp.next;
        }

        Set<Integer> oneSizeSet = new HashSet();
        nodeSizeMap.forEach((k, v) -> {
            if (v.intValue() == 1) {
                oneSizeSet.add(k);
            }
        });

        temp = head;
        while (temp != null) {
            if (oneSizeSet.contains(temp.val)) {
                listNodeList.add(temp);
            }
            temp = temp.next;

        }

        if (listNodeList.size() == 0) {
            return null;
        }

        for (int i = 0; i < listNodeList.size(); i++) {
            if (i + 1 < listNodeList.size()) {
                listNodeList.get(i).next = listNodeList.get(i + 1);
            }
        }
        listNodeList.get(listNodeList.size() - 1).next = null;

        return listNodeList.get(0);


    }
}
