package map.test;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    public static void main(String[] args) {
        Map map = new HashMap<>();
        map.put("oppId","845C71B0-6F14-4A73-96F5-CE953A029A19");
        map.put("pl",56);
        map.put("pl2","56");
        System.out.println(JSON.toJSONString(map));
    }
}
