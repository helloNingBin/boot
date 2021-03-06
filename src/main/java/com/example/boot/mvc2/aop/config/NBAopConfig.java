package com.example.boot.mvc2.aop.config;

import com.example.boot.mvc2.aop.aspect.NBAdvice;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *切面的配置信息
 */
@Data
public class NBAopConfig {
    /**
     * 切入点
     */
    private String pointCut;
    /**
     * 切面的类
     */
    private String aspectClass;
    /**
     * 切面的before方法
     */
    private String aspectBefore;
    /**
     * 切面的after方法
     */
    private String aspectAfter;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;

    public String getPointCut() {
        return pointCut;
    }

    public void setPointCut(String pointCut) {
        this.pointCut = pointCut;
    }

    public String getAspectClass() {
        return aspectClass;
    }

    public void setAspectClass(String aspectClass) {
        this.aspectClass = aspectClass;
    }

    public String getAspectBefore() {
        return aspectBefore;
    }

    public void setAspectBefore(String aspectBefore) {
        this.aspectBefore = aspectBefore;
    }

    public String getAspectAfter() {
        return aspectAfter;
    }

    public void setAspectAfter(String aspectAfter) {
        this.aspectAfter = aspectAfter;
    }

    public String getAspectAfterThrow() {
        return aspectAfterThrow;
    }

    public void setAspectAfterThrow(String aspectAfterThrow) {
        this.aspectAfterThrow = aspectAfterThrow;
    }

    public String getAspectAfterThrowingName() {
        return aspectAfterThrowingName;
    }

    public void setAspectAfterThrowingName(String aspectAfterThrowingName) {
        this.aspectAfterThrowingName = aspectAfterThrowingName;
    }

    /**
     * 把一个切面信息封装面一个个Advice，放到map中去
     */
    public Map<String, NBAdvice> getAdviceMap() throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        //切面类
        Class aspectClass = Class.forName(this.getAspectClass());
        //切面类里面的方法
        Map<String, Method> aspectMethods = new HashMap<String, Method>();
        for (Method method : aspectClass.getMethods()) {
            aspectMethods.put(method.getName(),method);
        }
        Map<String, NBAdvice> advices = new HashMap<String, NBAdvice>();
        if(!(null == this.getAspectBefore() || "".equals(this.getAspectBefore()))){
            advices.put("before",new NBAdvice(aspectClass.newInstance(),aspectMethods.get(this.getAspectBefore())));
        }
        if(!(null == this.getAspectAfter() || "".equals(this.getAspectAfter()))){
            advices.put("after",new NBAdvice(aspectClass.newInstance(),aspectMethods.get(this.getAspectAfter())));
        }
        if(!(null == this.getAspectAfterThrow() || "".equals(this.getAspectAfterThrow()))){
            NBAdvice advice = new NBAdvice(aspectClass.newInstance(),aspectMethods.get(this.getAspectAfterThrow()));
            advice.setThrowName(this.getAspectAfterThrowingName());
            advices.put("afterThrow",advice);
        }
        return advices;
    }
}
