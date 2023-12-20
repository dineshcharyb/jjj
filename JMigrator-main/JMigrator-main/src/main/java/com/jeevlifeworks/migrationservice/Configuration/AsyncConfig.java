package com.jeevlifeworks.migrationservice.Configuration;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.jeevlifeworks.migrationservice.service.MigrationService;

@Configuration
@EnableAsync
public class AsyncConfig {
	
	private static final Logger log = LoggerFactory.getLogger(AsyncConfig.class);


	@Bean(name="taskExecutor")
	public Executor taskExecutor() {
		log.info("creating async task executor");
		ThreadPoolTaskExecutor executor=new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(20);
		executor.setQueueCapacity(2000);
		executor.setThreadNamePrefix("userthread -");
		executor.initialize();
		return executor;
		
	}
}
