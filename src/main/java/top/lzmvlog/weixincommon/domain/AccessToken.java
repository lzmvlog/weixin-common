package top.lzmvlog.weixincommon.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author 是小张啊 zhang1591313226@163.com
 * @since 2023-04-18
 */
public class AccessToken {

    /**
     * 获取access_token填写client_credential
     */
    @JSONField(name = "grant_type", defaultValue = "client_credential")
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

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
