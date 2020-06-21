package com.okc.config;

import com.okc.filter.CrossFilter;
import com.okc.filter.JwtFilter;
import com.okc.security.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(new MyAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // 禁用跨域处理
                .csrf().disable()
                // 禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 所有请求需要token
                .and()
                .authorizeRequests().anyRequest().authenticated()
                // 自定义权限拒绝处理类
                .and()
                .exceptionHandling()
                // 403处理器
                .accessDeniedHandler(new MyAccessDeniedHandler())
                // 401处理器
                .authenticationEntryPoint(new MyAuthenticationEntryPoint())
                // 添加自定义权限过滤器
                .and()
                // JWT校验
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) {
        // 过滤请求
        web.ignoring()
                .antMatchers(
                        HttpMethod.GET,
                        "/**/*.gif",
                        "/**/*.jpg",
                        "/**/*.png",
                        "/**/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.txt",
                        "/**/*.js"
                )
                .antMatchers("/")
                .antMatchers("/auth/**")
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/", "/v2/api-docs")

        ;
    }


}