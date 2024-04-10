package operation.test;

public class TestMain {
    public static void main(String[] args) {

        int x = 10 ;
        int y = 20;

        //int k = y ^(x ^ y); //x
        //int k = (x ^ y) ^ (y ^ x);  //0
        //int k = (y ^ (x ^ y) & -(x << y)); //20
        int k = y ^ ((x ^ y) & ~(x << y)); // 10
        System.out.println(k);
    }
}
