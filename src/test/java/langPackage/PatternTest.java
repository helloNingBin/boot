package langPackage;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.regex.Pattern;


/**
 * 正则表达式测试
 */
public class PatternTest {
    @Test
    public void baseTest(){
        Pattern pattern = Pattern.compile("query*a");
        System.out.println(pattern.matcher("querya").matches());
        ArrayList<String> list = new ArrayList<String>();
    }
    public static String encode(String str){
        StringBuilder sb = new StringBuilder();
        byte[] bytes = str.getBytes();
        for(byte b : bytes){
            sb.append(Integer.toHexString(b));
        }

        return sb.toString();
    }
    @Test
    public  void six() throws UnsupportedEncodingException {
                    /*
                          1.帧头标志SYN：3A A3
                         2.保留字节RES：默认为0x 000000
                     */

                    System.out.println(Integer.toHexString(20));
        String str16 = encode("aabb");
        System.out.println(str16);
        String str = "3A A3 00 00 00 01 52 20 21 12 08 09 25 01 00 1D 09 01 52 00 00 01 03 08 00 00 00 01 52 20 21 12 08 09 25 01 00 00 00 00 00 00 01 01 00 AA B1";
        str = "3AA3000000015220211208092501001D0901520000010308000000015220211208092501000000000000010100AAB1";
        System.out.println(str.replaceAll(" ",""));

    }

    public int base() {
        return 3;
    }
}
