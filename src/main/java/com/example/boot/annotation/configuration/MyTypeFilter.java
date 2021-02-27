package com.example.boot.annotation.configuration;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * 自定义扫描规则
 */
public class MyTypeFilter implements TypeFilter {
    /**
     *
     * @param metadataReader  获取当前正在操作类的信息
     * @param metadataReaderFactory 获取上下文中所有的信息
     * @return
     * @throws IOException
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前类的所有的注解信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前扫描到的类的信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取到当前类的所有的资源
        Resource resource = metadataReader.getResource();

        String className = classMetadata.getClassName();

        System.out.println("-=-------------" + className + "--------------------------");

        //这里写判断逻辑
        if(className.indexOf("server") > -1){
            return true;
        }
        return false;
    }
}
