package com.anderl.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Created by dasanderl on 12.09.14.
 */
@Configuration
@EnableScheduling
public class ScheduleConfig {

    private final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

    @Scheduled(fixedDelay = 10000)
    public void testScheduleTask() {
        logger.warn("first scheduled task");
    }
}