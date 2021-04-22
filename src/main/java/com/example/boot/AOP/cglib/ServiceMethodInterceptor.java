package com.example.boot.AOP.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author:chichao
 * @date:年月日
 */
public class ServiceMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("======插入前置通知======");
        BaseService baseService = new BaseService();

        Object object = proxy.invokeSuper(obj,args);
        System.out.println("======插入后者通知======");
        return object;
    }
}
