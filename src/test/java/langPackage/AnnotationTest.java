package langPackage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@DisplayName(value = "dis")
@AnnotationRuntime(value = "AnnotionSource")
public class AnnotationTest {
    @Autowired
    private AnnotationService annotationService;

    @Test
    public void testGetAnnotationInClass(){
        Annotation[] declaredAnnotations = AnnotationTest.class.getDeclaredAnnotations();
        for(Annotation annotation : declaredAnnotations){
            System.out.println(annotation.annotationType());
            System.out.println(annotation instanceof DisplayName);
        }
    }

    @Test
    public void annotationSource() throws InterruptedException {
        System.out.println(140+86.0+256+157.0+177+69.0+330+46.0+281);
    }
}
