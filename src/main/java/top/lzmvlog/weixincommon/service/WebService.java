package top.lzmvlog.weixincommon.service;

import top.lzmvlog.weixincommon.domain.web.WebUserInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 是小张啊 zhang1591313226@163.com
 * @since 2023-04-20
 */
public interface WebService {

    /**
     * 获取认证地址
     *
     * @param state    认证参数
     * @param response
     * @return
     */
    String auth(String state, HttpServletResponse response);

    /**
     * 微信回调
     *
     * @param code 微信返回的申请 access_token 的参数
     * @param state 认证参数
     * @param response
     * @return
     */
    WebUserInfo callback(String code, String state, HttpServletResponse response);
}
