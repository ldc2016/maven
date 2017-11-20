package com.ldc.concurrent.artConcurrentBook.chapter2;

/**
 * Created by dacheng.liu on 2017/8/24.
 */
public class MultipleThreadTest {
    private static int i = 0; // 共享变量

    public static void main(String[] args) {
        Thread t1 = new Thread(new Adder());
        Thread t2 = new Thread(new Adder());

        t1.start();
        t2.start();
    }


    public static class Adder implements Runnable{
        @Override
        public void run() {
            for (int j = 0; j < 100; j++) {
                i++;
                System.out.println("current thread num : " + Thread.currentThread().getId() + " ************* " + i);
            }
        }
    }
}
