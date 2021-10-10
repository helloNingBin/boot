package com.example.boot.mvc.beans;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author:chichao
 * @date:年月日
 */
public class UrlMapping {
    private Pattern pattern;//URL
    private Method method;//对应的method
    private Object controller;//Method对应的实例对象
    private Class[] paramTypeArray;
    private String[] paramNameArrray;//参数名称，默认值也放在一起，用“=”隔开
    private String methodType;
    private boolean isRestful;

    public UrlMapping(Pattern pattern, Method method, Object controller, Class[] paramTypeArray, String[] paramNameArrray, String methodType,boolean isRestful) {
        this.pattern = pattern;
        this.method = method;
        this.controller = controller;
        this.paramTypeArray = paramTypeArray;
        this.paramNameArrray = paramNameArrray;
        this.methodType = methodType;
        this.isRestful = isRestful;
    }
    public Object invoke(Object[] requestPramValueArray)throws Exception{
        return this.method.invoke(this.controller,requestPramValueArray);
    }

    @Override
    public String toString() {
        return "UrlMapping{" +
                "pattern=" + pattern +
                ", method=" + method +
                ", controller=" + controller +
                ", paramTypeArray=" + Arrays.toString(paramTypeArray) +
                ", paramNameArrray=" + Arrays.toString(paramNameArrray) +
                ", methodType='" + methodType + '\'' +
                ", isRestful=" + isRestful +
                '}';
    }

    public boolean isRestful() {
        return isRestful;
    }

    public void setRestful(boolean restful) {
        isRestful = restful;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Class[] getParamTypeArray() {
        return paramTypeArray;
    }

    public void setParamTypeArray(Class[] paramTypeArray) {
        this.paramTypeArray = paramTypeArray;
    }

    public String[] getParamNameArrray() {
        return paramNameArrray;
    }

    public void setParamNameArrray(String[] paramNameArrray) {
        this.paramNameArrray = paramNameArrray;
    }

}
