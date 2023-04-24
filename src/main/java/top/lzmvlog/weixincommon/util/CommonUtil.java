package top.lzmvlog.weixincommon.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import top.lzmvlog.weixincommon.common.CommonCode;
import top.lzmvlog.weixincommon.common.RequestHandler;
import top.lzmvlog.weixincommon.domain.AccessToken;
import top.lzmvlog.weixincommon.domain.UserGet;
import top.lzmvlog.weixincommon.domain.UserInfo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 是小张啊 zhang1591313226@163.com
 * @since 2023-04-18
 */
@Component
public class CommonUtil {

    /**
     * 请求接口
     */
    @Value("${common.url}")
    private String url;

    /**
     * 第三方用户唯一凭证
     */
    @Value("${common.appid}")
    private String appid;

    /**
     * 第三方用户唯一凭证密钥，即appsecret
     */
    @Value("${common.secret}")
    private String secret;

    private final RestTemplate restTemplate;

    private final RedisTemplate redisTemplate;

    public CommonUtil(RestTemplate restTemplate, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取 微信公众号 access_token
     *
     * @return
     */
    public String getAccessToken() {
        AccessToken accessToken = new AccessToken();
        accessToken.setAppId(appid);
        accessToken.setSecret(secret);
        Map<String, Object> map = new HashMap<>(JSON.parseObject(accessToken.toString()));
        String requestUrl = RequestHandler.getUrl(url + "cgi-bin/token", map);
        String response = restTemplate.getForObject(requestUrl, String.class);
        JSONObject jsonObject = JSON.parseObject(response);
        String errCode = String.valueOf(jsonObject.get(CommonCode.ERRCODE));
        if (StringUtils.isNotBlank(errCode) && !errCode.equals(CommonCode.CODE)) {
            return null;
        }
        Long timeout = Long.valueOf((Integer) jsonObject.get(CommonCode.EXPIRESIN));
        String token = String.valueOf(jsonObject.get(CommonCode.ACCESSTOKEN));
        redisTemplate.opsForValue().set(CommonCode.TOKEN, token, timeout, TimeUnit.SECONDS);
        return token;
    }

    /**
     * 获取用户的 openId
     *
     * @param userGet 请求 用户列表参数
     * @return
     */
    public List<String> getOpenIds(UserGet userGet) {
        Map<String, Object> map = new HashMap<>(JSON.parseObject(userGet.toString()));
        String requestUrl = RequestHandler.getUrl(url + "cgi-bin/user/get", map);
        String response = restTemplate.getForObject(requestUrl, String.class);
        JSONObject jsonObject = JSON.parseObject(response);
        String count = String.valueOf(jsonObject.get(CommonCode.COUNT));
        if (count.equals(CommonCode.CODE)) {
            return null;
        }
        Object data = jsonObject.get(CommonCode.DATA);
        JSONObject openids = JSON.parseObject(String.valueOf(data));
        String openid = String.valueOf(openids.get(CommonCode.OPENID));
        JSONArray objects = JSON.parseArray(String.valueOf(openid));
        Iterator<Object> iterator = objects.stream().iterator();
        return IteratorUtils.toList(iterator);
    }

    /**
     * 获取 openid 微信信息
     *
     * @param token  access_token
     * @param openId openid
     * @return
     */
    public String getUser(String token, String openId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAccessToken(token);
        userInfo.setOpenId(openId);
        Map<String, Object> map = new HashMap<>(JSON.parseObject(userInfo.toString()));
        String requestUrl = RequestHandler.getUrl(url + "cgi-bin/user/info", map);
        String response = restTemplate.getForObject(requestUrl, String.class);
        JSONObject jsonObject = JSON.parseObject(response);
        String name = String.valueOf(jsonObject.get(CommonCode.NICKNAME));
        return name;
    }


}
