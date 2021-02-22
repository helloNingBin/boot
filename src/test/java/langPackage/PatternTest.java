package langPackage;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

/**
 * 正则表达式测试
 */
public class PatternTest {
    @Test
    public void baseTest(){
        Pattern pattern = Pattern.compile("query*a");
        System.out.println(pattern.matcher("querya").matches());
    }
}
