package top.lzmvlog.weixincommon.util;


import org.springframework.http.HttpStatus;

/**
 * @author 是小张啊 zhang1591313226@163.com
 * @date 2023-02-17
 */
public enum CommonHttpStatusCode implements CodeEnums {

    /**
     * 成功
     */
    SUCCESS(HttpStatus.OK.value(), "SUCCESS"),

    /**
     * 系统异常
     */
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "当前系统繁忙，请稍后再试"),

    /**
     * 请求参数错误
     */
    PARAM_ERROR(4000, "请求参数错误"),

    /**
     * 验证码错误
     */
    CODE_ERROR(4100, "验证码错误"),

    /**
     * 访问路径不存在
     */
    NOT_FOUND(HttpStatus.NOT_FOUND.value(), "亲~您走错路了"),

    /**
     * 不支持的请求方式
     */
    NOT_SUPPORTED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "不支持的请求方式"),

    /**
     * 用户未授权
     */
    NOT_AUTHORIZED(HttpStatus.UNAUTHORIZED.value(), "请登录后再操作"),

    /**
     * 用户重复请求
     */
    REPEAT_REQ(HttpStatus.TOO_MANY_REQUESTS.value(), "请勿重复请求"),
    ;

    private final Integer status;

    private final String message;

    CommonHttpStatusCode(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
