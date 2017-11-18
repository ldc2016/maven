package com.vip.common.service.iml;

import com.vip.common.service.ThreadPoolParameterService;
import com.vip.threadpool.ComplexPausedAbleThreadPoolExecutor;
import com.vip.threadpool.ComplexPausedableThreadPoolTaskExecutor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;


/**
 * Created by dacheng.liu on 2017/11/18.
 */
@Service("threadPoolParameterService")
public class ThreadPoolParameterServiceImpl implements ThreadPoolParameterService,ApplicationContextAware {
    ApplicationContext applicationContext;

    @Override
    public boolean isOpenBySwitchName(String executableSwitch, String start) {
        return false;
    }

    @Override
    public long getDelayTimeValueByName(String delayTimeName, long l) {
        return 0;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 通过该方法获取spring Ioc容器中的线程池，然后可以根据实际需求控制该线程池的状态
     * @param beanName
     * @return
     * @throws Exception
     */
    private ComplexPausedableThreadPoolTaskExecutor getThreadPoolTaskExecutorByBeanName(String beanName)
            throws Exception {
        Object obj = applicationContext.getBean(beanName, ComplexPausedAbleThreadPoolExecutor.class);
        if (obj == null) {
            throw new RuntimeException("threadPoolParameterService.getThreadPoolTaskExecutorByBeanName spring应用上下文创建线程池异常!");
        }
        ComplexPausedableThreadPoolTaskExecutor exeutor = (ComplexPausedableThreadPoolTaskExecutor) obj;
        return exeutor;
    }


    /** 以下方法是操作线程池的方法 */
    private boolean pauseThreadPool(ComplexPausedableThreadPoolTaskExecutor threadPoolExecutor) {
        threadPoolExecutor.pause();
        return true;
    }

    public boolean pauseThreadPool(String threadPoolExecutorName) throws Exception {
        if (threadPoolExecutorName == null) {
            return false;
        }

        ComplexPausedableThreadPoolTaskExecutor threadPoolExecutor = getThreadPoolTaskExecutorByBeanName(threadPoolExecutorName);
        return pauseThreadPool(threadPoolExecutor);
    }

    private boolean resumeThreadPool(ComplexPausedableThreadPoolTaskExecutor threadPoolExecutor) {
        threadPoolExecutor.resume();
        return true;
    }

    public boolean resumeThreadPool(String threadPoolExecutorName) throws Exception {
        if (threadPoolExecutorName == null) {
            return false;
        }
        ComplexPausedableThreadPoolTaskExecutor threadPoolExecutor = getThreadPoolTaskExecutorByBeanName(threadPoolExecutorName);
        return resumeThreadPool(threadPoolExecutor);
    }


    public long setWorkerThreadStopAmount(ComplexPausedableThreadPoolTaskExecutor threadPoolExecutor,int count){
        return threadPoolExecutor.setWorkerThreadStopAmount(count);
    }

    public long setWorkerThreadStopAmount(String threadPoolExecutorName,int maxCount) throws Exception {
        if (threadPoolExecutorName == null) {
            return 0;
        }

        ComplexPausedableThreadPoolTaskExecutor threadPoolExecutor = getThreadPoolTaskExecutorByBeanName(threadPoolExecutorName);
        return setWorkerThreadStopAmount(threadPoolExecutor,maxCount);
    }

    public boolean removeAllTaskUnitListFromQueue(String threadPoolExecutorName) throws Exception {
        if (threadPoolExecutorName == null) {
            return false;
        }

        ComplexPausedableThreadPoolTaskExecutor threadPoolExecutor = getThreadPoolTaskExecutorByBeanName(threadPoolExecutorName);
        threadPoolExecutor.pause();
        BlockingQueue<Runnable> taskUnitList = threadPoolExecutor.getThreadPoolExecutor().getQueue();
        if(CollectionUtils.isNotEmpty(taskUnitList)){
            threadPoolExecutor.getThreadPoolExecutor().getQueue().removeAll(taskUnitList);
        }

        threadPoolExecutor.resume();
        return true;
    }


    public String getPausedWorkerThreadStatus(String threadPoolExecutorName) throws Exception {
        if (threadPoolExecutorName == null) {
            return null;
        }
        ComplexPausedableThreadPoolTaskExecutor threadPoolExecutor = getThreadPoolTaskExecutorByBeanName(threadPoolExecutorName);
        return threadPoolExecutor.getPausedWorkerThreadStatus();
    }

    public long setDelayTime(String threadPoolExecutorName, long delayTime) throws Exception {
        ComplexPausedableThreadPoolTaskExecutor threadPoolExecutor = getThreadPoolTaskExecutorByBeanName(threadPoolExecutorName);
        threadPoolExecutor.setDelayTime(delayTime);
        return delayTime;
    }

    public int getThreadPoolCurrentQueueSize(String threadPoolExecutorName) throws Exception {
        ComplexPausedableThreadPoolTaskExecutor threadPoolExecutor = getThreadPoolTaskExecutorByBeanName(threadPoolExecutorName);
        return threadPoolExecutor.getThreadPoolExecutor().getQueue().size();

    }

}
