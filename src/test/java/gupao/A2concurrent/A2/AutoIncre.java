package gupao.A2concurrent.A2;

import java.math.BigDecimal;

public class AutoIncre {
    /**
     * 经过测试，不作处理执行完i的值是16000
     * 加上volatile是19979
     * 加上syncronized是200000
     */
    private int i=0;
    private BigDecimal b = new BigDecimal(0);
    public void incre(){
        synchronized (b){
            i++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AutoIncre ai = new AutoIncre();
        Thread[] threads = new Thread[2];
        threads[0] = new Thread(()->{
            for(int i =0;i< 10000;i++){
                ai.b = new BigDecimal(-i);
                ai.incre();
            }
        });
        threads[1] = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i =0;i< 10000;i++){
                    ai.b = new BigDecimal(i*1000);
                    ai.incre();
                }
            }
        });
        for(Thread t : threads){
            t.start();
        }
        threads[0].join();
        threads[1].join();
        System.out.println(ai.i);
    }
}
