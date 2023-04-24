package top.lzmvlog.weixincommon.service.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import top.lzmvlog.weixincommon.common.RequestHandler;
import top.lzmvlog.weixincommon.domain.web.*;
import top.lzmvlog.weixincommon.service.WebService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 是小张啊 zhang1591313226@163.com
 * @since 2023-04-20
 */
@Service
public class WebServiceImpl implements WebService {
    private Logger logger = LoggerFactory.getLogger(WebServiceImpl.class);

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

    @Value("${common.redirectUri}")
    private String redirectUri;

    private final RestTemplate restTemplate;

    public WebServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 获取 微信公众号 access_token
     *
     * @return
     */
    public WebAuthInfo getAccessToken(String code) {
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//        String token = String.valueOf(valueOperations.get(RedisKeys.WEBACCESSTOKEN));
//        if (StringUtils.isNotEmpty(token)) {
//            return JSONObject.parseObject(token, WebAuthInfo.class);
//        }
        logger.info("微信公总号code：" + code);
        WebAccessToken webAccessToken = new WebAccessToken();
        webAccessToken.setAppId(appid);
        webAccessToken.setSecret(secret);
        webAccessToken.setCode(code);
        Map<String, Object> map = new HashMap<>(JSON.parseObject(webAccessToken.toString()));
        String requestHandlerUrl = RequestHandler.getUrl(url + "/sns/oauth2/access_token", map);
        String responseBody = restTemplate.getForObject(requestHandlerUrl, String.class);
        WebAuthInfo webAuthInfo = JSON.parseObject(responseBody, WebAuthInfo.class);
//        Long timeout = Long.valueOf(webAuthInfo.getExpiresIn());
//        valueOperations.set(RedisKeys.WEBACCESSTOKEN, webAuthInfo, timeout, TimeUnit.SECONDS);
        return webAuthInfo;
    }

    /**
     * 通用重定向处理
     *
     * @param response 响应回话
     * @param url      冲定向URL
     */
    private void sendRedirect(HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new RuntimeException("重定向路径异常");
        }
    }

    /**
     * 失败重定向
     *
     * @param response 响应回话
     */
    private void sendRedirectError(HttpServletResponse response) {
        sendRedirect(response, "http://wx.xxx/authFail");
    }

    /**
     * 成功重定向
     *
     * @param response 响应回话
     */
    private void sendRedirectSuccess(HttpServletResponse response) {
        sendRedirect(response, "http://wx.xxx/authFail");
    }

    /**
     * 获取认证地址
     *
     * @param state    认证参数
     * @param response
     * @return
     */
    @Override
    public String auth(String state, HttpServletResponse response) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize";
        RequestAuth requestAuth = new RequestAuth();
        requestAuth.setAppId(appid);
        requestAuth.setRedirectUri(redirectUri);
        requestAuth.setState(state);
        Map<String, Object> map = new HashMap<>(JSON.parseObject(requestAuth.toString()));
        return RequestHandler.getUrl(url, map) + "#wechat_redirect";
    }

    /**
     * 微信回调
     *
     * @param code     微信返回的申请 access_token 的参数
     * @param state    认证参数
     * @param response
     * @return
     */
    @Override
    public WebUserInfo callback(String code, String state, HttpServletResponse response) {
        WebAuthInfo webAuthInfo = getAccessToken(code);
        logger.info("web_access_token：" + webAuthInfo.toString());
        RequestUserInfo requestUserInfo = new RequestUserInfo();
        requestUserInfo.setAccessToken(webAuthInfo.getAccessToken());
        requestUserInfo.setOpenid(webAuthInfo.getOpenid());
        Map<String, Object> map = new HashMap<>(JSON.parseObject(requestUserInfo.toString()));
        String responseBody = RequestHandler.getUrl(url + "/sns/userinfo", map);
//        if (StringUtils.isNotEmpty(responseBody)) {
//
//        }
        WebUserInfo webUserInfo = JSON.parseObject(responseBody, WebUserInfo.class);
        logger.info("web_user_info：" + webUserInfo.toString());
        return webUserInfo;
    }
}
