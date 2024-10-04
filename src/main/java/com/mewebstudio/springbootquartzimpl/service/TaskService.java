package com.mewebstudio.springbootquartzimpl.service;

import com.mewebstudio.springbootquartzimpl.dto.request.CreateTaskRequest;
import com.mewebstudio.springbootquartzimpl.entity.Task;
import com.mewebstudio.springbootquartzimpl.repository.TaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;

    private final JobService jobService;

    /**
     * Create a task
     *
     * @param request CreateTaskRequest
     * @return Task
     */
    @Transactional
    public Task create(CreateTaskRequest request) {
        Task task = taskRepository.save(Task.builder()
            .name(request.getName())
            .group(request.getGroup())
            .cronExpression(request.getCronExpression())
            .build());

        try {
            jobService.scheduleTaskJob(task);
        } catch (SchedulerException e) {
            log.error("Error scheduling task: {}", task.getName(), e);
        }

        return task;
    }

    @Transactional
    public void delete(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        boolean isUnScheduledJob = jobService.unScheduleTaskJob(task);
        if (isUnScheduledJob) {
            taskRepository.delete(task);
            log.info("Task {} deleted successfully", task.getName());
        } else {
            log.error("Error deleting task: {}", task.getName());
        }
    }

    @Transactional
    public void delete(String id) {
        delete(UUID.fromString(id));
    }
}
