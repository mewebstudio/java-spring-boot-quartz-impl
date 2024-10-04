package com.mewebstudio.springbootquartzimpl.service;

import com.mewebstudio.springbootquartzimpl.entity.Task;
import com.mewebstudio.springbootquartzimpl.job.TaskJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobService {
    private final Scheduler scheduler;

    /**
     * Schedule a job
     *
     * @param task Task
     */
    public void scheduleTaskJob(Task task) throws SchedulerException {
        JobBuilder jobBuilder = JobBuilder.newJob(TaskJob.class);
        jobBuilder.withIdentity(task.getName(), task.getGroup());
        JobDetail jobDetail = jobBuilder
            .withIdentity(task.getId().toString(), task.getGroup())
            .usingJobData("id", task.getId().toString())
            .build();

        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(task.getName() + "Trigger", task.getGroup())
            .withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExpression()))
            .build();

        scheduler.scheduleJob(jobDetail, trigger);
        log.info("Task {} - {} scheduled successfully", task.getId(), task.getName());
    }

    /**
     * UnSchedule a job
     *
     * @param task Task
     * @return boolean
     */
    public boolean unScheduleTaskJob(Task task) {
        try {
            boolean isDeleted = scheduler.deleteJob(getJobKey(task));
            log.info("Task {} - {} unscheduled successfully", task.getId(), task.getName());
            return isDeleted;
        } catch (SchedulerException e) {
            log.error("Error un-scheduling task: {} - {}", task.getId(), task.getName(), e);
            return false;
        }
    }

    /**
     * Get JobKey
     *
     * @param task Task
     * @return JobKey
     */
    private JobKey getJobKey(Task task) {
        return new JobKey(task.getId().toString(), task.getGroup());
    }
}
