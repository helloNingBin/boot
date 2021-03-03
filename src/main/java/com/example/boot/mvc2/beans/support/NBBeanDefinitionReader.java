package com.example.boot.mvc2.beans.support;

import com.example.boot.mvc2.beans.config.NBBeanDefinition;
import tools.FileUtil;
import tools.StringUtil;
import tools.WebUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * bean的解析/扫描器
 * 把指定包下的类信息封装成一个个NBBeanDefinition（类和类的接口），然后放到list中去
 *
 */
public class NBBeanDefinitionReader {
    //保存扫描的结果
    private List<String> regitryBeanClasses = new ArrayList<>();
    private Properties contextConfig = new Properties();

    public NBBeanDefinitionReader(String... configLocations){
        doLoadConfi(configLocations[0]);
        //扫描配置文件中的配置的相关的类
        doScanner(contextConfig.getProperty("scanPackage"));
    }

    /**
     * 解析类的别名和类名放到BeanDefinition中去
     */
    public List<NBBeanDefinition> loadBeanDefinitions(){
        List<NBBeanDefinition> result = new ArrayList<>();
        try {
            for(String className : regitryBeanClasses){
                Class<?> beanClass = Class.forName(className);
                Annotation[] annotations = beanClass.getDeclaredAnnotations();
                //只加载有注解的类
                if(!WebUtil.isNBAnnotation(annotations)){
                    continue;
                }
                result.add(doCreateBeanDefinition(StringUtil.toLowerFirstCase(beanClass.getSimpleName()), beanClass.getName()));
                //接口
                for (Class<?> i : beanClass.getInterfaces()){
                    result.add(doCreateBeanDefinition(i.getName(), beanClass.getName()));
                    System.out.println(className + "-----------" + i.getName() + "--------" + beanClass.getName());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param beanName       别名（IOC里的名称）
     * @param beanClassName  类名
     * @return
     */
    private NBBeanDefinition doCreateBeanDefinition(String beanName,String beanClassName){
        NBBeanDefinition beanDefinition = new NBBeanDefinition(beanName, beanClassName);
        return beanDefinition;

    }
    private void doScanner(String scanPackage) {
        String path = scanPackage.replaceAll("\\.","/");
        System.out.println("path:" + path);
        URL url = NBBeanDefinitionReader.class.getClassLoader().getResource(path);
        File classDir = new File(url.getFile());
        List<File> fileList = new ArrayList<File>();
        fileList = FileUtil.findAllList(classDir, fileList,".class");
        for (File file : fileList) {
            String fullName = file.getAbsolutePath().replace(".class","").replaceAll("\\\\","\\.");
            String clazzName = fullName.substring(fullName.indexOf(scanPackage));
            regitryBeanClasses.add(clazzName);
        }
    }

    private void doLoadConfi(String configLocation) {
        //直接从类路径下找到Spring主配置文件所在的路径
        //并且将其读取出来放到Properties对象中
        //相对于scanPackage=com.gupaoedu.demo 从文件中保存到了内存中
        InputStream fis = this.getClass().getClassLoader().getResourceAsStream(configLocation.replaceAll("classpath:",""));
        try {
            contextConfig.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != fis){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Properties getConfig() {
        return this.contextConfig;
    }
}
