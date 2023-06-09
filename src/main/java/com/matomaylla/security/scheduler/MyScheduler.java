package com.matomaylla.security.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.matomaylla.security.client.ExternalApiCaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
@ConditionalOnProperty(name = "scheduling.enabled", havingValue = "true", matchIfMissing = true)
public class MyScheduler {
    private TaskScheduler taskScheduler;

    @Autowired
    private ExternalApiCaller apiCaller;

    public MyScheduler() {
        taskScheduler = new ThreadPoolTaskScheduler(); // or any other implementation of TaskScheduler
    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void scheduleTask() throws JsonProcessingException {
        apiCaller.callExternalApi();
    }
}

