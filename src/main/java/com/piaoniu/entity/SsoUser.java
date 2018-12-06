package com.piaoniu.entity;

import lombok.Data;


/**
 * Created with IntelliJ IDEA.
 * Description:
 * SsoUser: daniel
 * Date: 2018-11-28
 * Time: 上午1:00
 */
@Data
public class SsoUser extends com.piaoniu.entity.Entity {
    /**
     * 用户名
     */
    private String name;
    private String password;
    private String email;
    private String phone;
    private int status;
    private String token;

}
