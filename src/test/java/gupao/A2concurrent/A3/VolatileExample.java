package gupao.A2concurrent.A3;

/**
 * @author:chichao
 * @date:年月日
 */
public class VolatileExample {
    public   static boolean stop=false;
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(()->{
            int i=0;
            while(!stop){ //load
                i++;//。。。。
                System.out.println("i:" + i);
            }
        });
        t1.start();
        System.out.println("begin start thread");
        Thread.sleep(1000);
        stop=true; //store
        //storeload()
        t1.join();
    }
}
