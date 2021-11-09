package com.example.boot.mvc.action;

import com.example.boot.annotation.*;
import com.example.boot.mvc.service.inter.IAService;
import com.example.boot.mvc.service.inter.IDemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@NBController
@NBRequestMapping("/nbmvc/demo")
public class DemoAction {
    @NBAutowired
    private IDemoService demoService;
    private IDemoService demoService2;

    @NBAutowired
    private IAService aService;

    public void print(){
        aService.print();
    }
    @NBRequestMapping("/querya")
    public String query(HttpServletRequest request, HttpServletResponse response,@NBRequestParam("dname") String name,@NBRequestParam(defalutValue = "123")String id){
        String result = null;
        if(demoService != null){
            result = demoService.get(name) + ";id : " + id;
        }else{
            result = "name:" + name  + ";id : " + id;
        }
        return "/xxx/ssss/dddd/" + result;
    }
    @NBRequestMapping("/add")
    public void add(HttpServletRequest request, HttpServletResponse response,@NBRequestParam(value = "dname",defalutValue = "dval") String name,@NBRequestParam("did") String id){
        try {
            response.getWriter().write(demoService.add(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @NBRequestMapping("/remove")
    @NBResponseBody
    public Map<String,Object> remove(HttpServletRequest request, HttpServletResponse response,@NBRequestParam("dname") String name,@NBRequestParam("did") String id){
        Map<String,Object> result = new HashMap<>();
        try {
            String removeResult = demoService.remove(id);
            result.put("msg", removeResult);
            result.put("state", true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("state", false);
            result.put("msg", e.getMessage());
        }
        return result;
    }
}
