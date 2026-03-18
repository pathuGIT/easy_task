package com.easytask.backend.services;

import com.easytask.backend.dto.LoadUser;
import com.easytask.backend.models.Users;
import com.easytask.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    // Get the user from the db
    @Override
    public UserDetails loadUserByUsername(String username){

        LoadUser loadUser = userRepository.findByUsername(username);

        if(loadUser == null){
            throw new UsernameNotFoundException("User not found with : " + username);
        }

        return User.builder()
                .username(loadUser.getUsername())
                .password(loadUser.getPassword())
                .build();
    }
}
