package com.ldc.threadpool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dacheng.liu on 2017/11/18.
 */
public class SimplePausedAbleThreadPoolExecutor extends ThreadPoolExecutor {
    protected final Logger LOG = LoggerFactory.getLogger(SimplePausedAbleThreadPoolExecutor.class);

    private boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unPaused = pauseLock.newCondition();


    /**
     * 默认构造函数
     */
    public SimplePausedAbleThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public SimplePausedAbleThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                              BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, Executors.defaultThreadFactory(), handler);
    }

    public SimplePausedAbleThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                              BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,threadFactory,handler);
    }

    /**
     * 线程t在执行r中的run()里的逻辑时会先检查当前线程池的状态
     * @param t
     * @param r
     */
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused) {
                LOG.info("===>SimplePausedAbleThreadPoolExecutor.beforeExecute, current status of the threadPool is paused, the thread :{}, will not run!",t.getId());
                unPaused.await();
            }

            LOG.info("===>SimplePausedAbleThreadPoolExecutor.beforeExecute, current status of the threadPool is not paused, the thread :{}, will start run!",t.getId());

        } catch (InterruptedException ie) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * 修改线程池的状态为暂停状态
     */
    public void pause() {
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    /**
     * 修改线程池的状态为可执行状态
     */
    public void resume() {
        pauseLock.lock();
        try {
            isPaused = false;
            unPaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

}
