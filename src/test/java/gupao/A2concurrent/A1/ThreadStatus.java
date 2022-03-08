package gupao.A2concurrent.A1;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author:chichao
 * @date:年月日
 */
public class ThreadStatus {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) {
        new Thread(()->{
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("current thread:" + Thread.currentThread().getName() + "==>" + sdf.format(new Date()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"TIME_WAITING").start();//TIMED_WAITING

        new Thread(()->{
            while(true){
                synchronized (ThreadStatus.class){
                    try {
                        System.out.println("current thread:" + Thread.currentThread().getName() + "==>" + sdf.format(new Date()));
                        ThreadStatus.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"WAITING");

        new Thread(new BlockDemo(),"BLOCKED-DEMO-01").start();//TIMED_WAITING
        new Thread(new BlockDemo(),"BLOCKED-DEMO-02").start();//BLOCKED
    }

    static class BlockDemo extends Thread{
        @Override
        public void run() {
            synchronized (BlockDemo.class){
                while(true){
                    try {
                        TimeUnit.SECONDS.sleep(10);
                        System.out.println("current thread:" + Thread.currentThread().getName() + "==>" + sdf.format(new Date()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
