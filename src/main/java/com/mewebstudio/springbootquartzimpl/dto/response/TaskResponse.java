package com.mewebstudio.springbootquartzimpl.dto.response;

import com.mewebstudio.springbootquartzimpl.entity.Task;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskResponse {
    private String id;

    private String name;

    private String group;

    private String cronExpression;

    /**
     * Convert to TaskResponse
     *
     * @param task Task
     * @return TaskResponse
     */
    public static TaskResponse convert(Task task) {
        return TaskResponse.builder()
            .id(task.getId().toString())
            .name(task.getName())
            .group(task.getGroup())
            .cronExpression(task.getCronExpression())
            .build();
    }
}
