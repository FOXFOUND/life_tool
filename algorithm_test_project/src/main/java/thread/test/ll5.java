package thread.test;


import java.util.concurrent.Semaphore;

//semaphore
public class ll5 {
    static Semaphore s1 = new Semaphore(1);
    static Semaphore s2 = new Semaphore(0);
    static Semaphore s3 = new Semaphore(0);

    public void printabc(String name, Semaphore c, Semaphore next) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            //利用信号量
            c.acquire();
            System.out.println(name);
            next.release();
        }
    }

    public static void main(String[] args) {
        ll5 lunliu = new ll5();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            lunliu.printabc("A", s1, s2);
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
                            lunliu.printabc("B", s2, s3);
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
                            lunliu.printabc("C", s3, s1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }
}