package javaboy.config;

import com.example.boot.bean.Car;
import com.example.boot.bean.Persion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConfigTest {
    @Autowired
    Car car;
    @Autowired
    Persion persion;
    @Test
    public void testConfigCar(){
        //@Component和@Configuration的区别，@Configuration会在ConfigurationClassPostProcess中被代理，然后会做一些缓存，当重复获取时，不会重新创建
        System.out.println("car:" + car);
        System.out.println("persion's:" + persion.getCar());
    }
}
