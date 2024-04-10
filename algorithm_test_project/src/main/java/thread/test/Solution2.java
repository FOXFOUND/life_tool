package thread.test;


public class Solution2 {
    private int num;


    public void printabc(String name, int target) throws InterruptedException {
        for (int i = 0; i < 10; ) {
            //随机进入
            if (num % 3 == target) {
                num++;
                i++;
                //System.out.println(i);
                System.out.println(name);
            }

        }
    }

    public static void main(String[] args) {
        Solution2 lunliu = new Solution2();
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
