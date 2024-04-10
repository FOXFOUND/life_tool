package threadLocalDemo.test;

public class ThreadLocalDemo2 {
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) throws InterruptedException {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    int value = threadLocal.get(); // 获取当前线程的变量副本
                    value++; // 修改变量副本的值
                    threadLocal.set(value); // 将修改后的变量副本保存回 ThreadLocalMap
                    System.out.printf("[%s] - value: %d\n", Thread.currentThread().getName(), value);
                }
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        /**
         * [Thread-1] - value: 1
         * [Thread-2] - value: 1
         * [Thread-1] - value: 2
         * [Thread-1] - value: 3
         * [Thread-1] - value: 4
         * [Thread-1] - value: 5
         * [Thread-2] - value: 2
         * [Thread-2] - value: 3
         * [Thread-2] - value: 4
         * [Thread-2] - value: 5
         */
    }
}

