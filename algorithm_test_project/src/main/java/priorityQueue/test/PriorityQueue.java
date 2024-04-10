package priorityQueue.test;

import java.util.ArrayList;

public class PriorityQueue<T extends Comparable<T>> {
    private ArrayList<T> queue = new ArrayList<T>();

    public void add(T element) {
        int index = 0;
        for (T item : queue) {
            if (item.compareTo(element) > 0) {
                break;
            }
            index++;
        }
        queue.add(index, element);
    }

    public T poll() {
        if (queue.isEmpty()) {
            return null;
        }
        return queue.remove(0);
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();

        // 向队列中添加元素
        queue.add(6);
        queue.add(1);
        queue.add(8);
        queue.add(2);

        // 输出队列中的元素
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
    }
}
