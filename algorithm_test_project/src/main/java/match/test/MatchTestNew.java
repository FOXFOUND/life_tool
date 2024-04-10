package match.test;

import java.util.regex.Pattern;

public class MatchTestNew {
    public static void main(String[] args) {
        String str = "https://xcrm.58corp.com/apaasChart/sssss";
        boolean a = Pattern.matches("^https?:\\/\\/xcrm.58corp.com/apaasChart/.*", str);
        System.out.println(a);
    }
}
