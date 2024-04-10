package bit.move;

public class test {
    public static void main(String[] args) {
        int s = 3;
        int k = 0;
        int s1 = s & ~(1 << k);
        int s2 =  -3 & ~(1 << k);
        System.out.println(~(1 << k));
        System.out.println(s1);
        System.out.println(s2);
        System.out.println((1 << k));
        System.out.println(-3& -2);


    }
}
