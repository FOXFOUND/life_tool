package fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.ParserConfig;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FT {
    public static void main(String[] args) throws InterruptedException {
        FT ft = new FT();
         Method[] methods = ft.getClass().getMethods();
         Type type = null;
        for (int i = 0; i < methods.length; i++) {
            if(methods[i].getGenericReturnType().getTypeName().equals("fastjson.ResultModel<java.util.List<fastjson.PhoneInfoDTO>>")){
                type =methods[i].getGenericReturnType();
                System.out.println("ok");
            }

        }
        final JSONObject obj = new JSONObject();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
       // Object proxy = Proxy.newProxyInstance(loader, new Class<?>[] { clazz }, obj);
        ParserConfig.getGlobalInstance().setSafeMode(true);
        //String str = "{\"productLine\":56,\"isBlock\":false,\"encryPhone\":\"XmeZWRX4eCmctOpupGnwcw==\",\"riskTagMap\":{\"客户表示被骚扰\":0,\"客户表示打错了\":0,\"客户表示不需要\":0,\"客户表示电话过多\":0,\"用户强烈反感再联系\":0,\"客户辱骂销售\":0,\"客户表示将去投诉\":0,\"发生辱骂\":0,\"不要再打电话\":0,\"销售工作作假\":0,\"客户快速挂断\":0,\"销售辱骂客户\":0},\"phone\":\"18222312809\",\"level\":0,\"count\":0,\"oppId\":\"6b17913f-04fc-45be-9b4a-c8fdccc4dbbe\",\"riskScore\":0.0,\"highRisk\":false,\"status\":0,\"errorPhoneDesc\":\"\"}";
        String str = "{\n" +
                "    \"data\":[{\n" +
                "\t\"count\": 0,\n" +
                "\t\"errorPhoneDesc\": \"\",\n" +
                "\t\"highRisk\": false,\n" +
                "\t\"isBlock\": false,\n" +
                "\t\"level\": 0,\n" +
                "\t\"oppId\": \"6b17913f-04fc-45be-9b4a-c8fdccc4dbbe\",\n" +
                "\t\"phone\": \"18222312809\",\n" +
                "\t\"productLine\": 56,\n" +
                "\t\"riskScore\": 0.0,\n" +
                "\t\"riskTagMap\": {\n" +
                "\t\t\"客户表示被骚扰\": 0,\n" +
                "\t\t\"客户表示打错了\": 0,\n" +
                "\t\t\"客户表示不需要\": 0,\n" +
                "\t\t\"客户表示电话过多\": 0,\n" +
                "\t\t\"用户强烈反感再联系\": 0,\n" +
                "\t\t\"客户辱骂销售\": 0,\n" +
                "\t\t\"客户表示将去投诉\": 0,\n" +
                "\t\t\"发生辱骂\": 0,\n" +
                "\t\t\"不要再打电话\": 0,\n" +
                "\t\t\"销售工作作假\": 0,\n" +
                "\t\t\"客户快速挂断\": 0,\n" +
                "\t\t\"销售辱骂客户\": 0\n" +
                "\t},\n" +
                "\t\"status\": 0\n" +
                "}]\n" +
                "}\n";
//        for (int i = 0; i <100 ; i++) {
//            new Thread(()->{
//
//            }).run();
//
//        }
        ResultModel<List<PhoneInfoDTO>> resultModel = JSON.parseObject(str,type);
        //Map<String, PhoneInfoDTO> phoneStatusMap = resultModel.getData().stream().collect(Collectors.toMap(PhoneInfoDTO::getPhone, item->item, (p, n) -> n));
        System.out.println(JSON.toJSONString(resultModel));
        //Thread.sleep(10000);

    }

    public  ResultModel<List<PhoneInfoDTO>> getInfoByPhones (){
        return null;
    }
}
