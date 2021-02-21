package com.example.boot;

import org.junit.jupiter.api.Test;
import tools.CollectionUtil;

import java.util.*;

/**
 * 集合类测试
 */
public class CollectionTest {
    @Test
    public void modifyExpPutValue(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("1", null);
        map.put("2", null);
        for(Object key : CollectionUtil.cloneSet(map.keySet())){
            map.put(key+"df", "dfd");
        }
        System.out.println(map);
      /*  for(String key : strings){
            map.put(key+"df", "dfd");
        }*/
    }
}
