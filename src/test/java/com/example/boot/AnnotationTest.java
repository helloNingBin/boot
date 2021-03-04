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
        System.out.println(app);
        ApplicationContext app2 = new AnnotationConfigApplicationContext(MyConfig.class);
        System.out.println(app2);
    }
}
