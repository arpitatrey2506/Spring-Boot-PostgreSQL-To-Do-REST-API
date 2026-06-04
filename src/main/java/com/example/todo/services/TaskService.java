package com.example.todo.services;

import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks(Integer userId) {
        return taskRepository.findByUserId(userId);
    }

    public Task addTask(Integer userId, Task task) {
        task.setId(null);
        task.setUserId(userId);
        return taskRepository.save(task);
    }

    public Task getTaskById(Integer userId, int id) {
        return taskRepository.findByIdAndUserId(id, userId).orElse(null);
    }

    public Task updateTask(Integer userId, int id, Task updatedTask) {
        return taskRepository.findByIdAndUserId(id, userId)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setCompleted(updatedTask.isCompleted());
                    return taskRepository.save(task);
                })
                .orElse(null);
    }

    public String deleteTask(Integer userId, int id) {
        return taskRepository.findByIdAndUserId(id, userId)
                .map(task -> {
                    taskRepository.delete(task);
                    return "Task Deleted";
                })
                .orElse("Task not found or not owned by user");
    }
}
