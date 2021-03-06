package com.example.boot.mvc2.aop.support;

import com.example.boot.mvc2.aop.aspect.NBAdvice;
import com.example.boot.mvc2.aop.config.NBAopConfig;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析AOP配置的工具类
 */
public class NBAdvisedSupport {
    private NBAopConfig config;
    private Object target;
    private Class targetClass;
    private Pattern pointCutClassPattern;
    private Map<Method,Map<String, NBAdvice>> methodCache;

    public NBAdvisedSupport(NBAopConfig config) {
        this.config = config;
    }

    /**
     * 解析配置文件的方法
     * 传入的目标被切入类，再根据aop表达式去判断是否符合条件（哪些方法需要被代理/添加逻辑）
     *
     */
    private void parse(){
        //把Spring的Excpress变成Java能够识别的表达式  public .* com\.gupaoedu\.vip\.demo\.service\..*Service\..*\(.*\)
        String pointCut = config.getPointCut()
                .replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");


        //保存专门匹配Class的正则   public .* com\.gupaoedu\.vip\.demo\.service\..*Service
        String pointCutForClassRegex = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);
        pointCutClassPattern = Pattern.compile("class " + pointCutForClassRegex.substring(pointCutForClassRegex.lastIndexOf(" ") + 1));


        //享元的共享池
        methodCache = new HashMap<Method, Map<String, NBAdvice>>();
        //保存专门匹配方法的正则
        Pattern pointCutPattern = Pattern.compile(pointCut);
        try{
            //切面类
            Class aspectClass = Class.forName(this.config.getAspectClass());
            //切面类里面的方法
            Map<String,Method> aspectMethods = new HashMap<String, Method>();
            for (Method method : aspectClass.getMethods()) {
                aspectMethods.put(method.getName(),method);
            }
            //目标类（被切入类）


            Map<String,NBAdvice> advices = this.config.getAdviceMap();
            for (Method method : this.targetClass.getMethods()) {
                String methodString = method.toString();
                if(methodString.contains("throws")){
                    methodString = methodString.substring(0,methodString.lastIndexOf("throws")).trim();
                }

                Matcher matcher = pointCutPattern.matcher(methodString);
                if(matcher.matches()){
                    //跟目标代理类的业务方法和Advices建立一对多个关联关系，以便在Porxy类中获得
                    methodCache.put(method,advices);
                }
            }


        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //根据一个目标代理类的方法，获得其对应的通知
    /**
     *得到一个切面集合，包含before方法、after方法等
     */
    public Map<String,NBAdvice> getAdvices(Method method, Object o) throws Exception {
        //享元设计模式的应用
        Map<String,NBAdvice> cache = methodCache.get(method);
        if(null == cache){
            Method m = targetClass.getMethod(method.getName(),method.getParameterTypes());
            cache = methodCache.get(m);
            this.methodCache.put(m,cache);
        }
        return cache;
    }

    //给ApplicationContext首先IoC中的对象初始化时调用，决定要不要生成代理类的逻辑
    public boolean pointCutMath() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
        parse();
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public Object getTarget() {
        return target;
    }























}
