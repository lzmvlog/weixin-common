package top.lzmvlog.weixincommon.domain.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author 是小张啊 zhang1591313226@163.com
 * @since 2023-04-19
 */
public class WebAccessToken {
    /**
     *  网页授权 获取access_token填写authorization_code
     */
    @JSONField(name = "grant_type", defaultValue = "authorization_code")
    private String grantType;

    /**
     * 第三方用户唯一凭证
     */
    @JSONField(name = "appid")
    private String appId;

    /**
     * 第三方用户唯一凭证
     */
    private String secret;

    /**
     * 网页授权时才会使用
     * code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
     */
    private String code;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
