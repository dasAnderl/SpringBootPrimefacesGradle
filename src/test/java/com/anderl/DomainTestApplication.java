package com.anderl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.anderl.domain", "com.anderl.dao"})
public class DomainTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainTestApplication.class);
    }
}