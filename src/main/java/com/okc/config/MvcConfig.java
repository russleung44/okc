package com.okc.config;

import com.okc.filter.CustomCorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.*;


import java.util.List;

/**
 * @author leung
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public CustomCorsFilter corsFilter() {
        return new CustomCorsFilter();
    }

    /**
     * 视图控制器
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/doc.html");
    }

    /**
     * @param registry 资源处理器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * @param registry 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    /**
     * 参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    }
}