package regex.test;

import org.apache.commons.lang3.StringUtils;

public class ApacheRepalceTest {
    public static void main(String[] args) {
        String sourceStr = "/testPre/testPre/getUserInfo/sdsdsd/dsdsdsdd/2/23232//54/545/4/5545/4545454/2323232323//544545////54545454545454/http://iwiki.58corp.com/wiki/baseexperiencetech/view/Main/58%E7%94%A8%E6%88%B7%E5%B9%B3%E5%8F%B0%E9%83%A8/passport/%E8%B4%A6%E5%8F%B7%E7%9B%B8%E5%85%B3%E6%96%87%E6%A1%A3/%E6%9D%83%E9%99%90%E7%B3%BB%E7%BB%9F/%E6%9D%83%E9%99%90%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86%E7%B1%BB%E6%8E%A5%E5%8F%A3/";
        String regex = "^/testPre/";
        String replaceStr = "/";
        for (int i = 0; i < 100; i++) {
            long t1 = System.currentTimeMillis();
            String str1 = sourceStr.replaceFirst(regex, replaceStr);
            System.out.println(str1);
            long t2 = System.currentTimeMillis();
            System.out.println("t2 -t1   " + (t2 - t1));
            long t3 = System.currentTimeMillis();
            String str2 = StringUtils.replacePattern(sourceStr, regex, replaceStr);
            System.out.println(str2);
            long t4 = System.currentTimeMillis();
            System.out.println("t4- t3   " + (t4 - t3));
        }

    }
}
