package fastjson;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class JsonTest {
    public static void main(String[] args) {
//        List<Integer>  integerList = new ArrayList<>();
//        integerList.add(1);
//        integerList.add(2);
//        System.out.println(JSON.toJSONString(integerList));
        String testStr = "[1]";
        List<Integer> cityIdList = null;
        cityIdList = JSON.parseArray(testStr, Integer.class);
        System.out.println(JSON.toJSONString(cityIdList));
        System.out.println(cityIdList.contains(new Integer(1)));
    }
}
