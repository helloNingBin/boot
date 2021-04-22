package com.example.boot.AOP.cglib;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 */
public class CglibApp {
    public static void main(String[] args) throws Exception{
        // 代理类class文件存入本地磁盘方便我们反编译查看源码
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "F:\\code");
        // 通过CGLIB动态代理获取代理对象的过程
        Enhancer enhancer = new Enhancer();
        // 设置enhancer对象的父类
        enhancer.setSuperclass(BaseService.class);
        // 设置enhancer的回调对象
        enhancer.setCallback(new ServiceMethodInterceptor());
        // 创建代理对象
        BaseService proxy= (BaseService)enhancer.create();
        // 通过代理对象调用目标方法
        proxy.print("err");

        springEnhancer();
    }
    private static void springEnhancer()throws Exception{
        org.springframework.cglib.proxy.Enhancer enhancer = new org.springframework.cglib.proxy.Enhancer();
        enhancer.setSuperclass(BaseService.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
               System.out.println("before------------");
//               Object result = methodProxy.invokeSuper(o,objects);
                //为什么这样调用会递归无限循环呢？因为此时的object是新生成的代理类，它所执行的方法都会被intecept拦截。
                Object result = method.invoke(o,objects);
               System.out.println("after-----------------");
                return null;
            }
        });
        BaseService o = (BaseService) enhancer.create();
        o.print("proxy");
    }
}
