package com.cys.fmmall.config;

import com.cys.fmmall.interceptor.CheckTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private CheckTokenInterceptor checkTokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(checkTokenInterceptor)
                .addPathPatterns("/shop/**")
                //.addPathPatterns("/product/**")
                .addPathPatterns("/orders/**")
                .addPathPatterns("/useraddr/**")
                .addPathPatterns("/user/check");
    }
}
