package com.example.boot.AOP;

import java.lang.reflect.Proxy;

/**
 * @author:chichao
 * @date:年月日
 */
public class ProxyApp {
    public static void main(String[] args) {

/*
        ProxyPrintService proxyPrintService = new ProxyPrintService(new DefaultProxyService());
        PrintService proxy = (PrintService) proxyPrintService.getProxy(new Class[]{PrintService.class});
        System.out.println(proxy.getClass());
        System.out.println(proxy.print("dfdf"));
*/


        // $Proxy1
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Object proxy2 = Proxy.newProxyInstance(classLoader,
                new Class[]{Comparable.class},
                (proxy1, method, args1) -> {
                    return null;
                });
        System.out.println(proxy2.getClass());

        ClassLoader classLoader3 = Thread.currentThread().getContextClassLoader();
        Object proxy3 = Proxy.newProxyInstance(classLoader3,
                new Class[]{Comparable.class},
                (proxy1, method, args1) -> {
                    return null;
                });
        System.out.println(proxy3.getClass());
    }
}
