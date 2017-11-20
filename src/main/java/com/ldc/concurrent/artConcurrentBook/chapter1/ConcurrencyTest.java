package com.ldc.concurrent.artConcurrentBook.chapter1;

/**
 * Created by dacheng.liu on 2017/8/23.
 */
public class ConcurrencyTest {

    private static long count = 10000000L;


    public static void main(String[] args) throws InterruptedException {
        concurrency();
        serial();
    }

    /**
     * @warn: 当前子线程中又创建了一个子线程对a做计算，当前子线对b做计算，2个子线程同时运行，有线程上下文切换开销
     * @throws InterruptedException
     */
    public static void concurrency() throws InterruptedException{
        long start = System.currentTimeMillis();
        Thread concurrencyTestThread = new Thread(new ConcurrencyExecutor());
        concurrencyTestThread.start();
        int b = 0;
        for (long i=0;i<count;i++){
            b--;
        }

        long time = System.currentTimeMillis() - start;
        concurrencyTestThread.join();

        System.out.println("concurrency : " + time + " ms, b= " + b );
    }

    /**
     * @warn: 当前子线程对a和b同时做计算,无线程上下文切换
     */
    public static void serial(){
        long start = System.currentTimeMillis();
        long a = 0;
        for(long i = 0;i< count;i++){
            a += 5;
        }

        int b = 0;
        for (long i=0;i<count;i++){
            b--;
        }
        long time = System.currentTimeMillis() - start;
        System.out.println("serial : " + time + " ms, b= " + b );
    }

    private static class ConcurrencyExecutor implements Runnable{
        @Override
        public void run() {
            int a = 0;
            for(long i = 0;i< count;i++){
                a += 5;
            }
        }
    }


}
