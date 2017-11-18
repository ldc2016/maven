package com.vip.threadpool;

/**
 * Created by dacheng.liu on 2017/11/18.
 */

import com.vip.common.service.ThreadPoolParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.scheduling.concurrent.ExecutorConfigurationSupport;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.util.concurrent.*;

/**
 * 可停的线程池执行器
 * see org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
 */
public class ComplexPausedableThreadPoolTaskExecutor extends ExecutorConfigurationSupport implements AsyncListenableTaskExecutor,SchedulingTaskExecutor {

    protected final Logger LOG = LoggerFactory.getLogger(ComplexPausedableThreadPoolTaskExecutor.class);

    /** thread pool original parameters start */
    private final Object poolSizeMonitor = new Object();
    private int corePoolSize = 1;
    private int maxPoolSize = Integer.MAX_VALUE;
    private int keepAliveSeconds = 60;
    private boolean allowCoreThreadTimeOut = false;
    private int queueCapacity = Integer.MAX_VALUE;
    /** thread pool original parameters end */

    /** 自定义线程池参数 start */
    /** 获取线程池相关参数的service类*/
    private ThreadPoolParameterService threadPoolParameterService;
    /** 线程池中的执行器，核心参数*/
    private ComplexPausedAbleThreadPoolExecutor threadPoolExecutor;
    /** 当前线程池的初始化是否需要根据开关状态进行初始化*/
    private boolean needWatchSwitch;
    /** 配置中心 or DB开关，可随时设置该值控制线程池的启动|停止状态*/
    private String executableSwitch;
    /** 当前线程池的工作线程是否延迟执行*/
    private boolean delay;
    /** delayTime对应的key*/
    private String delayTimeName;
    /** 当前线程池的工作线程执行时延迟的时间*/
    private long delayTime;
    private String isNeedWatchSwitch;
    private String delayWork;
    /** 自定义线程池参数 end */


    /**
     * InitializingBean子类ExecutorConfigurationSupport类中的afterPropertiesSet方法主要用来初始化一些核心参数
     * 最终调用的是：initializeExecutor方法来初始化threadPoolExecutor
     * 该方法在springIOC容器启动时，实例化Bean的时候执行，在SpringIoc容器的整个生命周期仅执行一次
     */
    @Override
    public void afterPropertiesSet() {
        // 1.初始化threadPoolExecutor
        super.afterPropertiesSet();
        // 2.根据DBor配置中心的开关状态暂停or启动线程池
        if(needWatchSwitch){
            if(threadPoolParameterService != null && executableSwitch!=null){
                LOG.info("ComplexPausedableThreadPoolTaskExecutor.afterPropertiesSet, 开始获取配置中心or数据库中的开关状态");
                if(threadPoolParameterService.isOpenBySwitchName(executableSwitch, "START")){
                    resume();
                    LOG.info("ComplexPausedableThreadPoolTaskExecutor.afterPropertiesSet, 线程池目前出于开启状态！");
                }else{
                    pause();
                    LOG.info("ComplexPausedableThreadPoolTaskExecutor.afterPropertiesSet, 线程池目前出于暂停状态！");
                }
            }
        }
        // 3.根据配置信息线程池中的工作线程是否延时执行
        if(delay){
            if(threadPoolParameterService != null && delayTimeName!=null){
                delayTime = threadPoolParameterService.getDelayTimeValueByName(delayTimeName, 0L);
                threadPoolExecutor.setDelayed(delay);
                threadPoolExecutor.setDelayTime(delayTime);
            }
        }

    }


    public void setCorePoolSize(int corePoolSize) {
        synchronized (this.poolSizeMonitor) {
            this.corePoolSize = corePoolSize;
            if (this.threadPoolExecutor != null) {
                this.threadPoolExecutor.setCorePoolSize(corePoolSize);
            }
        }
    }

    public int getCorePoolSize() {
        synchronized (this.poolSizeMonitor) {
            return this.corePoolSize;
        }
    }

    public void setMaxPoolSize(int maxPoolSize) {
        synchronized (this.poolSizeMonitor) {
            this.maxPoolSize = maxPoolSize;
            if (this.threadPoolExecutor != null) {
                this.threadPoolExecutor.setMaximumPoolSize(maxPoolSize);
            }
        }
    }

    public int getMaxPoolSize() {
        synchronized (this.poolSizeMonitor) {
            return this.maxPoolSize;
        }
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        synchronized (this.poolSizeMonitor) {
            this.keepAliveSeconds = keepAliveSeconds;
            if (this.threadPoolExecutor != null) {
                this.threadPoolExecutor.setKeepAliveTime(keepAliveSeconds, TimeUnit.SECONDS);
            }
        }
    }

    public int getKeepAliveSeconds() {
        synchronized (this.poolSizeMonitor) {
            return this.keepAliveSeconds;
        }
    }

    public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }


    public ExecutorService initializeExecutor(ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {
        BlockingQueue<Runnable> blockingQueue = createQueue(this.queueCapacity);
        ComplexPausedAbleThreadPoolExecutor executor  = new ComplexPausedAbleThreadPoolExecutor(
                this.corePoolSize, this.maxPoolSize, this.keepAliveSeconds, TimeUnit.SECONDS,
                blockingQueue, threadFactory, rejectedExecutionHandler,delay,delayTime);
        if (this.allowCoreThreadTimeOut) {
            executor.allowCoreThreadTimeOut(true);
        }

        this.threadPoolExecutor = executor;
        return executor;
    }



    /**
     * Create the BlockingQueue to use for the ThreadPoolExecutor.
     * <p>A LinkedBlockingQueue instance will be created for a positive
     * capacity value; a SynchronousQueue else.
     * @param queueCapacity the specified queue capacity
     * @return the BlockingQueue instance
     * @see java.util.concurrent.LinkedBlockingQueue
     * @see java.util.concurrent.SynchronousQueue
     */
    protected BlockingQueue<Runnable> createQueue(int queueCapacity) {
        if (queueCapacity > 0) {
            return new LinkedBlockingQueue<Runnable>(queueCapacity);
        }
        else {
            return new SynchronousQueue<Runnable>();
        }
    }

    /**
     * Return the current pool size.
     * @see java.util.concurrent.ThreadPoolExecutor#getPoolSize()
     */
    public int getPoolSize() {
        if (this.threadPoolExecutor == null) {
            return this.corePoolSize;
        }
        return this.threadPoolExecutor.getPoolSize();
    }

    /**
     * Return the number of currently active threads.
     * @see java.util.concurrent.ThreadPoolExecutor#getActiveCount()
     */
    public int getActiveCount() {
        if (this.threadPoolExecutor == null) {
            return 0;
        }
        return this.threadPoolExecutor.getActiveCount();
    }

    @Override
    public void execute(Runnable task) {
        Executor executor = getThreadPoolExecutor();
        try {
            executor.execute(task);
        }
        catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        execute(task);
    }

    @Override
    public Future<?> submit(Runnable task) {
        ExecutorService executor = getThreadPoolExecutor();
        try {
            return executor.submit(task);
        }
        catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        ExecutorService executor = getThreadPoolExecutor();
        try {
            return executor.submit(task);
        }
        catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        ExecutorService executor = getThreadPoolExecutor();
        try {
            ListenableFutureTask<Object> future = new ListenableFutureTask<Object>(task, null);
            executor.execute(future);
            return future;
        }
        catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        ExecutorService executor = getThreadPoolExecutor();
        try {
            ListenableFutureTask<T> future = new ListenableFutureTask<T>(task);
            executor.execute(future);
            return future;
        }
        catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    /**
     * This task executor prefers short-lived work units.
     */
    @Override
    public boolean prefersShortLivedTasks() {
        return true;
    }


    public long setWorkerThreadStopAmount(int count){
        return threadPoolExecutor.setWorkerThreadStopAmount(count);
    }

    public String getPausedWorkerThreadStatus(){
        return threadPoolExecutor.getPausedWorkerThreadStatus();
    }


    public boolean pause() {
        this.threadPoolExecutor.pauseThreadPool();
        return true;
    }

    public boolean resume() {
        this.threadPoolExecutor.resumeThreadPool();
        return true;
    }


    /** setter and getter */
    public ThreadPoolParameterService getThreadPoolParameterService() {
        return threadPoolParameterService;
    }

    public void setThreadPoolParameterService(ThreadPoolParameterService threadPoolParameterService) {
        this.threadPoolParameterService = threadPoolParameterService;
    }

    public ComplexPausedAbleThreadPoolExecutor getThreadPoolExecutor() {
        if(threadPoolExecutor == null){
            throw new RuntimeException("threadPoolExecutor is null!");
        }
        return threadPoolExecutor;
    }

    public void setThreadPoolExecutor(ComplexPausedAbleThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    public String getExecutableSwitch() {
        return executableSwitch;
    }

    public void setExecutableSwitch(String executableSwitch) {
        this.executableSwitch = executableSwitch;
    }


    public boolean isDelay() {
        return delay;
    }

    public void setDelay(boolean delay) {
        this.delay = delay;
        threadPoolExecutor.setDelayed(delay);
    }

    public String getDelayTimeName() {
        return delayTimeName;
    }

    public void setDelayTimeName(String delayTimeName) {
        this.delayTimeName = delayTimeName;
    }

    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = delayTime;
        threadPoolExecutor.setDelayTime(delayTime);
    }

    public void setNeedWatchSwitch(boolean needWatchSwitch) {
        this.needWatchSwitch = needWatchSwitch;
    }

    public boolean getNeedWatchSwitch() {
        return needWatchSwitch;
    }

    public void setDelayWork(String delayWork) {
        this.delayWork = delayWork;
    }

    public String getDelayWork() {
        return delayWork;
    }
}
