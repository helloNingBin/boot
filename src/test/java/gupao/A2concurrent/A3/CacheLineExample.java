package gupao.A2concurrent.A3;

import sun.misc.Contended;

/**
 * 3.伪共享问题
 *      X、Y、Z三个变量都在同一个缓存行里
 *      由于缓存行是CPU与内存交互的最小单位，线程A只需要用到变量A，会去加载缓存行所有的数据X、Y、Z，同理线程B也一样。
 *      这时线程A去修改X变量，线程B去修改Y变量，即使不是修改同一变量，那么也会出现缓存行的竞争问题。
 *      当线程A获取缓存行的修改权限时，会使线程B的缓存行失败，线程B只能再去内存中重新读取缓存行。同理当B获取到缓存行修改权限时，线程A
 *         也只能去重新读取数据，反复如此，会大大影响性能。
 *      解决办法是：用填充方法，当线程A只能到X变量时，那就是只读取A的数据，数据行不满64位，即填充满64位。其它线程也一样。
 */
public class CacheLineExample implements Runnable{
    public final static long ITERATIONS = 500L * 1000L * 100L;
    private int arrayIndex = 0;

    private static ValueNoPadding[] longs;
    public CacheLineExample(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }
    public static void main(final String[] args) throws Exception {
        for(int i = 1; i < 10; i++){
            System.gc();
            final long start = System.currentTimeMillis();
            runTest(i);
            System.out.println(i + " Threads, duration = " + (System.currentTimeMillis() - start));
        }
    }
    private static void runTest(int NUM_THREADS) throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        longs = new ValueNoPadding[NUM_THREADS];
        for (int i = 0; i < longs.length; i++) {
            longs[i] = new ValueNoPadding();
        }
        //线程数量越来越多
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new CacheLineExample(i));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    @Override
    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {//迭代次数：ITERATIONS
            longs[arrayIndex].value = 0L;
        }
    }

    public final static class ValuePadding {
        protected long p1, p2, p3, p4, p5, p6, p7;
        protected volatile long value = 0L;
        protected long p9, p10, p11, p12, p13, p14;
        protected long p15;
    }

    @Contended //实现对齐填充，但在给JDK参数配置
    public final static class ValueNoPadding {
        protected long p1, p2, p3, p4, p5, p6, p7;
        //8字节
        protected volatile long value = 0L;
        protected long p9, p10, p11, p12, p13, p14, p15;
    }

}

