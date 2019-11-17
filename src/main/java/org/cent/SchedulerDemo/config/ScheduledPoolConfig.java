package org.cent.SchedulerDemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 定时任务线程池自定义配置类
 * @author Vincent
 * @version 1.0 2019/11/17
 */
@Configuration
public class ScheduledPoolConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setThreadNamePrefix("Scheduled-");
        taskScheduler.setPoolSize(10);
        // 线程池对拒绝任务（无线程可用）的处理策略
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 关闭等待时间
        taskScheduler.setAwaitTerminationSeconds(60);
        taskScheduler.initialize();

        // 设置使用自定义线程池处理
        taskRegistrar.setTaskScheduler(taskScheduler);
    }
}
