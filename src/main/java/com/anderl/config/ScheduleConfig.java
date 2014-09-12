package com.anderl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

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