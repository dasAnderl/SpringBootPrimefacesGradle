package com.anderl;

import com.anderl.helper.ViewScope;
import com.google.common.collect.Maps;
import com.sun.faces.config.ConfigureListener;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.jsf.el.SpringBeanFacesELResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.el.ELResolver;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import java.util.Date;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
@ComponentScan
//@EnableConfigurationProperties //use this to register other properties sources e.g. property files
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public ServletRegistrationBean facesServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(
                new FacesServlet(), "*.xhtml", "*.jsf");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean
    public ServletListenerRegistrationBean<ConfigureListener> jsfConfigureListener() {
        return new ServletListenerRegistrationBean<>(
                new ConfigureListener());
    }

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsf");
        return resolver;
    }

    @Bean
    public static CustomScopeConfigurer getViewScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        Map<String,Object> view = Maps.newHashMap();
        view.put("view", new ViewScope());
        customScopeConfigurer.setScopes(view);
        return customScopeConfigurer;
    }
}