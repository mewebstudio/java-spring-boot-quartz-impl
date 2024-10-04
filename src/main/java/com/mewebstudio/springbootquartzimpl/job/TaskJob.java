package com.mewebstudio.springbootquartzimpl.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.net.InetAddress;

@Slf4j
public class TaskJob implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap dataMap = context.getMergedJobDataMap();
        log.info("TaskJob is running with id: {}", dataMap.get("id"));
        ping();
    }

    private void ping() {
        try {
            String host = "www.google.com";
            InetAddress inet = InetAddress.getByName(host);
            log.info("Pinging {}...", host);

            if (inet.isReachable(5000)) {
                log.info("{} is reachable.", host);
            } else {
                log.warn("{} is not reachable.", host);
            }
        } catch (IOException e) {
            log.error("Error: {}", e.getMessage());
        }
    }
}
