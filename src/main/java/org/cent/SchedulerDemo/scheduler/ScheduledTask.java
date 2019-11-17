package org.cent.SchedulerDemo.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Vincent
 * @version 1.0 2019/11/17
 */
@Component
public class ScheduledTask {

    @Scheduled(fixedRate = 10000)
    public void fixedRate() {
        System.out.println(Thread.currentThread()+"===>SpringTask FixedRate定时任务"+new Date());
    }

    @Scheduled(cron = "0 15 20 * * *")
    public void cron() {
        System.out.println(Thread.currentThread().toString()+"===>SpringTask Cron定时任务");
    }
}
