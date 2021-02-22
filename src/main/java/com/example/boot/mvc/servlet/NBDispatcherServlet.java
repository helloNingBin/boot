package com.example.boot.mvc.servlet;

import com.example.boot.mvc.annotation.NBAutowired;
import com.example.boot.mvc.annotation.NBController;
import com.example.boot.mvc.annotation.NBRequestMapping;
import com.example.boot.mvc.annotation.NBService;
import tools.CollectionUtil;
import tools.FileUtil;
import tools.WebUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    private Map<String,Object> mapping = new HashMap<String, Object>();
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
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        if(!this.mapping.containsKey(url)){
            resp.getWriter().write("404 Not Found!!");return;
        }
        HttpSession session = req.getSession();
        Method method = (Method) this.mapping.get(url);
        Map<String,String[]> params = req.getParameterMap();
        //整合请求参数
        Object[] requestPramArray = WebUtil.getRequestPramArray(params, method, req, resp, session);
//        method.getp
        method.invoke(this.mapping.get(method.getDeclaringClass().getName()),requestPramArray);
    }

    //当我晕车的时候，我就不去看源码了

    //init方法肯定干得的初始化的工作
    //inti首先我得初始化所有的相关的类，IOC容器、servletBean
    @Override
    public void init(javax.servlet.ServletConfig config) throws ServletException {

        //1、加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));

        //2、扫描相关的类
        doScanner(contextConfig.getProperty("scanPackage"));

        //3、初始化扫描到的类，并且将它们放入到ICO容器之中
        doInstance();

        //4、完成依赖注入
        doAutowired();

        //5、初始化HandlerMapping
        initHandlerMapping();

        System.out.println("NB Spring framework is init.");
    }
    private void doScanner(String scanPackage) {
        //注意：不能以“/”开头
        String path = scanPackage.replaceAll("\\.","/");
        System.out.println("path:" + path);
        URL url = NBDispatcherServlet.class.getClassLoader().getResource(path);
        File classDir = new File(url.getFile());
        List<File> fileList = new ArrayList<File>();
        fileList = FileUtil.findAllList(classDir, fileList,".class");
        for (File file : fileList) {
            String fullName = file.getAbsolutePath().replace(".class","").replaceAll("\\\\","\\.");
            String clazzName = fullName.substring(fullName.indexOf(scanPackage));
            mapping.put(clazzName,null);
        }
    }

    public static void main(String[] args) throws Exception {
        NBDispatcherServlet dispatcherServlet =new NBDispatcherServlet();
        dispatcherServlet.init(null);
    }
}
