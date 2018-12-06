package com.piaoniu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * SsoUser: daniel
 * Date: 2018-11-27
 * Time: 下午11:49
 */
@SpringBootApplication
public class SsoApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SsoApplication.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(SsoApplication.class, args);
    }

}

