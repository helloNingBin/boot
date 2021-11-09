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
    /**
     * 第一个字母变大写
     */
    public static String toUpperFirstCase(String str){
        if(str.length() == 1){
            return str.toUpperCase();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 获取属性的set方法名称
     */
    public static String getFiledSetName(String fieldName){
        return "set" + toUpperFirstCase(fieldName);
    }
    /**
     * 获取属性的get方法名称
     */
    public static String getFiledgetName(String fieldName){
        return "get" + toUpperFirstCase(fieldName);
    }
}
