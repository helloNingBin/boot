package com.example.boot.mvc.servlet;

import com.example.boot.mvc.annotation.NBAutowired;
import com.example.boot.mvc.annotation.NBController;
import com.example.boot.mvc.annotation.NBRequestMapping;
import com.example.boot.mvc.annotation.NBService;
import tools.FileUtil;

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
            resp.getWriter().write("500 Exception " + Arrays.toString(e.getStackTrace()));
        }
    }
    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replace(contextPath, "").replaceAll("/+", "/");
        if(!this.mapping.containsKey(url)){resp.getWriter().write("404 Not Found!!");return;}
        Method method = (Method) this.mapping.get(url);
        Map<String,String[]> params = req.getParameterMap();
        method.invoke(this.mapping.get(method.getDeclaringClass().getName()),new Object[]{req,resp,params.get("name")[0]});
    }

    //当我晕车的时候，我就不去看源码了

    //init方法肯定干得的初始化的工作
    //inti首先我得初始化所有的相关的类，IOC容器、servletBean
    @Override
    public void init(javax.servlet.ServletConfig config) throws ServletException {
        InputStream is = null;
        try{
            Properties configContext = new Properties();
            is = this.getClass().getClassLoader().getResourceAsStream(config.getInitParameter("contextConfigLocation"));
            configContext.load(is);
            String scanPackage = configContext.getProperty("scanPackage");
            doScanner(scanPackage);
            for (String className : mapping.keySet()) {
                if(!className.contains(".")){continue;}
                Class<?> clazz = Class.forName(className);
                if(clazz.isAnnotationPresent(NBController.class)){
                    mapping.put(className,clazz.newInstance());
                    String baseUrl = "";
                    if (clazz.isAnnotationPresent(NBRequestMapping.class)) {
                        NBRequestMapping requestMapping = clazz.getAnnotation(NBRequestMapping.class);
                        baseUrl = requestMapping.value();
                    }
                    Method[] methods = clazz.getMethods();
                    for (Method method : methods) {
                        if (!method.isAnnotationPresent(NBRequestMapping.class)) {  continue; }
                        NBRequestMapping requestMapping = method.getAnnotation(NBRequestMapping.class);
                        String url = (baseUrl + "/" + requestMapping.value()).replaceAll("/+", "/");
                        mapping.put(url, method);
                        System.out.println("Mapped " + url + "," + method);
                    }
                }else if(clazz.isAnnotationPresent(NBService.class)){
                    NBService service = clazz.getAnnotation(NBService.class);
                    String beanName = service.value();
                    if("".equals(beanName)){beanName = clazz.getName();}
                    Object instance = clazz.newInstance();
                    mapping.put(beanName,instance);
                    for (Class<?> i : clazz.getInterfaces()) {
                        mapping.put(i.getName(),instance);
                    }
                }else {continue;}
            }
            for (Object object : mapping.values()) {
                if(object == null){continue;}
                Class clazz = object.getClass();
                if(clazz.isAnnotationPresent(NBController.class)){
                    Field [] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if(!field.isAnnotationPresent(NBAutowired.class)){continue; }
                        NBAutowired autowired = field.getAnnotation(NBAutowired.class);
                        String beanName = autowired.value();
                        if("".equals(beanName)){beanName = field.getType().getName();}
                        field.setAccessible(true);
                        try {
                            field.set(mapping.get(clazz.getName()),mapping.get(beanName));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(is != null){
                try {is.close();} catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.print("NB MVC Framework is init");
    }
    private void doScanner(String scanPackage) {
        //注意：不能以“/”开头
        String path = "" + "com.example.boot.mvc".replaceAll("\\.","/");
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

    public static void main(String[] args) throws IOException {
        String packagePath = "com.example.boot.mvc";
        String path = packagePath.replaceAll("\\.","/");
        System.out.println("path:" + path + "------" + File.separator + "------" + File.pathSeparator);
        URL url = NBDispatcherServlet.class.getClassLoader().getResource(path);
        System.out.println(url);
        File classDir = new File(url.getFile());
        List<File> fileList = new ArrayList<File>();
        fileList = FileUtil.findAllList(classDir, fileList,".class");
        for (File file : fileList) {
        }
    }
}
