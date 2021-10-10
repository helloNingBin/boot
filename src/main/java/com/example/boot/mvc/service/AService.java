package com.example.boot.mvc.service;

import com.example.boot.annotation.NBAutowired;
import com.example.boot.annotation.NBService;
import com.example.boot.mvc.service.inter.IAService;
import com.example.boot.mvc.service.inter.IBService;

/**
 * @author:chichao
 * @date:年月日
 */
@NBService
public class AService implements IAService {
    @NBAutowired
    private IBService bService;
    public void print(){
        System.out.println("IBService:" + bService);
    }
}
