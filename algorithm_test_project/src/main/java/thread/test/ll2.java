package thread.test;


//join
public class ll2 {
    static class p implements Runnable {
        Thread t;

        public p(Thread t) {
            this.t = t;
        }

        @Override
        public void run() {
            if (t != null) {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName());
            } else {
                System.out.println(Thread.currentThread().getName());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        //相当于10 组 3个线程,
        for (int i = 0; i < 10; i++) {
            Thread a = new Thread(new p(null), "A");
            Thread b = new Thread(new p(a), "B");
            Thread c = new Thread(new p(b), "C");
            a.start();
            b.start();
            c.start();
            Thread.sleep(10);
        }
    }
}
