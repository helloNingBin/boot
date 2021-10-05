package javaboy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UrlController {
    @RequestMapping("/par")
    public String par(HttpServletRequest request){
        System.out.println(request.getQueryString());
        return "";
    }
}
