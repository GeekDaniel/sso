package com.piaoniu.service;

import com.piaoniu.SsoApplication;
import com.piaoniu.dao.SsoUserDao;
import com.piaoniu.entity.SsoUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: daniel
 * Date: 2018-12-06
 * Time: 下午11:11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SsoApplication.class)
@Slf4j
public class DBLockerTest {
    @Autowired
    private DBLocker dbLocker;

    ExecutorService executorService=Executors.newFixedThreadPool(2);

    @Autowired
    private SsoUserDao ssoUserDao;

    @Test
    public void withLock() {
            CountDownLatch countDownLatch=new CountDownLatch(2);
            executorService.submit(()->{
                dbLocker.withLock(1, "pn_ssouser",(id)->{
                    SsoUser ssoUser = ssoUserDao.findById(id);
                    log.info( "userName:"+ssoUser.getName());
                } );
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });

            executorService.submit(()->{
                dbLocker.withLock(1, "pn_ssouser",(id)->{
                    SsoUser ssoUser = ssoUserDao.findById(id);
                    log.info( "userName:"+ssoUser.getName());
                } );
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
