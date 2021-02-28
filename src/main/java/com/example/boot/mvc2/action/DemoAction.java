package com.example.boot.mvc2.action;

import com.example.boot.annotation.NBAutowired;
import com.example.boot.annotation.NBController;
import com.example.boot.annotation.NBRequestMapping;
import com.example.boot.annotation.NBRequestParam;
import com.example.boot.mvc2.service.inter.IDemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NBController
@NBRequestMapping("/nbmvc3/demo")
public class DemoAction {
    @NBAutowired
    private IDemoService demoService;
    private IDemoService demoService2;
    @NBRequestMapping("/query*a")
    public void query(HttpServletRequest request, HttpServletResponse response,@NBRequestParam("dname") String name,String id){
        String result = null;
        if(demoService != null){
            result = demoService.get(name) + ";id : " + id;
        }else{
            result = "name:" + name  + ";id : " + id;
        }
        try {
            response.getWriter().write(result + "------");
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
