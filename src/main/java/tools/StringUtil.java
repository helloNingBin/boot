package tools;

/**
 *字符串工具
 */
public class StringUtil {
    /**
     * 第一个字母变小写
     */
    public static String toLowerFirstCase(String str){
        if(str.length() == 1){
            return str.toLowerCase();
        }
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
}
