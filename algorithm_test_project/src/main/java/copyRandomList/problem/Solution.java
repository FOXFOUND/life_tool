package copyRandomList.problem;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {
    public Node copyRandomList(Node head) {

        Node newHead = new Node(0);
        Node preHead = new Node(0);
        preHead.next = head;
        Node currentHead = newHead;

        int num = 0;
        List<Node> newNodeList = new ArrayList<>();
        Map<Node, Integer> positionMap = new HashMap<>();
        while (preHead != null) {
            Node next = preHead.next;

            if (next == null) {
                break;
            }
            positionMap.put(next, num);
            currentHead.next = new Node(next.val);
            newNodeList.add(currentHead.next);
            preHead = next;
            num++;
            currentHead = currentHead.next;
        }


        preHead = new Node(0);
        preHead.next = head;
        currentHead = newHead;
        while (preHead != null) {
            Node random = preHead.random;
            if (random != null) {
                int position = positionMap.get(random);
                Node newRandom = newNodeList.get(position);
                currentHead.random = newRandom;

            } else {
                currentHead.random = null;
            }
            preHead = preHead.next;
            currentHead = currentHead.next;
        }

        return newHead.next;

    }
}
