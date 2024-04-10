package string;

public class StringBuilderTest {
    public static void main(String[] args) {
        String arr = "1,2,3";
        String[] bizArr = arr.split(",");
        for (int i = 0; i < bizArr.length; i++) {
            StringBuilder stringBuilderBiz = new StringBuilder();
            stringBuilderBiz.append("'");
            stringBuilderBiz.append(bizArr[i]);
            stringBuilderBiz.append("',");
            String bizStr = stringBuilderBiz.substring(0, stringBuilderBiz.length() - 1);

        }

    }
}
