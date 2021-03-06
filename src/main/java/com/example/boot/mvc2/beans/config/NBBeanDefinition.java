package com.example.boot.mvc2.beans.config;

/**
 * 配置封装模块
 */
public class NBBeanDefinition {
    private String beanClassName;
    private String factoryBeanName;

    /**
     * @param factoryBeanName   别名（IOC里的名称）
     * @param beanClassName     类名
     */
    public NBBeanDefinition(String factoryBeanName, String beanClassName) {
        if("com.example.boot.mvc2.config.CommonConfig".equals(factoryBeanName)){
            System.out.println(beanClassName);
        }
        this.beanClassName = beanClassName;
        this.factoryBeanName = factoryBeanName;
    }

    public NBBeanDefinition() {
    }

    public String getBeanClassName() {
        return beanClassName;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    @Override
    public String toString() {
        return "NBBeanDefinition{" +
                "beanClassName='" + beanClassName + '\'' +
                ", factoryBeanName='" + factoryBeanName + '\'' +
                '}';
    }

    public String getFactoryBeanName() {
        return factoryBeanName;
    }

    public void setFactoryBeanName(String factoryBeanName) {
        this.factoryBeanName = factoryBeanName;
    }
}
