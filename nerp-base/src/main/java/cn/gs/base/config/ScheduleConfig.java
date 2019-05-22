package cn.gs.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import cn.gs.base.util.SpringUtils;


/**
 * 新增一个配置类，实现SchedulingConfigurer接口。重写configureTasks方法，通过taskRegistrar设置自定义线程池
 * @author wangshaodong
 * @date 2018年8月29日
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
	
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    	ThreadPoolTaskScheduler taskExecutor = SpringUtils.getBean("taskScheduler", ThreadPoolTaskScheduler.class);
        taskRegistrar.setScheduler(taskExecutor);
    }
    
    @Bean(destroyMethod="shutdown", name="taskScheduler")
    public ThreadPoolTaskScheduler  taskExecutor() {
    	ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
    	scheduler.setPoolSize(20);
    	scheduler.setThreadNamePrefix("task-");
        return scheduler;
    }
}
