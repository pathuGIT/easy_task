package com.easytask.backend.services;

import com.easytask.backend.dto.ApiResponse;
import com.easytask.backend.enums.ProjectStatus;
import com.easytask.backend.models.Project;
import com.easytask.backend.models.Users;
import com.easytask.backend.repositories.ProjectRepository;
import com.easytask.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;

    public ApiResponse addProject(Project project, Authentication authentication) {
        Users user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(()->new UsernameNotFoundException("User Not Found."));
        project.setUsers(user);
        project.setProStatus(ProjectStatus.PLANNED);
        projectRepository.save(project);
        return new ApiResponse<>("Project Created Successfully.", true);
    }


    public ApiResponse getAll(Authentication authentication) {
        Users user = userRepository.findByUsername(authentication.getName())
                        .orElseThrow(()->new UsernameNotFoundException("User Not Found."));
        List<Project> all = projectRepository.findAllByUsers(user);

        if(all.isEmpty())
            return new ApiResponse<>("Empty project list", true);
        else
            return new ApiResponse<>("Successfully loaded all projects.", all);
    }
}
