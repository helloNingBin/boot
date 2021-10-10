package com.example.boot.mvc.servlet;

import com.example.boot.annotation.*;
import com.example.boot.mvc.action.DemoAction;
import com.example.boot.mvc.beans.UrlMapping;
import com.example.boot.mvc.service.AService;
import com.example.boot.mvc.service.BService;
import com.example.boot.mvc2.servlet.NBHandlerMapping;
import lombok.val;
import org.springframework.util.StringUtils;
import tools.FileUtil;
import tools.StringUtil;
import tools.WebUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 在SpringBoot中使用Servlet的例子
 * 还必需在启动类加上@ServletComponentScan
 */
@WebServlet(name = "nbDispatcherServlet",urlPatterns = "/nbmvc/*",initParams = {
        @WebInitParam(name = "contextConfigLocation", value = "application.properties"),
        @WebInitParam(name = "mobile", value = "1232423434")})
public class NBDispatcherServlet extends HttpServlet {
    private Map<String,Object> regitryBeanClassMap = new HashMap<>();
    private List<UrlMapping> urlMappingList = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {this.doPost(req,resp);}
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UrlMapping urlMapping = getHandler(req);
            if(urlMapping != null){
                Object o = doDispather(urlMapping, req, resp);
                doView(req, resp,urlMapping.isRestful(), o);
            }else{
                resp.getWriter().write("----404 Exception ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("----500 Exception " + Arrays.toString(e.getStackTrace()));
        }
    }

    //当我晕车的时候，我就不去看源码了

    //init方法肯定干得的初始化的工作
    //inti首先我得初始化所有的相关的类，IOC容器、servletBean
    @Override
    public void init(javax.servlet.ServletConfig config) throws ServletException {
        String scanPath = "com.example.boot.mvc";
        try {
            //1.读取配置文件，略
             //2.先实例化IOC容器
             initIOC(scanPath);
            //3.URL的Map


            System.out.println("NB Spring framework is init.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }

    }

    private void initIOC(String scanPath) throws Exception{
        //1.扫描所有注解的类
        doScanner(scanPath);
        //2.实例化
          //在第一步已经实例化了
        //3.DI处理
        doAutoWired();
        //4.doHandlerUrl
        doHandlerUrl();
    }



    private void doScanner(String scanPackage) throws Exception{
        String path = scanPackage.replaceAll("\\.","/");
        System.out.println("path:" + path);
        URL url = NBDispatcherServlet.class.getClassLoader().getResource(path);
        File classDir = new File(url.getFile());
        List<File> fileList = new ArrayList<File>();
        fileList = FileUtil.findAllList(classDir, fileList,".class");
        for (File file : fileList) {
            String fullName = file.getAbsolutePath().replace(".class","").replaceAll("\\\\","\\.");
            String clazzName = fullName.substring(fullName.indexOf(scanPackage));
            String simpleName = StringUtil.toLowerFirstCase(clazzName.substring(clazzName.lastIndexOf(".")+1));
            //实例化，只保留Controller和Service
            Class<?> clazz = Class.forName(clazzName);
            Object newInstance = null;
            boolean isSpringBeans = false;
            if(clazz.isAnnotationPresent(NBService.class)){
                newInstance = clazz.newInstance();
                NBService nbService = clazz.getDeclaredAnnotation(NBService.class);
                if(nbService.value() != null && !nbService.value().isEmpty()){
                    simpleName = nbService.value();
                }
                isSpringBeans = true;
                Class<?>[] interfaces = clazz.getInterfaces();
                if(interfaces != null && interfaces.length > 0){
                    for (Class<?> c : interfaces){
                        regitryBeanClassMap.put(c.getName(), newInstance);
                    }
                }

            }else if(clazz.isAnnotationPresent(NBController.class)){
                newInstance = clazz.newInstance();
                NBController nbController = clazz.getDeclaredAnnotation(NBController.class);
                if(nbController.value() != null && !nbController.value().isEmpty()){
                    simpleName = nbController.value();
                }
                isSpringBeans = true;
            }
            if(isSpringBeans){
                if(regitryBeanClassMap.containsKey(simpleName)){
                    throw new Exception("bean[" + simpleName + "]已存在！");
                }
                regitryBeanClassMap.put(simpleName, newInstance);
            }
        }
    }
    private void doAutoWired()throws Exception{
        System.out.println("begin doAutowired");
        for(Map.Entry entry : this.regitryBeanClassMap.entrySet()){
            Object key = entry.getKey();
            Object value = entry.getValue();
            Field[] declaredFields = value.getClass().getDeclaredFields();
            if(declaredFields != null && declaredFields.length > 0){
                for(Field f : declaredFields){
                    NBAutowired autowiredField = f.getDeclaredAnnotation(NBAutowired.class);
                    if(autowiredField != null){
                        String autowiredValue = autowiredField.value();
                        if(StringUtils.isEmpty(autowiredValue)){
                            String typeName = f.getType().getTypeName();
                            System.out.println("typeName:" + typeName);
                            autowiredValue = typeName;
                        }
                        f.setAccessible(true);
                        f.set(value,regitryBeanClassMap.get(autowiredValue));
                        System.out.println("autowiredValue:" + regitryBeanClassMap.get(autowiredValue));
                    }
                }
            }
        }
    }

    private void doHandlerUrl() throws Exception {
        for(Map.Entry entry : this.regitryBeanClassMap.entrySet()){
            Object key = entry.getKey();
            Object value = entry.getValue();
            NBController nbController = value.getClass().getDeclaredAnnotation(NBController.class);
            if(nbController != null){
                  //获取controller的URL前缀
                NBRequestMapping controllerRequestMapping = value.getClass().getDeclaredAnnotation(NBRequestMapping.class);
                String urlPrefix = "";
                if(controllerRequestMapping != null){
                    urlPrefix = controllerRequestMapping.value();
                }
                  //获取所有的RequestMapping方法
                  for(Method method : value.getClass().getDeclaredMethods()){
                      NBRequestMapping nbRequestMapping = method.getAnnotation(NBRequestMapping.class);
                      if(nbRequestMapping != null){
                          String url = urlPrefix + nbRequestMapping.value();
                          String methodType = nbRequestMapping.methodType();
                          //方法参数
                          Parameter[] parameters = method.getParameters();
                          Class[] paramTypeArray = new Class[parameters.length];
                          String[] paramNameArrray = new String[parameters.length];//参数名称，默认值也放在一起，用“=”隔开
                          int paramIndex = 0;
                          Annotation[][] parameterAnnotations = method.getParameterAnnotations();//参数注解
                          for(Parameter parameter : parameters){
                              paramTypeArray[paramIndex] = Class.forName(parameter.getType().getTypeName());
                              paramNameArrray[paramIndex] = WebUtil.getParamNameAndDefalutVlaue(parameter.getDeclaredAnnotation(NBRequestParam.class),parameter.getName());
                              System.out.println("typeName:" + paramTypeArray[paramIndex] + "------->" + paramNameArrray[paramIndex]);
                              paramIndex++;
                          }
                          UrlMapping urlMapping = new UrlMapping(Pattern.compile(url), method, value, paramTypeArray, paramNameArrray, methodType,method.isAnnotationPresent(NBResponseBody.class));
                          urlMappingList.add(urlMapping);
                      }
                  }
            }
        }
    }
    private UrlMapping getHandler(HttpServletRequest req) {
        if(this.urlMappingList.isEmpty()){return  null;}
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath,"").replaceAll("/+","/");
        System.out.println("request url:" + url);
        for (UrlMapping mapping : urlMappingList) {
            Matcher matcher = mapping.getPattern().matcher(url);
            if(!matcher.matches()){continue;}
            return mapping;
        }
        return null;
    }
    private Object doDispather(UrlMapping urlMapping,HttpServletRequest request,HttpServletResponse response)throws Exception{
        String method = request.getMethod();
        String urlMethodType = urlMapping.getMethodType();
        if("post".equalsIgnoreCase(urlMethodType) && !urlMethodType.equalsIgnoreCase(method)){
            throw new Exception("must post request!");
        }
        Object[] requestPramValueArray = WebUtil.getRequestPramArray(urlMapping, request, response);//方法的参数值
        return urlMapping.invoke(requestPramValueArray);
    }
    private void doView(HttpServletRequest request,HttpServletResponse response,boolean isRestful,Object result)throws Exception{
        response.getWriter().write("----isRestful:" + isRestful + ";result:" + result);
    }
    public static void main(String[] args)throws Exception {
        NBDispatcherServlet d = new NBDispatcherServlet();
        d.init(null);
        System.out.println(d.regitryBeanClassMap);
        for(UrlMapping url : d.urlMappingList){

            System.out.println("url====>" + url);
        }
        DemoAction demoAction = (DemoAction) d.regitryBeanClassMap.get("demoAction");
        demoAction.print();
    }
}
