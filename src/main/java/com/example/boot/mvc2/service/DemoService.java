package com.example.boot.mvc2.service;

import com.example.boot.annotation.NBService;
import com.example.boot.mvc2.service.inter.IDemoService;

@NBService
public class DemoService implements IDemoService {
    @Override
    public String get(String name) {
        return "My name is " + name;
    }

    @Override
    public String add(String name) {
        return "had add :" + name;
    }

    @Override
    public String remove(String id) {
        return "had remove id : " + id;
    }
}
