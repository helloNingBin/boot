package javaboy.config;

import com.example.boot.bean.Car;
import com.example.boot.bean.Persion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class BeanConfig {
    @Bean("configCar")
    public Car getCar(){
        return new Car();
    }

    @Bean("configPerson")
    public Persion getPerson(){
        return new Persion(getCar());
    }
}
