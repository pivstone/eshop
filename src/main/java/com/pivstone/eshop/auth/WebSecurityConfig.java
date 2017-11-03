package com.pivstone.eshop.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Mail: pivstone@gmail.com <br>
 * Created by pivstone on 2017/11/3.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**")
                .permitAll();
//                .and()
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/products/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PATCH, "/products/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PUT, "/categories/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.PATCH, "/categories/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.DELETE, "/categories/**").hasRole("ADMIN");
    }

}
