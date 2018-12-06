package com.piaoniu.controller;

import com.piaoniu.constant.SsoConstant;
import com.piaoniu.dto.LoginRequest;
import com.piaoniu.entity.SsoUser;
import com.piaoniu.service.Userservice;
import com.piaoniu.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * SsoUser: daniel
 * Date: 2018-11-28
 * Time: 上午12:45
 */
@Controller
@RequestMapping(SsoConstant.SSO_API_PREFIX + "/user")
public class LoginController {
    @Autowired
    private Userservice userservice;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map login(@RequestBody LoginRequest loginRequest) {
        SsoUser ssoUser = userservice.checkUser(loginRequest.getName(), loginRequest.getPassword());
        WebUtils.addCookie(response, SsoConstant.TOKEN_NAME, System.currentTimeMillis() + "");
        HashMap hashMap = new HashMap();
        hashMap.put("ssoUser", ssoUser);
        hashMap.put("originUrl", loginRequest.getOriginUrl());
        return hashMap;
    }

    @RequestMapping("/getUserInfo")
    @ResponseBody
    public Object getUserInfo(HttpServletRequest request) {
        String token = WebUtils.getCookieValue(request, SsoConstant.TOKEN_NAME);
        //todo replce mock
        if (!StringUtils.isEmpty(token)) {
            return new SsoUser();
        }
        return new HashMap();
    }

    public static void main(String[] args) {
        System.out.println(new String(Base64.getEncoder().encode("http://www.baidu.com".getBytes())));
    }

}
