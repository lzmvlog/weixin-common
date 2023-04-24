package top.lzmvlog.weixincommon.util;

/**
 * @author 是小张啊 zhang1591313226@163.com
 * @date 2023-02-17
 */
public class R<T> {

    private static final String SUCCESS_MSG = "SUCCESS";

    private static final String FAIL_MSG = "FAIL";

    private int code;

    private String msg;

    private T data;

    public static <T> R<T> ok() {
        return restResult(null, CommonHttpStatusCode.SUCCESS.getCode(), SUCCESS_MSG);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, CommonHttpStatusCode.SUCCESS.getCode(), SUCCESS_MSG);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, CommonHttpStatusCode.SUCCESS.getCode(), msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, CommonHttpStatusCode.SYSTEM_ERROR.getCode(), FAIL_MSG);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, CommonHttpStatusCode.SYSTEM_ERROR.getCode(), msg);
    }

    public static <T> R<T> failed(String msg, Integer code) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, CommonHttpStatusCode.SYSTEM_ERROR.getCode(), FAIL_MSG);
    }

    public static <T> R<T> build(CodeEnums codeEnums) {
        return restResult(null, codeEnums.getCode(), codeEnums.getMessage());
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, CommonHttpStatusCode.SYSTEM_ERROR.getCode(), msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode() {
        return code;
    }

    public boolean success() {
        return this.code == CommonHttpStatusCode.SUCCESS.getCode();
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

//    @Override
//    public String toString() {
////        return JSON.toJSONString(this);
//    }
}
