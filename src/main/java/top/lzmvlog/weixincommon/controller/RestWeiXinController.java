package top.lzmvlog.weixincommon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lzmvlog.weixincommon.domain.web.WebUserInfo;
import top.lzmvlog.weixincommon.service.WebService;
import top.lzmvlog.weixincommon.util.ResultVo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author 是小张啊 zhang1591313226@163.com
 * @since 2023-04-18
 */
@RestController
public class RestWeiXinController {

    private final WebService webService;

    public RestWeiXinController(WebService webService) {
        this.webService = webService;
    }

    /**
     * 获取认证
     *
     * @param state    32位验证码
     * @param response
     * @return
     */
    @GetMapping("/auth")
    public ResultVo<String> auth(String state, HttpServletResponse response) {
        return ResultVo.build(webService.auth(state, response));
    }

    /**
     * 微信回调
     *
     * @param code 微信返回的申请 access_token 的参数
     * @param state 认证参数
     * @param response
     * @return
     */
    @GetMapping("/callback")
    public ResultVo<WebUserInfo> getCode(String code, String state, HttpServletResponse response) {
        return ResultVo.build(webService.callback(code, state, response));
    }


}
