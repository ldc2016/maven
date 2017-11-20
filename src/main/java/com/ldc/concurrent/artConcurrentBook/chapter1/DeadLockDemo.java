package com.ldc.concurrent.artConcurrentBook.chapter1;

/**
 * Created by dacheng.liu on 2017/8/23.
 */
public class DeadLockDemo {
    private static Object monitorA = new Object();
    private static Object monitorB = new Object();


    public static void main(String[] args) {
        Thread t1 = new Thread(new DeadLockThreaderA());
        Thread t2 = new Thread(new DeadLockThreaderB());

        t1.start();
        t2.start();
    }

    private static final class DeadLockThreaderA implements Runnable{

        @Override
        public void run() {
            synchronized (monitorA){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (monitorB){
                    System.out.println("current thread is 1 !");
                }
            }
        }
    }

    private static final class DeadLockThreaderB implements Runnable{

        @Override
        public void run() {
            synchronized (monitorB){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (monitorA){
                    System.out.println("current thread is 2 !");
                }
            }
        }
    }
}
