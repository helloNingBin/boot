package com.example.boot.mvc2.servlet;

import com.example.boot.annotation.*;
import com.example.boot.mvc2.context.NBApplicationContext;
import tools.FileUtil;

import javax.servlet.ServletConfig;
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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 在SpringBoot中使用Servlet的例子
 * 还必需在启动类加上@ServletComponentScan
 */
@WebServlet(name = "nbDispatcherServlet3",urlPatterns = "/nbmvc3/*",initParams = {
        @WebInitParam(name = "contextConfigLocation", value = "application.properties"),
        @WebInitParam(name = "mobile", value = "1232423434")})
public class NBDispatcherServlet_v3 extends HttpServlet {
    private NBApplicationContext applicationContext;
    /**
     * 请求相关信息
     */
    private List<NBHandlerMapping> handlerMappings = new ArrayList<>();

    private Map<NBHandlerMapping,NBHandlerAdapter> handlerAdapters = new HashMap<>();

    private List<NBViewResolver> viewResolvers = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            doDispatch(req,resp);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //完成了对HandlerMapping的封装
        //完成了对方法返回值的封装ModelAndView

        //1、通过URL获得一个HandlerMapping
        NBHandlerMapping handler = getHandler(req);
        if(handler == null){
            processDispatchResult(req,resp,new NBModelAndView("404"));
            return;
        }

        //2、根据一个HandlerMaping获得一个HandlerAdapter
        NBHandlerAdapter ha = getHandlerAdapter(handler);

        //3、解析某一个方法的形参和返回值之后，统一封装为ModelAndView对象
        NBModelAndView mv = ha.handler(req,resp,handler);

        // 就把ModelAndView变成一个ViewResolver
        processDispatchResult(req,resp,mv);
    }
    private NBHandlerAdapter getHandlerAdapter(NBHandlerMapping handler) {
        if(this.handlerAdapters.isEmpty()){return null;}
        return this.handlerAdapters.get(handler);
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, NBModelAndView mv) throws Exception {
        if(null == mv){return;}
        if(this.viewResolvers.isEmpty()){return;}

        for (NBViewResolver viewResolver : this.viewResolvers) {
            NBView view = viewResolver.resolveViewName(mv.getViewName());
            //直接往浏览器输出
            view.render(mv.getModel(),req,resp);
            return;
        }
    }

    private NBHandlerMapping getHandler(HttpServletRequest req) {
        if(this.handlerMappings.isEmpty()){return  null;}
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath,"").replaceAll("/+","/");

        for (NBHandlerMapping mapping : handlerMappings) {
            Matcher matcher = mapping.getPattern().matcher(url);
            if(!matcher.matches()){continue;}
            return mapping;
        }
        return null;
    }
    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化spring核心IOC容器
        applicationContext = new NBApplicationContext(config.getInitParameter("contextConfigLocation"));

        //初始化九大组件
        initStrategies(applicationContext);
        System.out.println("NB Spring framework is init.");
    }

    /**
     * 九大核心组件
     * 1.多文件上传组件  initMultipartResolver(applicationContext)
     * 2.本地语言环境    initLocaleResolver(applicationContext)
     * 3.模板处理器      initThemeResolver(applicationContext)
     * 4.handlerMapping initHandlerMappings(applicationContext);
     * 5.参数适配器      initHandlerAdapters(applicationContext);
     * 6.异常拦截器      initHandlerExceptionResolvers(applicationContext);
     * 7.视图预处理器    initRequestToViewNameTranslator(applicationContext);
     * 8.视图转换器      initViewResolvers(applicationContext);
     * 9.FlashMap管理器  initFlashMapManager(applicationContext);
     */
    private void initStrategies(NBApplicationContext applicationContext) {
        //4.handlerMapping  initHandlerMappings(applicationContext);
        initHandlerMappings(applicationContext);
        //5.参数适配器        initHandlerAdapters(applicationContext);
        initHandlerAdapters(applicationContext);
        //8.视图转换器        initViewResolvers(applicationContext);
        initViewResolvers(applicationContext);
    }

    private void initViewResolvers(NBApplicationContext context) {
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();

        File templateRootDir = new File(templateRootPath);
        for (File file : templateRootDir.listFiles()) {
            this.viewResolvers.add(new NBViewResolver(templateRoot));
        }

    }

    private void initHandlerAdapters(NBApplicationContext context) {
        for (NBHandlerMapping handlerMapping : handlerMappings) {
            this.handlerAdapters.put(handlerMapping,new NBHandlerAdapter());
        }
    }
    private void initHandlerMappings(NBApplicationContext applicationContext) {
        if(this.applicationContext.getBeanDefinitionCount() == 0){ return;}

        for (String beanName : this.applicationContext.getBeanDefinitionNames()) {
            Object instance = applicationContext.getBean(beanName);
            Class<?> clazz = instance.getClass();

            if(!clazz.isAnnotationPresent(NBController.class)){ continue; }

            //相当于提取 class上配置的url
            String baseUrl = "";
            if(clazz.isAnnotationPresent(NBRequestMapping.class)){
                NBRequestMapping requestMapping = clazz.getAnnotation(NBRequestMapping.class);
                baseUrl = requestMapping.value();
            }

            //只获取public的方法
            for (Method method : clazz.getMethods()) {
                if(!method.isAnnotationPresent(NBRequestMapping.class)){continue;}
                //提取每个方法上面配置的url
                NBRequestMapping requestMapping = method.getAnnotation(NBRequestMapping.class);

                // //demo//query
                String regex = ("/" + baseUrl + "/" + requestMapping.value().replaceAll("\\*",".*")).replaceAll("/+","/");
                Pattern pattern = Pattern.compile(regex);
                //handlerMapping.put(url,method);
                handlerMappings.add(new NBHandlerMapping(pattern,instance,method));
                System.out.println("Mapped : " + regex + "," + method);
            }

        }
    }
}
