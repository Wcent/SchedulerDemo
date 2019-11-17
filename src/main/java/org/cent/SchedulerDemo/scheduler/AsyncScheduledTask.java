package org.cent.SchedulerDemo.scheduler;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务类（自定义线程池异步调用处理）
 * @author Vincent
 * @version 1.0 2019/11/17
 */
@Component
public class AsyncScheduledTask {

    @Async("scheduledPoolTaskExecutor")
    @Scheduled(fixedRate = 10000)
    public void run() {
        System.out.println(Thread.currentThread()+"===>SpringTask AsyncFixedRate定时任务"+new Date());
    }
}
