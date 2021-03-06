package langPackage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;

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
    public void annotationSource(){

    }
}
