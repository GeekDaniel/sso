package com.piaoniu.dao;

import com.piaoniu.SsoApplication;
import com.piaoniu.entity.SsoUser;
import com.piaoniu.service.Userservice;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: daniel
 * Date: 2018-12-05
 * Time: 下午9:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SsoApplication.class)
@Slf4j
public class UserServiceTest {

    @Autowired
    private Userservice userservice;

    ExecutorService executorService=Executors.newFixedThreadPool(2);

    @Test
    public void validTransactionalWork(){
        userservice.validateTransactionalWork("daniel");
    }

    @Test
    public void aCallB(){
        userservice.aCallB("daniel");
    }

    @Test
    public void checkForUpdateLockThrowException(){
        CountDownLatch countDownLatch=new CountDownLatch(2);
        executorService.submit(()->{
            userservice.validateSerial("daniel");
            countDownLatch.countDown();
        });
        executorService.submit(()->{
            userservice.validateSerial("daniel");
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
