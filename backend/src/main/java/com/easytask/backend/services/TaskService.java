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
            System.out.println("mn: "+SecurityContextHolder.getContext().getAuthentication());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                throw new IllegalArgumentException("User not authenticated");
            }

            Users user = userRepository.findByUsername(authentication.getName());
            if (user == null){
                throw new UsernameNotFoundException("This user not found: ");
            }
            List<Task> all = taskRepository.findAllByUsers(user);
            if (all == null){
                throw new UsernameNotFoundException("Empty list for this user: "+ user.getUsername());
            }
            return all;
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
