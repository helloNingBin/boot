package com.example.boot.mvc2.servlet;

import com.example.boot.annotation.NBRequestParam;
import tools.WebUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * handlerMapping的处理器
 * 带有adapter的都是逻辑处理的
 */
public class NBHandlerAdapter {
    /**
     * 解析请求参数
     * 处理传过来的参数，运行method，把得到的结果封装成ModelAndView
     */
    public NBModelAndView handler(HttpServletRequest request, HttpServletResponse response,NBHandlerMapping handler) throws Exception {
        Method method = handler.getMethod();
        //获取形参列表
        Class<?> [] parameterTypes = method.getParameterTypes();
        Object [] paramValues = WebUtil.getRequestPramArray(handler.getMethod(),request,response);
        Object result = handler.getMethod().invoke(handler.getController(), paramValues);
        if(result == null || result instanceof Void){
            return null;
        }
        boolean isModelAndView = handler.getMethod().getReturnType() == NBModelAndView.class;
        if(isModelAndView){
            return (NBModelAndView) result;
        }
        return null;
    }
}
