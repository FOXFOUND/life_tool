package fastjson;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一结果对象模型
 *
 * @author wangyafei05
 * @date 2019/3/12 16:43
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResultModel<T> {
    private String msg;
    private int code;
    private T data;

    public ResultModel(ResultCode resultCode, T data) {
        this(resultCode);
        this.data = data;
    }

    public ResultModel(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    public ResultModel(ResultCode resultCode, String msg) {
        this.code = resultCode.getCode();
        this.msg = msg;
    }

    public static <T> ResultModel success() {
        return new ResultModel<>(ResultCode.SUCCESS);
    }

    public static <T> ResultModel success(String msg) {
        return new ResultModel<>(ResultCode.SUCCESS, msg);
    }

    public static <T> ResultModel<T> success(T data) {
        return new ResultModel<>(ResultCode.SUCCESS, data);
    }

    public static <T> ResultModel<T> fail() {
        return new ResultModel<>(ResultCode.ERROR);
    }

    public static <T> ResultModel<T> fail(String msg) {
        return new ResultModel<>(ResultCode.ERROR, msg);
    }

    public static <T> ResultModel paramError() {
        return new ResultModel<>(ResultCode.PARAMETER_ERROR);
    }

    public static <T> ResultModel<T> paramError(String msg) {
        return new ResultModel<>(ResultCode.PARAMETER_ERROR, msg);
    }

    public enum ResultCode {
        SUCCESS(0, "操作成功"),
        ERROR(1, "操作失败"),
        PARAMETER_ERROR(2, "参数错误");

        private int code;
        private String msg;

        ResultCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        public static String getMessage(int code) {
            for (ResultCode rCode : ResultCode.values()) {
                if (rCode.getCode() == code) {
                    return rCode.getMsg();
                }
            }
           return "";
        }
    }

    /**
     * Method isSuccess returns the success of this ResultModel object.
     *
     * @return the success (type boolean) of this ResultModel object.
     */
    public boolean isSuccess() {
        return this.code == ResultCode.SUCCESS.getCode();
    }
}
