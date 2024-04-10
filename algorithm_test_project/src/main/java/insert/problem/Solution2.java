package insert.problem;

class Solution2 {
    public Node insert(Node head, int insertVal) {
        Node node = new Node(insertVal);
        if (head == null) {
            node.next = node;
            return node;
        }
        if (head.next == head) {
            head.next = node;
            node.next = head;
            return head;
        }
        Node curr = head, next = head.next;
        while (next != head) {
            //递增
            if (insertVal >= curr.val && insertVal <= next.val) {
                break;
            }
            //递减
            if (curr.val > next.val) {
                //可以满足插入条件,插入curr之后
                if (insertVal > curr.val
                        // 可以满足递增条件 插入next之前
                        || insertVal < next.val) {
                    break;
                }
            }
            curr = curr.next;
            next = next.next;
        }
        curr.next = node;
        node.next = next;
        return head;
    }
}

//作者：LeetCode-Solution
//        链接：https://leetcode.cn/problems/4ueAj6/solution/pai-xu-de-xun-huan-lian-biao-by-leetcode-f566/
//        来源：力扣（LeetCode）
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
