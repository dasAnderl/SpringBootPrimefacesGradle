package com.anderl;

import com.anderl.helper.ViewScope;
import com.google.common.collect.Maps;
import com.sun.faces.config.ConfigureListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.jsf.el.SpringBeanFacesELResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.el.ELResolver;
import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
@ComponentScan
//@EnableTransactionManagement
//@EnableJpaRepositories("com.anderl.dao")
//@EntityScan("com.anderl.domain")
public class Application extends SpringBootServletInitializer implements ServletContextAware {

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
        return new ServletListenerRegistrationBean<ConfigureListener>(
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
    public ELResolver getElResolver() {
        return new SpringBeanFacesELResolver();
    }

    @Bean
    public CustomScopeConfigurer getViewScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        Map<String,Object> view = Maps.newHashMap();
        view.put("view", new ViewScope());
        customScopeConfigurer.setScopes(view);
        return customScopeConfigurer;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
    }


}