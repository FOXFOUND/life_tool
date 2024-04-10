package apaast;

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
 * @Package : com.bj58.crm_svc_leads.model.dto.incall
 * @Class : IncallOpportunity
 * @Description : incall商机录入实体
 * @author : LinBoWen
 * @CreateDate : 2021-06-02 20:17:19
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
public class IncallOpportunity implements Serializable {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("base_id")
    private Long baseId;
    @JsonProperty("call_id")
    private String callId;
    @JsonProperty("create_time")
    private Long createTime;
    @JsonProperty("company_name")
    private String companyName;
    @JsonProperty("sub_category_id")
    private Integer subCategoryId;
    @JsonProperty("sub_category")
    private String subCategory;
    @JsonProperty("current_repo_state")
    private Integer currentRepoState;
    @JsonProperty("initial_repo_state")
    private Integer initialRepoState;
    @JsonProperty("city_id")
    private Integer cityId;
    @JsonProperty("city")
    private String city;
    @JsonProperty("career_type_id")
    private Integer careerTypeId;
    @JsonProperty("career_type")
    private String careerType;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("contact_name")
    private String contactName;
    @JsonProperty("business_level")
    private Integer businessLevel;
    @JsonProperty("receive_team")
    private Integer receiveTeam;
    @JsonProperty("receive_user")
    private String receiveUser;
    @JsonProperty("record_user_bsp_id")
    private String recordUserBspId;
    @JsonProperty("record_user")
    private String recordUser;
    @JsonProperty("source")
    private Integer source;
    @JsonProperty("job")
    private String job;
    @JsonProperty("tel_type")
    private Integer telType;
    @JsonProperty("customer_type")
    private Integer customerType;
    @JsonProperty("license_type")
    private Integer licenseType;
    @JsonProperty("job_type")
    private Integer jobType;
    @JsonProperty("issue_type")
    private Integer issueType;
    @JsonProperty("subissue_type")
    private Integer subissueType;
    @JsonProperty("follow_up_tips")
    private String followUpTips;

    public static void main(String[] args) throws IllegalAccessException {
        String str = "{\n" +
                "    \"id\":1,\n" +
                "    \"create_time\":1\n" +
                "}";
        IncallOpportunity incallOpportunity = JSON.parseObject(str, IncallOpportunity.class);
        System.out.println(JSON.toJSONString(incallOpportunity));


        Map<String, Object> map = new HashMap<>();
        Class<?> clazz = incallOpportunity.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(JsonProperty.class)) {
                String annotationValue = field.getAnnotation(JsonProperty.class).value();
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value = field.get(incallOpportunity);
                if (value != null) {
                    map.put(annotationValue, value);
                }
            }
        }

        System.out.println(JSON.toJSONString(map));
    }
}
