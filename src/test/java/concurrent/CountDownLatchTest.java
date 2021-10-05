package concurrent;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    static Map<String,Object> map = new HashMap<String,Object>();
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch c = new CountDownLatch(1000);
        c.countDown();
        for(int i = 0;i < 1000;i++){
            new Thread(new Runnable(){

                @Override
                public void run() {
                    c.countDown();
                    String count = ConcurrentUtil.getCount();
                    System.out.println(count);
                    if (map.containsKey(count)){
                        System.out.println("重复的Key：" + count);
                    }else{
                        map.put(count, "--");
                    }
                }
            }).start();
        }
        Thread.sleep(1500);
        System.out.println("================>" + ConcurrentUtil.getCount());
    }
}
