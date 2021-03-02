package com.example.boot.mvc2.context;

import com.example.boot.annotation.NBAutowired;
import com.example.boot.annotation.NBController;
import com.example.boot.annotation.NBService;
import com.example.boot.mvc2.beans.NBBeanWrapper;
import com.example.boot.mvc2.beans.config.NBBeanDefinition;
import com.example.boot.mvc2.beans.support.NBBeanDefinitionReader;
import org.springframework.beans.factory.BeanExpressionException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 按之前源码分析的套路，IOC、ID、MVC、AOP
 *
 * 完成Bean的创建和DI
 */
public class NBApplicationContext {
    private NBBeanDefinitionReader reader;
    /**
     * key : beanDefinition's simpleName and full class name
     * valuel : beanDefinition
     */
    private Map<String, NBBeanDefinition> beanDefinitionMap = new HashMap<>();
    /**
     * IOC容器
     */
    private Map<String, NBBeanWrapper> factoryBeanInstanceCache = new HashMap<>();
    /**
     * key : beanDefinition's simpleName and full class name
     * valuel : instance
     */
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
        //这一步，所有的Bean并没有真正的实例化，还只是配置阶段。。
        for(Map.Entry<String,NBBeanDefinition> entry : this.beanDefinitionMap.entrySet()){
            String beanName = entry.getKey();
            getBean(beanName);
        }
    }
    //Bean的实例化，DI是从这个方法开始的
    public Object getBean(String beanName) {
        //1.先拿到BeanDefinition配置信息
        NBBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
        //2.反射实例化newInstance
        Object instance = instantiateBean(beanName,beanDefinition);
        //3.封装成一个叫做BeanWrapper
        NBBeanWrapper beanWrapper = new NBBeanWrapper(instance);
        //4.保存到IOC容器
        this.factoryBeanInstanceCache.put(beanName, beanWrapper);
        //5.执行依赖注入
        populateBean(beanName,beanDefinition,beanWrapper);

        return beanWrapper.getWrappedInstance();
    }

    private void populateBean(String beanName, NBBeanDefinition beanDefinition, NBBeanWrapper beanWrapper) {
        /**
         * 可能涉及到循环依赖
         * A｛B b}
         * B{A a"
         * 用两个缓存，循环两次
         * 1.把第一次读取结果为空的BeanDefinition存到第一个缓存
         * 2.等第一次循环之后，第二次循环再检查第一次的缓存，再进行赋值
         */
        Object instance = beanWrapper.getWrappedInstance();
        Class<?> clazz = beanWrapper.getWrappedClass();

        //相当于在Spring中@Component
        if(!clazz.isAnnotationPresent(NBController.class) || !clazz.isAnnotationPresent(NBService.class)){
            return;
        }
        //把所有的包括private/protected/default/public修饰字段都取出来
        for(Field field : clazz.getDeclaredFields()){
            if(!field.isAnnotationPresent(NBAutowired.class)){
                continue;
            }

            NBAutowired autowired = field.getAnnotation(NBAutowired.class);
            //如果用户没有自定义的beanName，就默认根据类型注入
            String autowiredBeanName = autowired.value().trim();
            if("".equals(autowiredBeanName)){
                autowiredBeanName = field.getType().getName();
            }
            //暴力访问
            field.setAccessible(true);

            try {
                if(this.factoryBeanInstanceCache.get(autowiredBeanName) == null){
                    continue;
                }
                field.set(instance, this.factoryBeanInstanceCache.get(autowiredBeanName));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建真正的实例对象
     * @param beanName  这个用来当作key放到factoryBeanObjectCache中去
     * @param beanDefinition
     */
    private Object instantiateBean(String beanName, NBBeanDefinition beanDefinition) {
        String className = beanDefinition.getBeanClassName();
        Object instance = null;
        try {
            Class<?> clazz = Class.forName(className);
            instance = clazz.newInstance();
            this.factoryBeanObjectCache.put(beanName, instance);
        }catch (Exception e){
            e.printStackTrace();
        }
        return instance;
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
    public Object getBean(Class beanClass){
        return getBean(beanClass.getName());
    }

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public Properties getConfig() {
        return this.reader.getConfig();
    }
}
