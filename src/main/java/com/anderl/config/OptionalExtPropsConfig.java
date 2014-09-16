package com.anderl.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by dasanderl on 12.09.14.
 */
@Configuration
@PropertySource(value = "file:D:\\test.properties", ignoreResourceNotFound = true)
public class OptionalExtPropsConfig {

    @Autowired
    Environment env;

    @Bean
    public String testBean() {
        System.out.println("define some valid external config file here");
//        print here som value of your of your external config file e.g.
        env.getProperty("test.property");
//        setting active profiles in propertysource does not yet work due to bug http://stackoverflow.com/questions/25427684/using-profile-in-spring-boot
        env.getProperty("spring.profiles.active");
        return null;
    }
}