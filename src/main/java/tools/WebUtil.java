package tools;

import com.example.boot.annotation.*;
import com.example.boot.mvc.beans.UrlMapping;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;

/**
 * web相关的工具
 */
public class WebUtil {
    public static Object[] getRequestPramArray( Method method,HttpServletRequest request,HttpServletResponse response)throws Exception{
        Map<String,String[]> paramMap = request.getParameterMap();
        HttpSession session = request.getSession();
        Parameter[] parameters = method.getParameters();
        Object[] paramterArray = new Object[parameters.length];
        int index = 0;
        for(Parameter p : parameters){
            if(p.getType().equals(HttpServletRequest.class)){//如果是HttpServletRequest
                paramterArray[index] = request;
            }else if(p.getType().equals(HttpServletResponse.class)){//如果是HttpServletResponse
                paramterArray[index] = response;
            }else if(p.getType().equals(HttpSession.class)){//如果是HttpSession
                paramterArray[index] = session;
            }else{
                //TODO 这里要逐个判断每一个类型吗？
                String paramName = p.getName();
                String defaultParamValue = null;
                //通过运行时的状态去拿到你
                Annotation[] [] pa = method.getParameterAnnotations();
                for(Annotation a : pa[index]){
                    if(a instanceof NBRequestParam){
                        String defaultParamName = ((NBRequestParam) a).value();
                        if(defaultParamName != null && !"".equals(defaultParamName.trim())){
                            paramName = defaultParamName;
                        }
                        defaultParamValue =   ((NBRequestParam) a).defalutValue();
                        break;
                    }
                }

                String[] values = paramMap.get(paramName);
                if(values != null){
                    paramterArray[index] = p.getType().getConstructor(String.class).newInstance(values[0]);
                }else{
                    paramterArray[index] = StringUtils.isEmpty(defaultParamValue) ? null : p.getType().getConstructor(String.class).newInstance(defaultParamValue);
                }
            }
            index++;
        }
        return paramterArray;
    }
    public static Object[] getRequestPramArray(UrlMapping urlMapping, HttpServletRequest request, HttpServletResponse response)throws Exception{
        Map<String,String[]> paramMap = request.getParameterMap();
        HttpSession session = request.getSession();
        Class[] paramTypeArray = urlMapping.getParamTypeArray();
        if(paramTypeArray == null || paramTypeArray.length == 0){
            return null;
        }
        String[] paramNameArrray=  urlMapping.getParamNameArrray();
        Object[] paramterArray = new Object[paramTypeArray.length];
        int index = 0;
         for(Class paramClass : paramTypeArray){
            if(paramClass.equals(HttpServletRequest.class)){//如果是HttpServletRequest
                paramterArray[index] = request;
            }else if(paramClass.equals(HttpServletResponse.class)){//如果是HttpServletResponse
                paramterArray[index] = response;
            }else if(paramClass.equals(HttpSession.class)){//如果是HttpSession
                paramterArray[index] = session;
            }else{
                //TODO 这里要逐个判断每一个类型吗？
                String paramName = paramNameArrray[index].split("=")[0];
                String defaultParamValue = null;
                if(paramNameArrray[index].split("=").length > 1){
                    defaultParamValue = paramNameArrray[index].split("=")[1];
                }

                String[] values = paramMap.get(paramName);
                if(values != null){
                    paramterArray[index] = paramClass.getConstructor(String.class).newInstance(values[0]);
                }else{
                    paramterArray[index] = StringUtils.isEmpty(defaultParamValue) ? null : paramClass.getConstructor(String.class).newInstance(defaultParamValue);
                }
            }
            index++;
        }

        return paramterArray;
    }
    /**
     * 判断是否自定义的注解
     */
    public static boolean isNBAnnotation(Annotation annotation){
        if(annotation instanceof NBController || annotation instanceof NBService){
            return true;
        }
        return false;
    }
    public static boolean isNBAnnotation(Annotation[] annotations){
        for(Annotation annotation : annotations){
            if(isNBAnnotation(annotation)){
                return true;
            }
        }
        return false;
    }

    /**
     *获取参数的名称和默认值，以“=”拼接起来
     */
    public static String getParamNameAndDefalutVlaue(NBRequestParam nBRequestParam,String paramName){
        String paramNameAndDefalutValue = "";
        if(nBRequestParam != null){
            if(!StringUtils.isEmpty(nBRequestParam.value())){
                paramName = nBRequestParam.value();
            }
            paramNameAndDefalutValue = paramName;
            if(!StringUtils.isEmpty(nBRequestParam.defalutValue())){
                paramNameAndDefalutValue += "=" + nBRequestParam.defalutValue();
            }
        }
        return paramNameAndDefalutValue;
    }
    public static void main(String[] args) {

    }
}
