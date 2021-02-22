package tools;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;

/**
 * web相关的工具
 */
public class WebUtil {
    public static Object[] getRequestPramArray(Map<String,String[]> paramMap, Method method,HttpServletRequest request,HttpServletResponse response,HttpSession session)throws Exception{
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
                String[] values = paramMap.get(p.getName());
                if(values != null){
                    paramterArray[index] = p.getType().getConstructor(String.class).newInstance(values[0]);
                }else{
                    parameters[index] = null;
                }
            }
            index++;
        }
        return paramterArray;
    }

    public static void main(String[] args) {

    }
}
