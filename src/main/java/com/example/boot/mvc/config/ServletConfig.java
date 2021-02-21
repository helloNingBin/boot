package com.example.boot.mvc.config;

import com.example.boot.mvc.servlet.ConfigServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 */
@Configuration
public class ServletConfig implements WebMvcConfigurer {
    /**
     * nbDispatcherServlet2方法名默认是servlet的名称
     */
    @Bean
    public ServletRegistrationBean nbDispatcherServlet2(){
        return new ServletRegistrationBean(new ConfigServlet(),"/nbmvc2/*");
    }
}
