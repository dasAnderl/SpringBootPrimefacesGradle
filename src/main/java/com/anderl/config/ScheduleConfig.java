package com.anderl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by dasanderl on 12.09.14.
 */
@Configuration
@EnableScheduling
public class ScheduleConfig {


    @Scheduled(fixedDelay = 10000)
    public void testScheduleTask() {
        System.out.println("in scheduled task");
    }
}