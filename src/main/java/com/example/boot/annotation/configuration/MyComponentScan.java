package com.example.boot.annotation.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(value = "com.example.boot.bean",
               //扫描指定包下，含注解类型，且是Controller注解的
             // includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value = {Controller.class})}
        //  扫描指定包下，具体的类
        // includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {DispatcherController.class})}
        //自定义过滤
        includeFilters = {@ComponentScan.Filter(type = FilterType.CUSTOM,value = {MyTypeFilter.class})},
        useDefaultFilters = false
)
public class MyComponentScan {
}
