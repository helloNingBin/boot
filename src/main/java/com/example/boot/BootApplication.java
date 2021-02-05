package com.example.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        System.out.println("-------------begine start bootapplication-------------");
        SpringApplication.run(BootApplication.class, args);
        //------
    }

}
