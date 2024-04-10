package string.arr.test;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringArr {
    public static void main(String[] args) {
        String str = "{\"phones\":[\"17806171276\",\"13355547342\",\"15854971307\",\"17606408141\",\"13962291034\",\"18353966117\",\"15020953512\",\"15151649831\",\"15263947570\",\"15725198362\",\"13020657798\",\"15253982327\",\"15265136103\",\"15950980785\",\"17673796477\",\"15065997276\",\"18300488660\",\"18300433022\",\"19860996701\",\"17852765662\"]}";
        Map<String, List<String>> stringListMap = (Map<String, List<String>>) JSON.parse(str);
        System.out.println(JSON.toJSONString(stringListMap));
        String s0 = String.join(",",stringListMap.get("phones"));
        System.out.println(s0);
//        String s = String.join(",", stringListMap.get("phones").toArray(new String[stringListMap.get("phones").size()]));
//        System.out.println(s);
//        Map<String,String> map = new HashMap<>();
//        map.put("phones",s);
//        System.out.println(JSON.toJSONString(map));
    }
}
