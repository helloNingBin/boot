package javaboy;

import com.example.boot.mvc2.BootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 测试类似SpringMVC中的HandleMapping，注解Map
 */
@SpringBootApplication
public class JavaBoyApplication {
    public static void main(String[] args) {
        System.out.println("----------------begine start bootapplication- will be ok note.------------JavaBoyApplication");
        SpringApplication.run(JavaBoyApplication.class, args);
        System.out.println("----------------end start bootapplication- will be ok note.------------");
    }
}
