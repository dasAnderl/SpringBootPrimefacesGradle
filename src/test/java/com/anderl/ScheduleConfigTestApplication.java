package com.anderl;

import com.anderl.config.AmqpMessagingConfig;
import com.anderl.config.WebFilterConfig;
import com.anderl.config.WebJsfConfig;
import com.anderl.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude = {AmqpMessagingConfig.class, ExternalPropsConfigApplication.class, WebFilterConfig.class, WebJsfConfig.class, WebSecurityConfig.class})
@ComponentScan({"com.anderl.config"})
public class ScheduleConfigTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleConfigTestApplication.class);
    }
}