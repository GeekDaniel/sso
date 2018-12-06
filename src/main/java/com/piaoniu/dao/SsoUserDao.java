package com.piaoniu.dao;

import com.piaoniu.annotations.DaoGen;
import com.piaoniu.entity.SsoUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * SsoUser: daniel
 * Date: 2018-11-30
 * Time: 上午1:02
 */
@DaoGen
public interface SsoUserDao extends EntityDao<SsoUser> {
    String FIELDS = "id,name,password,email,phone,status,token";

    SsoUser findByName(@Param("name") String name);

    @Select("select "+FIELDS+" from pn_ssouser where id=#{id} for update ")
    SsoUser lockById(@Param("id") int id);

    int updateForName(@Param("name")String name,@Param("id") int id);
}
