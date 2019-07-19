package com.okc.config;

import com.okc.filter.MyCorsFilter;
import com.okc.security.*;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private TokenProvider tokenProvider;
    private MyCorsFilter myCorsFilter;
    private MyAuthenticationProvider myAuthenticationProvider;
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    private MyAccessDeniedHandler accessDeniedHandler;

    public WebSecurityConfig(TokenProvider tokenProvider, com.okc.filter.MyCorsFilter myCorsFilter, MyAuthenticationProvider myAuthenticationProvider, MyFilterSecurityInterceptor myFilterSecurityInterceptor, MyAccessDeniedHandler accessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.myCorsFilter = myCorsFilter;
        this.myAuthenticationProvider = myAuthenticationProvider;
        this.myFilterSecurityInterceptor = myFilterSecurityInterceptor;
        this.accessDeniedHandler = accessDeniedHandler;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(myAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // 禁用跨域处理
                .csrf().disable()
                // 禁用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 所有请求需要授权
                .and()
                .authorizeRequests().anyRequest().authenticated()
                // 自定义权限拒绝处理类
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                // 添加自定义权限过滤器
                .and()
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)
                // 登录校验
                .addFilterBefore(myCorsFilter, UsernamePasswordAuthenticationFilter.class)
                // JWT校验
                .apply(new JwtConfigurer(tokenProvider));

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
                .antMatchers("/user/login")
                .antMatchers("/fdfs/**")
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/", "/v2/api-docs")

        ;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        return new MyAccessDecisionManager();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}