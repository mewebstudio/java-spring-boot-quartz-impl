package com.mewebstudio.springbootquartzimpl.repository;

import com.mewebstudio.springbootquartzimpl.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
