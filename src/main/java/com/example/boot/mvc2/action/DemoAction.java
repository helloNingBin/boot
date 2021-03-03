package com.example.boot.mvc2.action;

import com.example.boot.annotation.NBAutowired;
import com.example.boot.annotation.NBController;
import com.example.boot.annotation.NBRequestMapping;
import com.example.boot.annotation.NBRequestParam;
import com.example.boot.mvc2.service.inter.IDemoService;
import com.example.boot.mvc2.servlet.NBModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@NBController
@NBRequestMapping("/nbmvc3/demo")
public class DemoAction {
    @NBAutowired
    private IDemoService demoService;
    private IDemoService demoService2;
    @NBRequestMapping("/query")
    public NBModelAndView query(HttpServletRequest request, HttpServletResponse response,@NBRequestParam("dname") String name,@NBRequestParam(defalutValue = "6543") String id){
        Map<String,Object> map = new HashMap<>();
        String result = null;
        if(demoService != null){
            result = demoService.get(name) + ";id : " + id;
        }else{
            result = "name:" + name  + ";id : " + id;
        }
        map.put("teacher",result);
        map.put("data", name);
        map.put("token", id);
       return new NBModelAndView("first",map);
    }
    @NBRequestMapping("/add")
    public NBModelAndView add(HttpServletRequest request, HttpServletResponse response, @NBRequestParam("dname") String name, @NBRequestParam("did") String id){
        try {
            response.getWriter().write(demoService.add(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
