package com.piaoniu.dto;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: daniel
 * Date: 2018-12-02
 * Time: 下午8:32
 */
@Data
public class LoginRequest {
    private String name;
    private String password;
    private String originUrl;
}
