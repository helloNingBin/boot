package com.example.boot.mvc.service;

import com.example.boot.mvc.service.inter.IDemoService;

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
