package com.example.boot;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 集合类测试
 */
public class CollectionTest {
    @Test
    public void modifyExpPutValue(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("1", null);
        map.put("2", null);
        for(String key : map.keySet()){
            map.put(key, "dfd");
        }
    }
}
