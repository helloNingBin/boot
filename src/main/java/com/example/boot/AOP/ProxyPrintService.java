package com.example.boot.AOP;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 */
public class ProxyPrintService implements InvocationHandler {
    private PrintService defaultProxyService;

    public ProxyPrintService(PrintService defaultProxyService) {
        this.defaultProxyService = defaultProxyService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName());
        return method.invoke(defaultProxyService, args);
    }

    public Object getProxy(Class<?>[] interfaces){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return Proxy.newProxyInstance(classLoader ,interfaces, this);
    }
}
