package fastjson;

import com.alibaba.fastjson.JSON;

public class JsonTestNew {
    public static void main(String[] args) {

        String s = "{\"appId\":26,\"tableName\":\"t_1\",\"uuid\":\"c18dafee-c2d2-4ed5-a586-fa50a39c29c6\",\"time\":1671612444873,\"data\":{\"customer_type\":1,\"job_type\":1,\"contact_name\":\"weihaitao03\",\"record_user_bsp_id\":\"2021052614120005ac2939\",\"tel_type\":\"\",\"create_time\":1671612444838,\"business_level\":1,\"license_type\":1,\"issue_type\":1,\"source_type\":13,\"productline_id\":56,\"career_type_id\":581,\"follow_up_tips\":\"\",\"sub_category_id\":5601,\"create_datetime\":\"2022-12-21 16:47:24\",\"company_name\":\"招聘商机录入（测试测试）【测试门店】【测试门店】\",\"phone_number\":\"13311223366\",\"id\":1605485074457874432,\"job\":\"疫情防控员\",\"record_user\":\"weihaitao03\",\"city_id\":1},\"where\":{\"id\":1605485074457874432},\"env\":3,\"dataSourceName\":\"incall\"}";
        System.out.println(JSON.toJSON(s));
    }
}
