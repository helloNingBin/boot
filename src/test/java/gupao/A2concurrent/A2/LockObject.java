package gupao.A2concurrent.A2;

/**
 * 关于markWord，在ClassLayoutDemo中已经打印出对象在JVM内存中的分布情况
 * 其中的信息头就有一些字节来标记锁的情况
 * 这就意味着，如果多个方法或代码块都使用该对象来上锁，那就相当于这一把锁可以同时锁着多个房间，
 * 这几个房间在同一时间仅允许有一个线程进去访问
 * 那下面来测试一下
 */
public class LockObject {
    public void lock_a(){
        synchronized (this){
            System.out.println("lock_a");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("lock_a执行完了");
        }
    }
    public void lock_b(){
        synchronized (this){
            System.out.println("lock_b");
        }
    }

    public static void main(String[] args) {
        //使用两个线程，A线程先进入lock_a方法，然后阻塞住不出来。这时看B线程能不能进去lock_b。
        //我的猜想：不能。因为这两个代码块是同一把锁，这时锁还被A线程占有
        LockObject lockObject = new LockObject();
        new Thread(()->{
            lockObject.lock_a();
        }).start();
        new Thread(()->{
            lockObject.lock_b();
        }).start();
    }
}
