package threadLocalDemo.test;

public class ThreadLocalDemo {
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
        t1.join();
        t2.start();
        t2.join();
    }
}

