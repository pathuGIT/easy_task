package com.easytask.backend.services;

import com.easytask.backend.dto.RegisterRequest;
import com.easytask.backend.dto.RegisterResponse;
import com.easytask.backend.models.Users;
import com.easytask.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);


    public RegisterResponse register(RegisterRequest request) {
        System.out.println("lll");
        if(userRepository.existsByUsername(request.getUsername())){
            throw new IllegalArgumentException("This username already exist...");
        }

        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        Users registerResponse = userRepository.save(user);

        RegisterResponse reg = new RegisterResponse(registerResponse.getId(), registerResponse.getUsername());
        System.out.println();
        return reg;
    }
}
