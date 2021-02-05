package langPackage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

@DisplayName(value = "dis")
public class AnnotationTest {
    @Test
    public void testGetAnnotationInClass(){
        Annotation[] declaredAnnotations = AnnotationTest.class.getDeclaredAnnotations();
        for(Annotation annotation : declaredAnnotations){
            System.out.println(annotation.annotationType());
            System.out.println(annotation instanceof DisplayName);
        }
    }
}
