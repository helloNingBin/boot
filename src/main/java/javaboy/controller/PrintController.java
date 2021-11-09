package javaboy.controller;

import javaboy.bean.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
public class PrintController {
    @Autowired
    private Book book;
    @Value("${book.name}")
    private String name;
    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable Integer id,HttpServletRequest request){
        System.out.println("id:" + id + ";book:" + book + ",name:" + name);
        System.out.println("getQueryString:" + request.getQueryString());
        System.out.println("getRequestURI:" + request.getRequestURI());
        return book;
    }
    @PostMapping("/devices/events")
    public String getData(String name, String age, HttpServletRequest request,@RequestBody String abcb) throws IOException {
        System.out.println(";name :" + name + ";age:" + age);
//        BufferedReader bufferedReader = request.getReader();

        return abcb + "=================" ;
    }
    @RequestMapping(value="/devices/events2",method=RequestMethod.POST)
    @ResponseBody
    public String iotEvent(@RequestBody String eventJson){
        return eventJson;
    }
}
