package fastjson;

import lombok.Data;

import java.util.Map;

/**
 * @author jiazhichao
 * @date  2019/12/18 15:04
 */
@Data
public class PhoneInfoDTO {
    /**
     * 1-防骚扰[电话划线],2-黑名单,3-正向,4-负向，0-正常或无
     */
    private Integer status;
    /**
     *  防骚扰
     */
    private Boolean isBlock;
    private Integer count;
    private String cityName;
    private Integer productLine;
    private String phone;
    private String oppId;
    private int level;
    /**电话风险值预测相关*/
    private boolean highRisk;
    private String highRiskReason;
    private Float riskScore;
    private Map<String, Integer> riskTagMap;
    /**
     * 是否黑名单
     */
    private Boolean isBlack;
    /**
     *  电话画像 正向、普通、负向
     */
    private String portraitStatus;
    /**
     * 正负向原因
     */
    private String conditionalReason;
    /**
     * 电话标记错误次数
     */
    private String errorPhoneDesc;
}
