package json.tomap;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class JsonToMap {
    public static void main(String[] args) {
        String str = "{\"myperson\":{\"age\":\"123age\",\"name\":\"123name\",\"student\":{\"studentNo\":\"123\"}}}";
        Map<String,Person> map = new HashMap<>();
        map =( Map<String,Person>) JSON.parse(str);
        System.out.println(JSON.toJSONString(map));
        System.out.println("ok");
    }
}
