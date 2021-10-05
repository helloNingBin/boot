package javaboy.controller;

import com.example.boot.bean.Car;
import com.example.boot.bean.Persion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeanController {
    @Autowired
    Car car;
    @Autowired
    Persion persion;
    @RequestMapping("/getPerson")
    @ResponseBody
    public Persion getPersion(@RequestParam(defaultValue = "1")String name){
        System.out.println("car:" + car);
        System.out.println("persion's:" + persion.getCar());
        return persion;
    }
}
