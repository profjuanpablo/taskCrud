package com.managertask.app.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.managertask.app.model.Task;
import com.managertask.app.repository.StatusRepository;
import com.managertask.app.repository.TaskRepository;

import com.managertask.app.model.Status;


import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskViewController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private StatusRepository statusRepository;

    // Página para listar as tarefas
    @GetMapping("/view")
    public String listTasks(Model model) {
        List<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "task-list";
    }

    // Página para criar uma nova tarefa
    @GetMapping("/create")
    public String createTaskForm(Model model) {
        List<Status> statuses = statusRepository.findAll();
        model.addAttribute("task", new Task());
        model.addAttribute("statuses", statuses);
        return "task-create";
    }

    // Processa o formulário de criação de nova tarefa
    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task) {
        taskRepository.save(task);
        return "redirect:/tasks/view";
    }

    // Página para editar uma tarefa existente
    @GetMapping("/edit/{id}")
    public String editTaskForm(@PathVariable Long id, Model model) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        List<Status> statuses = statusRepository.findAll();
        model.addAttribute("task", task);
        model.addAttribute("statuses", statuses);
        return "task-edit";
    }

    // Processa o formulário de edição de tarefa
    @PostMapping("/edit/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute Task taskDetails) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        task.setName(taskDetails.getName());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());
        taskRepository.save(task);
        return "redirect:/tasks/view";
    }
}