package url.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class URLTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        String time = "2021-04-20 00:00:00";
//        System.out.println(StandardCharsets.UTF_8.name());
//        System.out.println(StandardCharsets.UTF_8.toString());
//        System.out.println(StandardCharsets.UTF_8.displayName());
//        System.out.println(URLEncoder.encode(time));
//        System.out.println(URLEncoder.encode(time, StandardCharsets.ISO_8859_1.name()));
        System.out.println(time.replace(" ","%20"));
    }
}
