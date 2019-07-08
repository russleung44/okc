package com.okc.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tony
 * @date 2019/7/4 10:37
 */
@Configuration
public class DruidConfig {

    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        //设置控制台管理用户
        reg.addInitParameter("loginUsername", "admin");
        reg.addInitParameter("loginPassword", "123456");
        // 禁用HTML页面上的“Reset All”功能
        reg.addInitParameter("resetEnable", "false");
        reg.addInitParameter("allow", "");
        reg.addInitParameter("deny", "");
        return reg;

    }


    @Bean
    public FilterRegistrationBean webStatFilter() {

        FilterRegistrationBean bean = new FilterRegistrationBean<>(new WebStatFilter());

        Map<String, String> initParams = new HashMap<>(1);

        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");

        bean.setInitParameters(initParams);

        bean.addUrlPatterns("/*");

        return bean;
    }
}

