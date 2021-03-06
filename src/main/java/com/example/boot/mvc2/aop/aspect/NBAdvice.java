package com.example.boot.mvc2.aop.aspect;

import lombok.Data;

import java.lang.reflect.Method;

/**
 *切面的类
 * 如记录日志、开启事务等
 */
@Data
public class NBAdvice {
    private Object aspect;
    private Method adviceMethod;
    private String throwName;

    public NBAdvice(Object aspect, Method adviceMethod) {
        this.aspect = aspect;
        this.adviceMethod = adviceMethod;
    }

    public Object getAspect() {
        return aspect;
    }

    public void setAspect(Object aspect) {
        this.aspect = aspect;
    }

    public Method getAdviceMethod() {
        return adviceMethod;
    }

    public void setAdviceMethod(Method adviceMethod) {
        this.adviceMethod = adviceMethod;
    }

    public String getThrowName() {
        return throwName;
    }

    public void setThrowName(String throwName) {
        this.throwName = throwName;
    }
}
