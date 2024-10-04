package com.mewebstudio.springbootquartzimpl.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateTaskRequest {
    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Group is required")
    private String group;

    @NotEmpty(message = "Cron expression is required")
    private String cronExpression;
}
