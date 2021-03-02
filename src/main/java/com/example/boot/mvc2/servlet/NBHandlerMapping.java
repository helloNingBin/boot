package com.example.boot.mvc2.servlet;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 *一次请求有三个重要的因素
 * 1.请求路径
 * 2.在哪个类执行
 * 3.执行哪个方法
 */
public class NBHandlerMapping {
    private Pattern pattern;//URL
    private Method method;//对应的method
    private Object controller;//Method对应的实例对象

    public NBHandlerMapping() {
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

    public NBHandlerMapping(Pattern pattern, Object controller, Method method) {
        this.pattern = pattern;
        this.method = method;
        this.controller = controller;
    }
}
