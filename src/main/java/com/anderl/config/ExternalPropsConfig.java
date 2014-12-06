package com.anderl.config;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by dasanderl on 12.09.14.
 */
@Configuration
@PropertySource(value = "${external.props}", ignoreResourceNotFound = false)
@PropertySource(value = "add more files", ignoreResourceNotFound = true)
public class ExternalPropsConfig {

    static {
        LoggerFactory.getLogger(ExternalPropsConfig.class).debug("loading external properties");
    }
}