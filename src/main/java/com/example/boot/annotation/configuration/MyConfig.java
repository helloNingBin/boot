package com.example.boot.annotation.configuration;

import com.example.boot.bean.Persion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 注解学习
 * 可以在AnnotationConfigApplicationContext(MyConfig.class);中获取
 * ApplicationContext app = new AnnotationConfigApplicationContext(MyConfig.class);
 * Bean的名称取自注解中的；value值，如果value值为空，则取自方法名称
 */
@Configuration
public class MyConfig {
    /**
     * prototype:原型，多例
     * singleton:单例
     * request : 主要应用于web模块，同一次请求只创建一个实例
     * session : 主要应用于web模块，同一个session只创建一个对象
     */
    @Scope("prototype")
    @Bean(initMethod = "starDay")
    public Persion ningbin(){
        return new Persion("ningbin", 18);
    }

    //--------------------------以下是conditiaonal注解的代码----------------------------------------

    /**
     * 按一定的条件进行判断，满足条件就给容器注册bean。
     * 假设：
     *    如果我的操作系统是window，那么就只加载chunmei
     *    是Linux，加载ningbin/xiaobao
     * 要实现Condition接口，在里写判断逻辑
     */
    @Conditional(WinCondition.class)
    @Scope("prototype")
    public Persion chunmei(){
        return new Persion("chunmei", 29);
    }
    @Scope("prototype")
    public Persion xiaobao(){
        return new Persion("xiaobao", 27);
    }
}
