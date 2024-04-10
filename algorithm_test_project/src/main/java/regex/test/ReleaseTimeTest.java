package regex.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReleaseTimeTest {
    public static void main(String[] args) {
        String sourceStr = "【手动跟进】sourceid:16020;系统释放规则-跟进频率：3600，保护时长：3600；当前跟进时间：2022-09-21 10:49:37，绑定时间：2022-09-16 08:30:59，当前时间 + 跟进频率：2032-07-30 10:49:37，绑定时间 + 保护时长：2032-07-25 08:30:59；将要释放的时间取最小值：2032-07-25 08:30:59；";
        String regex = "绑定时间\\S(.*?)\\uff0c";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sourceStr);
        if(matcher.find()) {
            System.out.println(matcher.group());
            String bindingTime = matcher.group(1);
            System.out.println(bindingTime);
        }
    }
}
