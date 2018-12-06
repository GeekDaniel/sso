package com.piaoniu.service;

import com.piaoniu.dao.SsoUserDao;
import com.piaoniu.entity.SsoUser;
import com.piaoniu.util.SecurityHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * SsoUser: daniel
 * Date: 2018-11-28
 * Time: 上午12:59
 */
@Service
@Slf4j
public class Userservice {

    @Autowired
    private SsoUserDao ssoUserDao;

    public SsoUser checkUser(String name, String password) {
        SsoUser user = ssoUserDao.findByName(name);
        String encryptedPass = SecurityHelper.encryptPassword(password);
        if (user != null && encryptedPass.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    @Transactional
    public SsoUser validateTransactionalWork(String name) {
        SsoUser user = ssoUserDao.findByName(name);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ssoUserDao.updateForName("daniel1", user.getId());

        log.info("username after changed:{}", new Object[]{ssoUserDao.findById(user.getId()).getName()});

        if (true) {
            throw new RuntimeException("");
        }
        return null;
    }

    public SsoUser aCallB(String name){
       return  validateTransactionalWithPrivateNotWork(name);
    }

    @Transactional
    private SsoUser validateTransactionalWithPrivateNotWork(String name) {
        SsoUser user = ssoUserDao.findByName(name);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ssoUserDao.updateForName("daniel1", user.getId());

        log.info("username after changed:{}", new Object[]{ssoUserDao.findById(user.getId()).getName()});

        if (true) {
            throw new RuntimeException("on purpose");
        }
        return null;
    }

    @Transactional
    public void validateSerial(String name) {
        SsoUser user = ssoUserDao.findByName(name);
        try {
            SsoUser ssoUser = ssoUserDao.lockById(user.getId());
            log.info("ssoUser get :"+ssoUser.getName());
        }catch (Exception e){
            log.info("exception class: {}",new Object[]{e.getClass().getName()});
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("process over");
    }




}
