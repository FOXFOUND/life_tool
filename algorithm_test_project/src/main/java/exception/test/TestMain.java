package exception.test;

public class TestMain {
    public static void main(String[] args) throws Exception {
        try {

            testFunc();
        } catch (Exception e) {
            System.out.println("3");
        }
    }

    private static void testFunc() throws Exception {

        try {
            System.out.println("1");
            throw new Exception();
        } finally {
            System.out.println("2");
        }
    }
}
