package json.tomap;

import lombok.Data;

/**
 * 【ClassName】：OppContactVO
 * 【Description】：商机联系人vo
 * 【Author】：zhangzhiwei05@58ganji.com
 * 【Create】：2018年09月27日 16:11
 */
@Data
public class ContactVO {

    /**
     * 主键
     */
    
    private Long id;
    /**
     * 产品线
     */
    
    private Integer productLine;
    /**
     * 分表Id
     */
    
    private String oppId;
    /**
     * 联系人ID
     */
    
    private String contactId;
    /**
     * 联系人类型（0：默认；1：实体联系人）
     */
    
    private Integer contactType;
    /**
     * 基准ID
     */
    
    private String basicLeadId;
    /**
     * 城市ID
     */
    
    private String cityId;
    /**
     * 职位
     */
    
    private String jobTitle;
    /**
     * 姓名
     */
    
    private String fullName;
    /**
     * 性别
     */
    
    private Integer genderCode;
    /**
     * 爱好
     */
    
    private String hobby;
    /**
     * 生日
     */
    
    private String birthDate;
    /**
     * 备注
     */
    
    private String description;
    /**
     * 邮件地址
     */
    
    private String emailAddress;
    /**
     * 聊天账号
     */
    
    private String imAccount;
    /**
     * 微信号
     */
    
    private String weixinAccount;
    /**
     * 58账号
     */
    
    private Long userId;
    /**
     * 是否决策者
     */
    
    private Integer isDecisionMaker;
    /**
     * 是否销售
     */
    
    private Integer isSalesMan;
    /**
     * 实体ID
     */
    
    private String custEntityId;
    /**
     * 创建时间
     */
    
    private String createdOn;
    /**
     * 创建人ID
     */
    
    private String createdById;
    /**
     * 修改时间
     */
    
    private String modifiedOn;
    /**
     * 修改人ID
     */
    
    private String modifiedById;
    /**
     * 源 0 新签 1 续费
     */
    
    private Integer sourceCode;
    /**
     * 是否作废
     */
    
    private Integer isDisabled;

    /**
     * 手机号码
     */
    
    private String mobile;
    /**
     * 固话号码
     */
    
    private String telephone;

    /**
     * 手机号码有效标志，默认为1有效（0:无效；1：有效）
     */
    
    private Integer mobileFlag;

    /**
     * 固话有效标志
     */
    
    private Integer telFlag;

    /**
     * 状态(1:有效,0:无效)
     */
    
    private Integer state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductLine() {
        return productLine;
    }

    public void setProductLine(Integer productLine) {
        this.productLine = productLine;
    }

    public String getOppId() {
        return oppId;
    }

    public void setOppId(String oppId) {
        this.oppId = oppId;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public Integer getContactType() {
        return contactType;
    }

    public void setContactType(Integer contactType) {
        this.contactType = contactType;
    }

    public String getBasicLeadId() {
        return basicLeadId;
    }

    public void setBasicLeadId(String basicLeadId) {
        this.basicLeadId = basicLeadId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getGenderCode() {
        return genderCode;
    }

    public void setGenderCode(Integer genderCode) {
        this.genderCode = genderCode;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getImAccount() {
        return imAccount;
    }

    public void setImAccount(String imAccount) {
        this.imAccount = imAccount;
    }

    public String getWeixinAccount() {
        return weixinAccount;
    }

    public void setWeixinAccount(String weixinAccount) {
        this.weixinAccount = weixinAccount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getIsDecisionMaker() {
        return isDecisionMaker;
    }

    public void setIsDecisionMaker(Integer isDecisionMaker) {
        this.isDecisionMaker = isDecisionMaker;
    }

    public Integer getIsSalesMan() {
        return isSalesMan;
    }

    public void setIsSalesMan(Integer isSalesMan) {
        this.isSalesMan = isSalesMan;
    }

    public String getCustEntityId() {
        return custEntityId;
    }

    public void setCustEntityId(String custEntityId) {
        this.custEntityId = custEntityId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public String getModifiedById() {
        return modifiedById;
    }

    public void setModifiedById(String modifiedById) {
        this.modifiedById = modifiedById;
    }

    public Integer getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(Integer sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Integer getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getMobileFlag() {
        return mobileFlag;
    }

    public void setMobileFlag(Integer mobileFlag) {
        this.mobileFlag = mobileFlag;
    }

    public Integer getTelFlag() {
        return telFlag;
    }

    public void setTelFlag(Integer telFlag) {
        this.telFlag = telFlag;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
