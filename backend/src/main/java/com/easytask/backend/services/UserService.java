package com.easytask.backend.services;

import com.easytask.backend.dto.LoadUser;
import com.easytask.backend.dto.LoginRequest;
import com.easytask.backend.dto.LoginResponse;
import com.easytask.backend.dto.RegisterResponse;
import com.easytask.backend.models.Users;
import com.easytask.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Encrypt the password
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public RegisterResponse register(LoginRequest request) {
        System.out.println("lll");
        if (userRepository.existsByUsername(request.getUsername())) { // validate user is already exist?
            throw new IllegalArgumentException("This username already exist...");
        }

        request.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());

        Users registerResponse = userRepository.save(user); // save new user

        return new RegisterResponse(registerResponse.getId(), registerResponse.getUsername());
    }

    public LoginResponse login(LoginRequest loginRequest) {
        System.out.println("T2");
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        System.out.println("T...");
        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Invalid credentials");
        }

        System.out.println("T3");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String activeToken = jwtService.generateActiveToken(userDetails.getUsername());
        String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());

        System.out.println("T4");
        if (refreshToken != null) {
            Users users = userRepository.findByUsername(userDetails.getUsername());

            if (users == null) {
                throw new UsernameNotFoundException("User not found with : " + userDetails.getUsername());
            }

            // save refresh token
            Users saveUser = userRepository.findById(users.getId())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + users.getId()));

            saveUser.setRefresh_token(refreshToken);
            userRepository.save(saveUser);

        }

        return new LoginResponse(activeToken, refreshToken);
    }
}
