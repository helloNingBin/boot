package com.example.boot.AOP.cglib;

/**
 * @author:chichao
 * @date:年月日
 */
public class BaseService {
    public String print(String msg){
        String pringMsg = "baseService----->" + msg;
        System.out.println(pringMsg);
        return pringMsg;
    }
}
