package com.piaoniu.util;

import com.piaoniu.constant.SsoConstant;

import java.util.Base64;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: daniel
 * Date: 2018-12-02
 * Time: 下午8:26
 */

public class SecurityHelper {
    public static String encryptPassword(String password) {
        return Base64.getEncoder().encodeToString(MD5.MD5Encode(SsoConstant.MD5_SALT + password).getBytes());
    }
}
