package com.example.boot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/nb")
public class DispatcherController {
    @GetMapping(value = "/u/{id}")
    public Map findById(@PathVariable int id){
        Map map = new HashMap();
        map.put("id",id);
        map.put("u","nb");
        return map;
    }
}
