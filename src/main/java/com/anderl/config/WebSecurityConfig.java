package com.anderl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by dasanderl on 11.09.14.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${h2.console.url.pattern}")
    private String h2ConsoleUrlPattern;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers(
                        "/resources/**"
                        , "/shutdown"
                        , h2ConsoleUrlPattern
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/public/**").permitAll()
                // we basically disable security for local development
                .antMatchers("/**").permitAll()
                .anyRequest().hasRole("USER")
                .and()
                .formLogin() // enable form based log in
                .defaultSuccessUrl("/index.xhtml")
                .permitAll()
                .and()
                .sessionManagement()
                .sessionFixation()
                .newSession()   // migrate session creates exeptions
                .and()
                .csrf()  //cross site request forgery, without disabling login doesnt work
                .disable();
    }

    @Configuration
    protected static class AuthenticationConfiguration extends
            GlobalAuthenticationConfigurerAdapter {

        @Value("${user.default}")
        protected String userDefault;

        @Value("${user.default.pw}")
        private String userDefaultPw;

        @Value("${user.admin}")
        private String userAdmin;

        @Value("${user.admin.pw}")
        private String userAdminPw;

        @Value("${role.admin}")
        private String roleAdmin;

        @Value("${role.user}")
        private String roleUser;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .inMemoryAuthentication()
                    .withUser(userDefault).password(userDefaultPw).roles(roleUser)
                    .and()
                    .withUser(userAdmin).password(userAdminPw).roles(roleAdmin);
        }

    }
}
