package LRUCache.problem;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    class Node {

        public Node pre;
        public Node next;
        public int val;
        public int key;

        public Node() {

        }

        public Node(Node pre, Node next, int val) {
            this.next = next;
            this.pre = pre;
            this.val = val;

        }

    }


    int capacity = 0;
    int useCapacity = 0;

    Node dummyHead = null;
    Node dummyTail = null;

    Map<Integer, Node> keyMap = null;

    public LRUCache(int capacity) {
        this.dummyHead = new Node();
        this.dummyTail = new Node();
        this.capacity = capacity;
        this.useCapacity = 0;
        this.keyMap = new HashMap<>();

        dummyHead.next = dummyTail;
        dummyTail.pre = dummyHead;
    }

    public int get(int key) {
        Node node = keyMap.get(key);
        if (node == null) {
            return -1;
        } else {
            addHead(node);
            return node.val;
        }
    }

    public void put(int key, int value) {

        Node node = keyMap.get(key);
        if (node == null) {
            Node newNode = new Node();
            newNode.val = value;
            newNode.key = key;
            keyMap.put(key, newNode);
            addHead(newNode);
            useCapacity++;
            if (useCapacity > capacity) {
                Node removeNode = removeTail();
                useCapacity--;
                keyMap.put(removeNode.key, null);

            }
        } else {
            node.val = value;
            addHead(node);
        }
    }


    private Node removeTail() {
        Node tailPre = dummyTail.pre;
        Node tailPrePre = tailPre.pre;
        tailPrePre.next = dummyTail;
        dummyTail.pre = tailPrePre;
        return tailPre;
    }

    private void addHead(Node newNode) {

        Node newNodeNext = newNode.next;
        Node newNodePre = newNode.pre;

        if (newNodeNext != null) {
            newNodeNext.pre = newNodePre;
        }
        if (newNodePre != null) {
            newNodePre.next = newNodeNext;
        }

        Node headNext = dummyHead.next;
        dummyHead.next = newNode;
        newNode.next = headNext;

        headNext.pre = newNode;
        newNode.pre = dummyHead;


    }
}

