package com.example.boot.mvc.servlet;

import com.example.boot.annotation.NBAutowired;
import com.example.boot.annotation.NBController;
import com.example.boot.annotation.NBService;
import org.springframework.util.StringUtils;
import tools.FileUtil;
import tools.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * 在SpringBoot中使用Servlet的例子
 * 还必需在启动类加上@ServletComponentScan
 */
@WebServlet(name = "nbDispatcherServlet",urlPatterns = "/nbmvc/*",initParams = {
        @WebInitParam(name = "contextConfigLocation", value = "application.properties"),
        @WebInitParam(name = "mobile", value = "1232423434")})
public class NBDispatcherServlet extends HttpServlet {
    private Map<String,Object> regitryBeanClassMap = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {this.doPost(req,resp);}
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().write("----500 Exception " + Arrays.toString(e.getStackTrace()));
        }
    }
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

    //当我晕车的时候，我就不去看源码了

    //init方法肯定干得的初始化的工作
    //inti首先我得初始化所有的相关的类，IOC容器、servletBean
    @Override
    public void init(javax.servlet.ServletConfig config) throws ServletException {
        String scanPath = "";
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

        //3.DI处理

        //4.
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
            String clazzName = StringUtil.toLowerFirstCase(fullName.substring(fullName.indexOf(scanPackage)));
            //实例化，只保留Controller和Service
            Object newInstance = Class.forName(fullName).newInstance();
            boolean isSpringBeans = false;
            if(newInstance.getClass().isAnnotationPresent(NBService.class)){
                NBService nbService = newInstance.getClass().getDeclaredAnnotation(NBService.class);
                if(nbService.value() != null && !nbService.value().isEmpty()){
                    clazzName = nbService.value();
                }
                isSpringBeans = true;
            }else if(newInstance.getClass().isAnnotationPresent(NBController.class)){
                NBController nbController = newInstance.getClass().getDeclaredAnnotation(NBController.class);
                if(nbController.value() != null && !nbController.value().isEmpty()){
                    clazzName = nbController.value();
                }
                isSpringBeans = true;
            }
            if(isSpringBeans){
                if(regitryBeanClassMap.containsKey(clazzName)){
                    throw new Exception("bean[" + clazzName + "]已存在！");
                }
                regitryBeanClassMap.put(clazzName, newInstance);
            }
        }
    }
    private void doAutoWired()throws Exception{
        for(Map.Entry entry : this.regitryBeanClassMap.entrySet()){
            Object key = entry.getKey();
            Object value = entry.getValue();

        }
    }
}
