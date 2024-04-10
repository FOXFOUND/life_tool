package direct;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetCityCode {
    public static void main(String[] args) {
        String str = "[\n" +
                "        {\n" +
                "            \"name\": \"北京\",\n" +
                "            \"id\": 1\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"大连\",\n" +
                "            \"id\": 147\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"沈阳\",\n" +
                "            \"id\": 188\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"哈尔滨\",\n" +
                "            \"id\": 202\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"长春\",\n" +
                "            \"id\": 319\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"青岛\",\n" +
                "            \"id\": 122\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"济南\",\n" +
                "            \"id\": 265\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"石家庄\",\n" +
                "            \"id\": 241\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"太原\",\n" +
                "            \"id\": 740\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"苏州\",\n" +
                "            \"id\": 5\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"无锡\",\n" +
                "            \"id\": 93\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"南京\",\n" +
                "            \"id\": 172\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"杭州\",\n" +
                "            \"id\": 79\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"合肥\",\n" +
                "            \"id\": 837\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"广州\",\n" +
                "            \"id\": 3\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"深圳\",\n" +
                "            \"id\": 4\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"东莞\",\n" +
                "            \"id\": 413\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"福州\",\n" +
                "            \"id\": 304\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"厦门\",\n" +
                "            \"id\": 606\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"南宁\",\n" +
                "            \"id\": 845\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"重庆\",\n" +
                "            \"id\": 37\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"成都\",\n" +
                "            \"id\": 102\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"武汉\",\n" +
                "            \"id\": 158\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"郑州\",\n" +
                "            \"id\": 342\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"长沙\",\n" +
                "            \"id\": 414\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"上海\",\n" +
                "            \"id\": 2\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"天津\",\n" +
                "            \"id\": 18\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"惠州\",\n" +
                "            \"id\": 722\n" +
                "        }\n" +
                "    ]";

        List<JSONObject> jsonObjectList = JSON.parseArray(str,JSONObject.class);
        List<Integer> cityId = new ArrayList<>();
        for (JSONObject jsonObject : jsonObjectList){
            System.out.println(jsonObject.get("id"));
            cityId.add(jsonObject.getIntValue("id"));
        }
        System.out.println(JSON.toJSONString(cityId));
        System.out.println(cityId.size());
    }
}
