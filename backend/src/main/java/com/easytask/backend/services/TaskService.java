package com.easytask.backend.services;

import com.easytask.backend.models.Task;
import com.easytask.backend.models.Users;
import com.easytask.backend.repositories.TaskRepository;
import com.easytask.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getAll() {
        try {
            System.out.println("mn: " + SecurityContextHolder.getContext().getAuthentication());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new IllegalArgumentException("User not authenticated");
            }

            Users user = userRepository.findByUsername(authentication.getName());
            if (user == null) {
                throw new UsernameNotFoundException("This user not found: ");
            }
            List<Task> all = taskRepository.findAllByUsers(user);
            if (all == null) {
                throw new UsernameNotFoundException("Empty list for this user: " + user.getUsername());
            }
            return all;
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Task createTask(Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User not authenticated");
        }

        Users user = userRepository.findByUsername(authentication.getName());
        if (user == null) {
            throw new UsernameNotFoundException("This user not found: " + authentication.getName());
        }

        task.setUsers(user);
        task.setCreatedAt(java.time.LocalDateTime.now());
        if (task.getStatus() == null || task.getStatus().isEmpty()) {
            task.setStatus("TO_DO");
        }
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User not authenticated");
        }

        Users user = userRepository.findByUsername(authentication.getName());
        if (user == null) {
            throw new UsernameNotFoundException("This user not found: " + authentication.getName());
        }

        Task task = taskRepository.findByIdAndUsers(id, user);
        if (task == null) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        return task;
    }

    public Task updateTask(Long id, Task reqTask) {
        Task existingTask = getTaskById(id);
        if (reqTask.getTitle() != null) {
            existingTask.setTitle(reqTask.getTitle());
        }
        if (reqTask.getDescription() != null) {
            existingTask.setDescription(reqTask.getDescription());
        }
        if (reqTask.getStatus() != null) {
            existingTask.setStatus(reqTask.getStatus());
        }
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        Task existingTask = getTaskById(id);
        taskRepository.delete(existingTask);
    }
}
