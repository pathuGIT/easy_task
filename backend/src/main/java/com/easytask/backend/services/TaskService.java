package com.easytask.backend.services;

import com.easytask.backend.enums.TaskStatus;
import com.easytask.backend.models.Project;
import com.easytask.backend.models.Task;
import com.easytask.backend.models.Users;
import com.easytask.backend.repositories.ProjectRepository;
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
    private ProjectRepository projectRepository;

    public List<Task> getAll(long projectId) {
        try {
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(()->new UsernameNotFoundException("Project Not Found."));

            List<Task> all = taskRepository.findAllByProject(project);
            if (all == null) {
                throw new UsernameNotFoundException("Empty list for this Project title: " + project.getTitle() +" & id: " + projectId);
            }
            return all;
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Task createTask(Long projectId, Task task, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User not authenticated");
        }

        Project project = projectRepository.findById(projectId)
                .orElseThrow(()-> new UsernameNotFoundException("Project Not Found."));

        task.setProject(project);
        task.setCreatedAt(java.time.LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task getTaskById(Long taskId, Authentication authentication) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(()-> new UsernameNotFoundException("This Task Not Found."));

        Project project = projectRepository.findById(task.getProject().getpId())
                .orElseThrow(()->new UsernameNotFoundException("This Project Not Found."));
        if(authentication.getName().equals(project.getUsers().getUsername())){
            return task;
        } else {
            throw new RuntimeException("User:"+ authentication.getName() +" not allow to do this operation!");
        }
    }

    public Task updateTask(Long id, Task reqTask, Authentication authentication) {
        Task existingTask = getTaskById(id, authentication);
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

    public void deleteTask(Long id, Authentication authentication) {
        Task existingTask = getTaskById(id, authentication);
        taskRepository.delete(existingTask);
    }
}
