package com.example.boot.mvc.action;

import com.example.boot.mvc.annotation.NBAutowired;
import com.example.boot.mvc.annotation.NBController;
import com.example.boot.mvc.annotation.NBRequestMapping;
import com.example.boot.mvc.annotation.NBRequestParam;
import com.example.boot.mvc.service.inter.IDemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NBController
@NBRequestMapping("/nbmvc/demo")
public class DemoAction {
    @NBAutowired
    private IDemoService demoService;
    private IDemoService demoService2;
    @NBRequestMapping("/query")
    public void query(HttpServletRequest request, HttpServletResponse response,@NBRequestParam("dname") String name,@NBRequestParam("did") String id){
        String result = null;
        if(demoService != null){
            result = demoService.get(name) + ";id : " + id;
        }else{
            result = "name:" + name  + ";id : " + id;
        }
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @NBRequestMapping("/add")
    public void add(HttpServletRequest request, HttpServletResponse response,@NBRequestParam("dname") String name,@NBRequestParam("did") String id){
        try {
            response.getWriter().write(demoService.add(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @NBRequestMapping("/remove")
    public void remove(HttpServletRequest request, HttpServletResponse response,@NBRequestParam("dname") String name,@NBRequestParam("did") String id){
        try {
            response.getWriter().write(demoService.remove(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
