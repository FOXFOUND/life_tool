package thread.test;


//三个线程轮流打印A B C 打印10次 - synchronized
public class Lunliu {
    private int num;
    private Object lock = new Object();

    public void printabc(String name, int target) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            //利用synchronized进行临界区的控制,利用num%3进行判断是否执行当前线程,不执行的话,继续wait进行释放锁,可以执行线程继续执行
            synchronized (lock) {
                while (num % 3 != target) {
                    lock.wait();
                }
                System.out.print(name);
                num++;
                lock.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        Lunliu lunliu = new Lunliu();
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
        ) {
        }.start();
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
        ) {
        }.start();
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
        ) {
        }.start();
    }
}
