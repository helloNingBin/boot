package langPackage;

import java.lang.annotation.*;

@Target(ElementType.TYPE)//指定适用范围类，方法，属性，参数等，可指定多个
@Retention(RetentionPolicy.SOURCE)//只有定义为RetentionPolicy.RUNTIME时，我们才能通过注解反射获取到注解。
@Documented
public @interface AnnotionSource {
    String value() default "";
}
