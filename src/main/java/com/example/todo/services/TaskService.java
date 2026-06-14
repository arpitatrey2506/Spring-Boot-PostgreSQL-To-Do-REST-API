package com.example.todo.services;

import com.example.todo.model.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getTasksByUserId(Integer userId) {
        return taskRepository.findByUserId(userId);
    }

    public List<Task> getAllTasks(Integer userId) {
        return taskRepository.findByUserId(userId);
    }

    public Task addTask(Integer userId, Task task) {
        // 1. Check if user already exists 
        boolean userExists = userRepository.existsById(userId);

        if (!userExists) {
            // 2. Retrieve provided user details for first-time registration
            com.example.todo.model.User newUser = task.getUserDetails();
            if (newUser == null) {
                throw new IllegalArgumentException("User with ID " + userId + " does not exist. Please provide 'userDetails' in the body to register the user.");
            }
            // 3. Save the new user record to the database
            userRepository.insertUser(userId, newUser.getName(), newUser.getAddress(), newUser.getEmail());
        }

        // 4. Save the task normally
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
