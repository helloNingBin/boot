package com.example.boot.mvc2.context;

import com.example.boot.mvc2.beans.NBBeanWrapper;
import com.example.boot.mvc2.beans.config.NBBeanDefinition;
import com.example.boot.mvc2.beans.support.NBBeanDefinitionReader;
import org.springframework.beans.factory.BeanExpressionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 按之前源码分析的套路，IOC、ID、MVC、AOP
 *
 * 完成Bean的创建和DI
 */
public class NBApplicationContext {
    private NBBeanDefinitionReader reader;
    private Map<String, NBBeanDefinition> beanDefinitionMap = new HashMap<>();
    private Map<String, NBBeanWrapper> factoryBeanInstanceCache = new HashMap<>();
    private Map<String,Object> factoryBeanObjectCache = new HashMap<String, Object>();
    public NBApplicationContext(String... configLocations){
        //1.加载文件配置
        reader = new NBBeanDefinitionReader(configLocations);
        try {
            //2.解析配置文件，封装成BeanDefinition
            List<NBBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
            //3.把BeanDefinition缓存起来
            doRegistBeanDefinition(beanDefinitions);
            
            doAutowrited();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void doAutowrited() {
        //调用getBean()
        //这一步，所有的Bean并没有真正的实例化，还只是配置阶段。
        for(Map.Entry<String,NBBeanDefinition> entry : this.beanDefinitionMap.entrySet()){
            String beanName = entry.getKey();
            getBean(beanName);
        }
    }

    private void getBean(String beanName) {
    }

    private void doRegistBeanDefinition(List<NBBeanDefinition> beanDefinitions) {
        for(NBBeanDefinition beanDefinition : beanDefinitions){
            if(this.beanDefinitionMap.containsKey(beanDefinition.getBeanClassName())){
                throw new BeanExpressionException(beanDefinition.getBeanClassName() + " is exists!");
            }
            //全类名和别名都一起放进去
            this.beanDefinitionMap.put(beanDefinition.getBeanClassName(),beanDefinition);
            this.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }
}
