package thread.test;

public class Solution {

    public static void main(String[] args) throws InterruptedException {




        for (int i = 0; i < 10; i++) {

            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {

                    System.out.println(1);
                }
            });

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        thread1.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(2);

                }
            });

            Thread thread3 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        thread2.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(3);
                }
            });
            thread1.start();
            thread2.start();
            thread3.start();

            Thread.sleep(10L);
        }



    }
}
