package com.managertask.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.managertask.app.model.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}