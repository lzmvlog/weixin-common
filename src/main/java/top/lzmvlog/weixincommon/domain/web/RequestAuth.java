package top.lzmvlog.weixincommon.domain.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author 是小张啊 zhang1591313226@163.com
 * @since 2023-04-20
 */
public class RequestAuth {

    /**
     * 公众号的唯一标识
     */
    @JSONField(name = "appid")
    private String appId;

    /**
     * 授权后重定向的回调链接地址， 请使用 urlEncode 对链接进行处理
     */
    @JSONField(name = "redirect_uri")
    private String redirectUri;

    /**
     * 返回类型，请填写code
     */
    @JSONField(name = "response_type", defaultValue = "code")
    private String responseType;

    /**
     * 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo
     * （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
     */
    @JSONField(name = "scope", defaultValue = "snsapi_userinfo")
    private String scope;

    /**
     * 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     */
    private String state;

    /**
     * 强制此次授权需要用户弹窗确认；默认为false；需要注意的是，若用户命中了特殊场景下的静默授权逻辑，则此参数不生效
     */
    @JSONField(name = "forcePopup", defaultValue = "true")
    private String forcePopup;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getForcePopup() {
        return forcePopup;
    }

    public void setForcePopup(String forcePopup) {
        this.forcePopup = forcePopup;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
