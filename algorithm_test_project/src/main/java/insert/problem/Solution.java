package insert.problem;

class Solution {
    public Node insert(Node head, int insertVal) {

        if (head == null) {
            Node n = new Node(insertVal);
            n.next =n;
            return n;
        }


        Node temp = head;
        while (true) {
            Node next = temp.next;
            if (insertVal > temp.val && insertVal < next.val) {
                Node n = new Node(insertVal);
                temp.next = n;
                n.next = next;
                return head;
            } else if (next == temp) {
                Node n = new Node(insertVal);
                n.next = temp;
                temp.next = n;
                return head;
            } else if (next == head) {
                Node n = new Node(insertVal);
                temp.next = n;
                n.next = next;
                return head;

            }
            temp = next;
        }

    }
}
