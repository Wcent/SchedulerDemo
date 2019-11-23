package org.cent.SchedulerDemo.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;

/**
 * 定时任务类（自定义线程池异步调用处理）
 * @author Vincent
 * @version 1.0 2019/11/17
 */
@Component
public class AsyncScheduledTask {

    @Autowired
    private RestTemplate restTemplate;

    @Async("scheduledPoolTaskExecutor")
    @Scheduled(fixedRate = 10000)
    public void run() {
        System.out.println(Thread.currentThread()+"===>SpringTask AsyncFixedRate定时任务"+new Date());
    }

    @Async("scheduledPoolTaskExecutor")
    @Scheduled(fixedRate = 30000)
    public void getTime() {
        String url = "http://localhost:8080/get/{1}";
        String time = restTemplate.getForObject(url, String.class, new Date().toString());
        System.out.println(Thread.currentThread()+"===>SpringTask AsyncFixedRate定时任务http get time: "+time);
    }
}
