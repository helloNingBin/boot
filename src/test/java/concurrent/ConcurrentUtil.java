package concurrent;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ConcurrentUtil {
    private static String[] concurrentOrderNo = {"",""};
    private static int count = 0;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    public synchronized static String getCount(){
        String key = sdf.format(new Date());
        return key + "====>" + getOrderNoSubfix(key);
    }
    public static Object getOrderNoSubfix(String key){
        if(concurrentOrderNo[0].equals(key)){
            concurrentOrderNo[1] = concurrentOrderNo[1] + "0";
        }else{
            concurrentOrderNo[0] = key;
            concurrentOrderNo[1] = "0";
        }
        return concurrentOrderNo[1].length();
    }
}
