package com.example.boot.mvc2.aop;

import com.example.boot.mvc2.aop.aspect.NBAdvice;
import com.example.boot.mvc2.aop.support.NBAdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 *
 */
public class NBJdkDynamicAopProxy implements InvocationHandler {
    private NBAdvisedSupport config;
    public NBJdkDynamicAopProxy(NBAdvisedSupport config){
        this.config = config;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Map<String, NBAdvice> advices = config.getAdvices(method, null);
        Object returnValue;
        try {
            invokeAdvice(advices.get("before"));
            returnValue = method.invoke(this.config.getTarget(), args);
            invokeAdvice(advices.get("after"));
        }catch (Exception e){
            invokeAdvice(advices.get("afterThrow"));
            throw e;
        }
        return returnValue;
    }
    private void invokeAdvice(NBAdvice advice){
        try {
            advice.getAdviceMethod().invoke(advice.getAspect());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public Object getProxy(){
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),this.config.getTargetClass().getInterfaces(),this);
    }
}
