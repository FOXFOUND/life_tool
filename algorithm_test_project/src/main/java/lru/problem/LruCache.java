package lru.problem;

import java.util.HashMap;
import java.util.Map;

class DLinkedNode {
    public String key;
    public String value;
    public DLinkedNode pre;
    public DLinkedNode next;
}

public class LruCache {

    /**
     * 真正缓存数据的容器
     */
    private Map<String, DLinkedNode> cache = new HashMap<>();

    /**
     * 当前缓存中的数据数量
     */
    private int count;

    /**
     * 缓存的容量（最多能存多少个数据KV）
     */
    private int capacity;

    /**
     * 双向链表的头尾节点（数据域key和value都为null）
     */
    private DLinkedNode head, tail;

    /**
     * 唯一构造器，进行初始化
     *
     * @param capacity 最多能保存的缓存项数量
     */
    public LruCache(int capacity) {
        this.count = 0;
        this.capacity = capacity;

        this.head = new DLinkedNode();
        this.tail = new DLinkedNode();

        this.head.pre = null;
        this.head.next = this.tail;

        this.tail.pre = this.head;
        this.tail.next = null;
    }

    /**
     * 从缓存中获取数据
     *
     * @param key 缓存中的key
     * @return 缓存的value
     */
    public String get(String key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return null;
        }

        // 每次访问后，就需要将访问的key对应的节点移到第一个位置（最近访问）
        moveToFirst(node);
        return node.value;
    }

    /**
     * 向缓存中添加数据
     *
     * @param key   元素key
     * @param value 元素value
     */
    public void set(String key, String value) {
        // 先尝试从缓存中获取key对应缓存项（node）
        DLinkedNode existNode = cache.get(key);

        // key对应的数据不存在，则加入缓存
        if (null == existNode) {
            DLinkedNode newNode = new DLinkedNode();
            newNode.key = key;
            newNode.value = value;

            // 放入缓存
            cache.put(key, newNode);
            // 将新加入的节点存入双链表，且放到第一个位置
            addNodeToFirst(newNode);
            count++;

            // 如果加入新的数据后，超过缓存容量，则要进行淘汰
            if (count > capacity) {
                DLinkedNode delNode = delLastNode();
                cache.remove(delNode.key);
                --count; // 淘汰后，数量建议
            }
        } else {
            // key对应的数据已存在，则进行覆盖
            existNode.value = value;
            // 将访问的节点移动到第一个位置（最近访问）
            moveToFirst(existNode);
        }
    }

    /**
     * 添加新节点到双向链表（新加入的节点位于第一个位置）
     *
     * @param newNode 新加入的节点
     */
    private void addNodeToFirst(DLinkedNode newNode) {
        newNode.next = head.next;
        head.next.pre = newNode;

        newNode.pre = head;
        head.next = newNode;
    }

    /**
     * 删除双向链表的尾节点（淘汰节点）
     *
     * @return 被删除的节点
     */
    private DLinkedNode delLastNode() {
        DLinkedNode last = tail.pre;
        delNode(last);
        return last;
    }

    /**
     * 将节点移动到双向链表的第一个位置
     *
     * @param node 需要移动的节点
     */
    private void moveToFirst(DLinkedNode node) {
        // 将节点移动到头部，有两种方式：
        delNode(node);
        addNodeToFirst(node);
    }

    /**
     * 删除双链表的节点（直接连接前后节点)
     *
     * @param node 要删除的节点
     */
    private void delNode(DLinkedNode node) {
        DLinkedNode pre = node.pre;
        DLinkedNode post = node.next;

        pre.next = post;
        post.pre = pre;
    }
}