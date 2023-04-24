package top.lzmvlog.weixincommon.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author 是小张啊 zhang1591313226@163.com
 * @since 2023-04-18
 */
public class UserInfo {

    /**
     * 获取到的凭证
     */
    @JSONField(name = "access_token")
    private String accessToken;

    /**
     * 获取到的凭证
     */
    @JSONField(name = "openid")
    private String openId;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
