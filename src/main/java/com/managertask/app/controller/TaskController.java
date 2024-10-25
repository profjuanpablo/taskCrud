package com.managertask.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.managertask.app.model.Status;
import com.managertask.app.model.Task;
import com.managertask.app.repository.StatusRepository;
import com.managertask.app.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StatusRepository statusRepository;

    // Listar todas as tarefas
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // Criar uma nova tarefa
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        // Verificar se o Status existe antes de salvar a tarefa
        if (task.getStatus() != null && task.getStatus().getId() != null) {
            Optional<Status> statusOptional = statusRepository.findById(task.getStatus().getId());
            statusOptional.ifPresent(task::setStatus);
        }
        return taskRepository.save(task);
    }

    // Atualizar uma tarefa existente
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

        task.setName(taskDetails.getName());
        task.setDescription(taskDetails.getDescription());

        if (taskDetails.getStatus() != null && taskDetails.getStatus().getId() != null) {
            Status status = statusRepository.findById(taskDetails.getStatus().getId())
                    .orElseThrow(() -> new RuntimeException("Status não encontrado"));
            task.setStatus(status);
        }

        return taskRepository.save(task);
    }
}
