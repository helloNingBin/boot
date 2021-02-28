package com.example.boot.mvc2.servlet;

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
    //保存application.properties配置文件中的内容
    private Properties contextConfig = new Properties();
    //保存扫描的所有的类名
    private List<String> classNames = new ArrayList<>();
    //IOC容器
    private Map<String,Object> ioc = new HashMap<>();
    //思考：为什么不用Map
    //你用Map的话，key，只能是url
    //Handler 本身的功能就是把url和method对应关系，已经具备了Map的功能
    //根据设计原则：冗余的感觉了，单一职责，最少知道原则，帮助我们更好的理解
    private List<NBDispatcherServlet_v3.Handler> handlerMapping = new ArrayList<NBDispatcherServlet_v3.Handler>();
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


        try {
            //1、加载配置文件
            doLoadConfig(config.getInitParameter("contextConfigLocation"));

            //2、扫描相关的类
            doScanner(contextConfig.getProperty("scanPackage"));

            //3、初始化扫描到的类，并且将它们放入到ICO容器之中
            doInstance();
            //4、完成依赖注入
            doAutowired();

            //5、初始化HandlerMapping
          //  initHandlerMapping();

            System.out.println("NB Spring framework is init.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }

    }

    /**
     * DI
     * 给属性注入值
     */
    private void doAutowired() {
        try {
            for(Object beanInstance : this.ioc.values()){
                //判断注解类型
                if(beanInstance.getClass().isAnnotationPresent(NBController.class)){
                    //获取带有Autowited注解的属性
                    Field[] fields = beanInstance.getClass().getDeclaredFields();
                    for(Field field : fields){
                        NBAutowired autowired = field.getAnnotation(NBAutowired.class);
                        if(autowired != null){
                            //从ioc容器中获取值，赋值给属性
                            Class<?> fieldType = field.getType();
                            Object obj = ioc.get(field.getName());
                            if(obj != null){
                                field.setAccessible(true);
                                field.set(beanInstance,obj);
                            }
                        }
                    }

                }

            }
        }catch (Exception e){

        }
    }

    /**
     * 把带有注解的放进到IOC容器中
     * service注解的还要把它的接口也放进去ioc容器中
     */
    private void doInstance() throws Exception {
         //不用判断classNames是否为空，因为作为一个框架，这必定是有值的。所以这可以直接使用
        for(String className : classNames){
            //不是所有的类都需要实例化，先判断是有注解后再实例化
            Class<?> clazz = Class.forName(className);
            //判断注解类型
            if(clazz.isAnnotationPresent(NBController.class)){//controller注解
                Object instance = clazz.newInstance();
                String beanName = clazz.getAnnotation(NBController.class).value();
                if(StringUtils.isEmpty(beanName)){
                    beanName = StringUtil.toLowerFirstCase(beanName);
                }
                Object oriKey = ioc.put(beanName, instance);
                if(oriKey != null){
                    throw new Exception(beanName + "，已存在IOC容器中！");
                }
            }else if(clazz.isAnnotationPresent(NBService.class)){//service注解
                Object instance = clazz.newInstance();
                String beanName = clazz.getAnnotation(NBService.class).value();
                if(StringUtils.isEmpty(beanName)){
                    beanName = StringUtil.toLowerFirstCase(beanName);
                }
                Object oriKey = ioc.put(beanName, instance);
                if(oriKey != null){
                    throw new Exception(beanName + "，已存在IOC容器中！");
                }
                //把接口也存到IOC容器中
                Class<?>[] interfaces = clazz.getInterfaces();
                for(Class<?> interfaceClass : interfaces){
                    Object oriInterfaceKey = ioc.put(interfaceClass.getName(), instance);
                    if(oriKey != null){
                        throw new Exception(interfaceClass.getName() + "，已存在IOC容器中！");
                    }
                }
            }
        }
    }

    private void doLoadConfig(String contextConfigLocation) {
        //classLoader的开始位置就位于jar包的class文件夹下
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

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
            classNames.add(clazzName);
        }
    }
    public class Handler{

    }
}
