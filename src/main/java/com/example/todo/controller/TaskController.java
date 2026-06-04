package com.example.todo.controller;

import com.example.todo.model.Task;
import com.example.todo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> getAllTasks(@PathVariable Integer userId) {
        return taskService.getAllTasks(userId);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Integer userId, @PathVariable int id) {
        return taskService.getTaskById(userId, id);
    }

    @PostMapping
    public Task addTask(@PathVariable Integer userId, @RequestBody Task task) {
        return taskService.addTask(userId, task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Integer userId,
                           @PathVariable int id,
                           @RequestBody Task task) {
        return taskService.updateTask(userId, id, task);
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable Integer userId, @PathVariable int id) {
        return taskService.deleteTask(userId, id);
    }
}
