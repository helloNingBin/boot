package com.example.boot;

import com.example.boot.annotation.configuration.MyConfig;
import com.example.boot.bean.Persion;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AnnotationTest {

    @Test
    public void configurationTest(){
        ApplicationContext app = new AnnotationConfigApplicationContext(MyConfig.class);
        Persion getPersionConfig = (Persion) app.getBean("ningbin");
        System.out.println(getPersionConfig);
        Persion bean = app.getBean(Persion.class);
        System.out.println(getPersionConfig == bean);
    }
}
