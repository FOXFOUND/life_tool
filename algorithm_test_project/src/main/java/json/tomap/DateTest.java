package json.tomap;

import com.alibaba.fastjson.JSON;

import java.util.Date;

public class DateTest {
    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(JSON.toJSONString(date));
    }
}
