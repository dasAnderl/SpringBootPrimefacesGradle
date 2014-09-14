package com.anderl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
//@EnableConfigurationProperties //use this to register other properties sources e.g. property files
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}