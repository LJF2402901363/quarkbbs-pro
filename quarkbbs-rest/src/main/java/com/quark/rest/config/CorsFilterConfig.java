package com.quark.rest.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Classname:CorsFilterConfig
 *
 * @description:
 * @author: 陌意随影
 * @Date: 2021-05-30 01:54
 * @Version: 1.0
 **/
@Configuration
public class CorsFilterConfig  {
   @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
       config.addAllowedOriginPattern("*");
       config.addAllowedHeader("*");
       config.addAllowedMethod("*");
       config.setAllowCredentials(true);
//        config.addAllowedOrigin("*");
        source.registerCorsConfiguration("/**", config); // CORS 配置对所有接口都有效
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }
}
