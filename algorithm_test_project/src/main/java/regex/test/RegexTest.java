package regex.test;

import java.util.regex.Pattern;

public class RegexTest {
    public static void main(String[] args) {
        String sourceStr = "/testPre/testPre/getUserInfo";
        String regex = "^/testPre/.*";
//        String res = sourceStr.replaceFirst(regex,"/");
//        System.out.println(res);
        Pattern pattern = Pattern.compile(regex);
        System.out.println(pattern.matcher(sourceStr).matches());
    }
}
