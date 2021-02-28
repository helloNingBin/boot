package com.example.boot.mvc2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BootApplication {

    public static void main(String[] args) {
        System.out.println("----------------begine start bootapplication- will be ok note.------------");
        SpringApplication.run(BootApplication.class, args);
        //------00000000000
        //update at home  keyiliangxi
        System.out.println("----------------end start bootapplication- will be ok note.------------");
    }

}
