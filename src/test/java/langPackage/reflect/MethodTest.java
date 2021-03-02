package langPackage.reflect;

import org.junit.jupiter.api.Test;
import tools.WebConstatns;
import tools.WebUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 *Method测试
 */
public class MethodTest {
    public void show(String name,Integer id){
        System.out.println("name:" + name + ";id" + id);
    }
    /**
     * 获取访求里每个参数的类型、名称
     */
    @Test
    public void MethodParamTest()throws Exception{
        MethodTest m = new MethodTest();
        Method[] methods = MethodTest.class.getDeclaredMethods();
        Map<String,String[]> paramMap = new HashMap<String,String[]>();
        paramMap.put("name",new String[]{"abc"});
        paramMap.put("id",new String[]{"123"});
        Map<String,Object> webParamMap = new HashMap<String,Object>();
        for(Method method : methods){
            if(method.getName().equals("show")){
                /*Object[] requestPramArray = WebUtil.getRequestPramArray(paramMap, method,null,null,null);
                System.out.println(requestPramArray);*/
            }
        }
    }
}
