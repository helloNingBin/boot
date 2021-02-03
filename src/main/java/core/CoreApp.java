package core;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class CoreApp {
    public static void main(String[] args) {
       /* ApplicationContext beanFactory = new ClassPathXmlApplicationContext("applications.xml");
        CoreBean coreBean = (CoreBean) beanFactory.getBean("coreBean");
        BigCoreBean bigCoreBean = (BigCoreBean) beanFactory.getBean("bigCoreBean");
        System.out.println(coreBean);
        System.out.println(bigCoreBean);
        Resource resource = beanFactory.getResource("");
        System.out.println("-------->" + resource.getFilename());*/
    }
}
