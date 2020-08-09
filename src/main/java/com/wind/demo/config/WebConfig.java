package com.wind.demo.config;

import com.wind.demo.access.AccessLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web的配置
 * @author Saint
 * @createTime 2020-05-22 16:52
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    AccessLimitInterceptor accessLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessLimitInterceptor);
                /*//拦截路径
                .addPathPatterns("/translate")
                //放行路径
                .excludePathPatterns("/**");*/
    }
}
