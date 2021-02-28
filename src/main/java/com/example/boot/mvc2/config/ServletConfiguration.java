package com.example.boot.mvc2.config;

import com.example.boot.mvc2.servlet.ConfigServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 */
@Configuration
public class ServletConfiguration implements WebMvcConfigurer {
    /**
     * nbDispatcherServlet2方法名默认是servlet的名称
     */
    @Bean
    public ServletRegistrationBean nbDispatcherServlet20(){
        return new ServletRegistrationBean(new ConfigServlet(),"/cnbmvc2/*");
    }
}
