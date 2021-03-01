package com.example.boot.mvc2.servlet;

import com.example.boot.annotation.NBRequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * handlerMapping的处理器
 * 带有adapter的都是逻辑处理的
 */
public class NBHandlerAdapter {
    public NBModelAndView handler(HttpServletRequest request, HttpServletResponse response,NBHandlerMapping handler){
        Method method = handler.getMethod();
        //获取形参列表
        Class<?> [] parameterTypes = method.getParameterTypes();
        Object [] paramValues = new Object[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            Class paramterType = parameterTypes[i];
            if(paramterType == HttpServletRequest.class){
                paramValues[i] = request;
            }else if(paramterType == HttpServletResponse.class){
                paramValues[i] = response;
            }else if(paramterType == HttpSession.class){
                paramValues[i] = request.getSession();
            }else if(paramterType == String.class){
                //通过运行时的状态去拿到你
                Annotation[] [] pa = method.getParameterAnnotations();
                for (int j = 0; j < pa.length ; j ++) {
                    for(Annotation a : pa[i]){
                        if(a instanceof NBRequestParam){
                            String paramName = ((NBRequestParam) a).value();
                            if(!"".equals(paramName.trim())){
                                String value = Arrays.toString(params.get(paramName))
                                        .replaceAll("\\[|\\]","")
                                        .replaceAll("\\s+",",");
                                paramValues[i] = value;
                            }
                        }
                    }
                }

            }
        }
    }
}
