package json.tomap;

public class SplitTest {
    public static void main(String[] args) {
        String split = new String("#&#");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("1");
        stringBuilder.append(split);
        stringBuilder.append("1");
        stringBuilder.append(split);
        stringBuilder.append("1");
        String s  = stringBuilder.toString();
        String [] arr= s.split(split);

        System.out.println(arr.length);
    }
}
