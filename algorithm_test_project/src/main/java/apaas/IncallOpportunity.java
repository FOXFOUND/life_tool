package apaas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
    @JsonProperty("baseId")
    private Long baseId;
    @JsonProperty("callId")
    private String callId;
    @JsonProperty("createTime")
    private Integer createTime;
    @JsonProperty("companyName")
    private String companyName;
    @JsonProperty("subCategoryId")
    private Integer subCategoryId;
    @JsonProperty("subCategory")
    private String subCategory;
    @JsonProperty("currentRepoState")
    private Integer currentRepoState;
    @JsonProperty("initialRepoState")
    private Integer initialRepoState;
    @JsonProperty("cityId")
    private Integer cityId;
    @JsonProperty("city")
    private String city;
    @JsonProperty("careerTypeId")
    private Integer careerTypeId;
    @JsonProperty("careerType")
    private String careerType;
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @JsonProperty("businessLevel")
    private Integer businessLevel;
    @JsonProperty("receiveTeam")
    private Integer receiveTeam;
    @JsonProperty("receiveUser")
    private String receiveUser;
    @JsonProperty("recordUserBspId")
    private String recordUserBspId;
    @JsonProperty("recordUser")
    private String recordUser;
    @JsonProperty("source")
    private Integer source;
    @JsonProperty("job")
    private String job;
    @JsonProperty("telType")
    private Integer telType;
    @JsonProperty("customerType")
    private Integer customerType;
    @JsonProperty("licenseType")
    private Integer licenseType;
    @JsonProperty("jobType")
    private Integer jobType;
    @JsonProperty("issueType")
    private Integer issueType;
    @JsonProperty("subIssueType")
    private Integer subIssueType;
    @JsonProperty("followUpTips")
    private String followUpTips;
}
