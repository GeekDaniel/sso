package com.piaoniu.controller;

import com.piaoniu.constant.SsoConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: daniel
 * Date: 2018-12-02
 * Time: 上午11:51
 */
@Controller
@RequestMapping(SsoConstant.SSO_VIEW_PREFIX+"/user")
public class LoginViewController {
    @RequestMapping(value = "/loginView", method = RequestMethod.GET)
    public String loginView(HttpServletRequest request, HttpServletResponse response) {
        return "login";
    }
}
