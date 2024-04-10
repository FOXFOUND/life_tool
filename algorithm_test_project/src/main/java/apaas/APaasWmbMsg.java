package apaas;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/***
 *
 * @Project : CRM2.5
 * @Package : com.bj58.crm_svc_leads.wmb.apaas
 * @Class : APaasWmbMsg
 * @Description : apaas-wmb-消息格式
 * @author : LinBoWen
 * @CreateDate : 2021-06-04 17:36:57
 * @version : V1.0.0
 * @Copyright : 2021 58 Inc. All rights reserved.
 * @Reviewed : 
 * @UpateLog :    Name    Date    Reason/Contents
 *             ---------------------------------------
 *                ****    ****    **** 
 * 
 */
@NoArgsConstructor
@Data
public class APaasWmbMsg implements Serializable {

    @JsonProperty("appId")
    private Long appId;
    @JsonProperty("tableId")
    private Long tableId;
    @JsonProperty("tableName")
    private String tableName;
    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("time")
    private Long time;
    @JsonProperty("data")
    private Map data;
    @JsonProperty("where")
    private Map where;


    public static void main(String[] args) throws IllegalAccessException {
        APaasWmbMsg aPaasWmbMsg = new APaasWmbMsg();
        aPaasWmbMsg.setAppId(1L);
        aPaasWmbMsg.setTableId(1L);
        aPaasWmbMsg.setTableName("str");
        aPaasWmbMsg.setUuid("str");
        aPaasWmbMsg.setTime(1L);
        Map<String,Object> testMap = new HashMap();
        IncallOpportunity incallOpportunity = new IncallOpportunity();
        getInner(incallOpportunity);
        Map<String, Object> res =  getObjectToMap(incallOpportunity);
        testMap.put("id","123");
        aPaasWmbMsg.setData(res);
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("status",0);
        aPaasWmbMsg.setWhere(resMap);
        System.out.println(JSON.toJSONString(aPaasWmbMsg));
        IncallOpportunity incallOpportunity1 = JSON.parseObject(JSON.toJSONString(res),IncallOpportunity.class);
        System.out.println(JSON.toJSONString(incallOpportunity1));

    }

    public static Map<String, Object> getObjectToMap(Object obj) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<String, Object>();
        Class<?> cla = obj.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String keyName = field.getName();
            Object value = field.get(obj);
            if (value == null)
                value = "";
            map.put(keyName, value);
        }
        return map;
    }


    private static void getInner(IncallOpportunity incallOpportunity) {
        incallOpportunity.setId(0L);
        incallOpportunity.setBaseId(0L);
        incallOpportunity.setCallId("");
        incallOpportunity.setCreateTime(0);
        incallOpportunity.setCompanyName("");
        incallOpportunity.setSubCategoryId(0);
        incallOpportunity.setSubCategory("");
        incallOpportunity.setCurrentRepoState(0);
        incallOpportunity.setInitialRepoState(0);
        incallOpportunity.setCityId(0);
        incallOpportunity.setCity("");
        incallOpportunity.setCareerTypeId(0);
        incallOpportunity.setCareerType("");
        incallOpportunity.setPhoneNumber("");
        incallOpportunity.setBusinessLevel(0);
        incallOpportunity.setReceiveTeam(0);
        incallOpportunity.setReceiveUser("");
        incallOpportunity.setRecordUserBspId("");
        incallOpportunity.setRecordUser("");
        incallOpportunity.setSource(0);
        incallOpportunity.setJob("");
        incallOpportunity.setTelType(0);
        incallOpportunity.setCustomerType(0);
        incallOpportunity.setLicenseType(0);
        incallOpportunity.setJobType(0);
        incallOpportunity.setIssueType(0);
        incallOpportunity.setSubIssueType(0);
        incallOpportunity.setFollowUpTips("");
    }

}
