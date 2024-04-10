package json.tomap;

import com.alibaba.fastjson.JSON;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class DateNewTest {
    public static void main(String[] args) {
        String s = "[{\n" +
                "\t\"basicLeadId\": \"17340983847913\",\n" +
                "\t\"cityId\": \"13ce2c1f-6ef3-e211-93ee-d4ae529a94fc\",\n" +
                "\t\"contactId\": \"85c2a096-b185-4cbb-9c92-5e4bfbd2e062\",\n" +
                "\t\"contactType\": 0,\n" +
                "\t\"createdById\": \"f3373c89-ae88-4218-878c-d229888019f9\",\n" +
                "\t\"createdOn\": \"2019-06-18 20:42:19\",\n" +
                "\t\"custEntityId\": \"dc98e2c5-c5bc-4a8a-b16e-2a400c74870e\",\n" +
                "\t\"fullName\": \"张進一\",\n" +
                "\t\"genderCode\": 3,\n" +
                "\t\"id\": 1140962949347717121,\n" +
                "\t\"isDecisionMaker\": 3,\n" +
                "\t\"isDisabled\": 0,\n" +
                "\t\"isSalesMan\": 0,\n" +
                "\t\"mobile\": \"18963037675\",\n" +
                "\t\"mobileFlag\": 1,\n" +
                "\t\"modifiedById\": \"f3373c89-ae88-4218-878c-d229888019f9\",\n" +
                "\t\"modifiedOn\": \"2019-06-18 20:42:19\",\n" +
                "\t\"oppId\": \"6ebd85fd-35f1-430d-9b31-d941610d88fd\",\n" +
                "\t\"productLine\": 55,\n" +
                "\t\"sourceCode\": 0,\n" +
                "\t\"state\": 1,\n" +
                "\t\"userId\": 0\n" +
                "}, {\n" +
                "\t\"basicLeadId\": \"17340983847913\",\n" +
                "\t\"cityId\": \"13ce2c1f-6ef3-e211-93ee-d4ae529a94fc\",\n" +
                "\t\"contactId\": \"3130413a-c087-45d5-84b8-704d967b3b7f\",\n" +
                "\t\"contactType\": 0,\n" +
                "\t\"createdById\": \"f3373c89-ae88-4218-878c-d229888019f9\",\n" +
                "\t\"createdOn\": \"2019-06-18 20:42:19\",\n" +
                "\t\"custEntityId\": \"dc98e2c5-c5bc-4a8a-b16e-2a400c74870e\",\n" +
                "\t\"emailAddress\": \"\",\n" +
                "\t\"fullName\": \"张先生\",\n" +
                "\t\"genderCode\": 0,\n" +
                "\t\"id\": 1140962949356109825,\n" +
                "\t\"isDecisionMaker\": 0,\n" +
                "\t\"isDisabled\": 0,\n" +
                "\t\"isSalesMan\": 0,\n" +
                "\t\"jobTitle\": \"\",\n" +
                "\t\"mobile\": \"15710844966\",\n" +
                "\t\"mobileFlag\": 1,\n" +
                "\t\"modifiedById\": \"f3373c89-ae88-4218-878c-d229888019f9\",\n" +
                "\t\"modifiedOn\": \"2019-06-18 20:42:19\",\n" +
                "\t\"oppId\": \"6ebd85fd-35f1-430d-9b31-d941610d88fd\",\n" +
                "\t\"productLine\": 55,\n" +
                "\t\"sourceCode\": 0,\n" +
                "\t\"state\": 1,\n" +
                "\t\"userId\": 0\n" +
                "}, {\n" +
                "\t\"basicLeadId\": \"17340983847913\",\n" +
                "\t\"cityId\": \"13ce2c1f-6ef3-e211-93ee-d4ae529a94fc\",\n" +
                "\t\"contactId\": \"1e55f7b9-fb62-4c42-9cdd-1b6f10805119\",\n" +
                "\t\"contactType\": 0,\n" +
                "\t\"createdById\": \"8203bf35-0756-4290-8087-b8c948534a1e\",\n" +
                "\t\"createdOn\": \"2019-06-18 20:42:19\",\n" +
                "\t\"custEntityId\": \"dc98e2c5-c5bc-4a8a-b16e-2a400c74870e\",\n" +
                "\t\"fullName\": \"罗小姐\",\n" +
                "\t\"genderCode\": 3,\n" +
                "\t\"id\": 1140962949402255360,\n" +
                "\t\"isDecisionMaker\": 3,\n" +
                "\t\"isDisabled\": 0,\n" +
                "\t\"isSalesMan\": 0,\n" +
                "\t\"mobile\": \"18823201832\",\n" +
                "\t\"mobileFlag\": 1,\n" +
                "\t\"modifiedById\": \"8203bf35-0756-4290-8087-b8c948534a1e\",\n" +
                "\t\"modifiedOn\": \"2019-06-18 20:42:19\",\n" +
                "\t\"oppId\": \"6ebd85fd-35f1-430d-9b31-d941610d88fd\",\n" +
                "\t\"productLine\": 55,\n" +
                "\t\"sourceCode\": 0,\n" +
                "\t\"state\": 1,\n" +
                "\t\"userId\": 0\n" +
                "}, {\n" +
                "\t\"basicLeadId\": \"17340983847913\",\n" +
                "\t\"cityId\": \"13ce2c1f-6ef3-e211-93ee-d4ae529a94fc\",\n" +
                "\t\"contactId\": \"0e35fe2a-e5b4-4db2-b297-e753a0a4d80e\",\n" +
                "\t\"contactType\": 1,\n" +
                "\t\"createdById\": \"\",\n" +
                "\t\"createdOn\": \"2019-06-18 20:22:25\",\n" +
                "\t\"custEntityId\": \"\",\n" +
                "\t\"fullName\": \"未知\",\n" +
                "\t\"genderCode\": 3,\n" +
                "\t\"id\": 1143928293054001153,\n" +
                "\t\"isDecisionMaker\": 3,\n" +
                "\t\"isDisabled\": 0,\n" +
                "\t\"isSalesMan\": 0,\n" +
                "\t\"modifiedById\": \"\",\n" +
                "\t\"modifiedOn\": \"2019-06-18 20:22:25\",\n" +
                "\t\"oppId\": \"6ebd85fd-35f1-430d-9b31-d941610d88fd\",\n" +
                "\t\"productLine\": 55,\n" +
                "\t\"sourceCode\": 0,\n" +
                "\t\"state\": 1\n" +
                "}]";

        List<ContactVO> contactVOList = JSON.parseArray(s, ContactVO.class);
        System.out.println(JSON.toJSONString(contactVOList));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Collections.sort(contactVOList, new Comparator<ContactVO>() {
            @Override
            public int compare(ContactVO o1, ContactVO o2) {
               return o1.getId().compareTo(o1.getId());
            }
        });
        System.out.println(JSON.toJSONString(contactVOList));
    }
}
