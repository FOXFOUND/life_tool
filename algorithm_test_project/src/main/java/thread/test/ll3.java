package thread.test;



import java.util.concurrent.locks.ReentrantLock;

//lock
public class ll3 {
    private int num;
    //可重入锁是可重入独占锁,默认是非公平的,可能产生饥饿问题,当前场景使用公平锁可以加快收敛
    private ReentrantLock lock = new ReentrantLock(true);

    public void printabc(String name, int target) throws InterruptedException {
        for (int i = 0; i < 10; ) {
            // 利用lock进行临界区控制,保证只有一个线程可以执行,利用for循环进行轮询和轮次控制
            lock.lock();
            if (num % 3 == target) {
                num++;
                i++;
                System.out.println(i);
                System.out.println(name);
            }
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ll3 lunliu = new ll3();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lunliu.printabc("A", 0);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lunliu.printabc("B", 1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lunliu.printabc("C", 2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
        System.out.println("ok");
    }
}
