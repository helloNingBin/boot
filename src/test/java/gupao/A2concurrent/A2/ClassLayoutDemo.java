package gupao.A2concurrent.A2;

import org.openjdk.jol.info.ClassLayout;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

public class ClassLayoutDemo {
    private BigDecimal b = new BigDecimal(399);
    private StringBuilder sb = new StringBuilder();
    public  void s(){
        synchronized(this){

        }
    }

    public static void main(String[] args) {
        ClassLayoutDemo demo = new ClassLayoutDemo();
        System.out.println(ClassLayout.parseClass(ClassLayoutDemo.class).toPrintable());
        System.out.println(ClassLayout.parseInstance(demo).toPrintable());
    }
}
