package com.easytask.backend.services;

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
import org.springframework.security.core.userdetails.User;

import java.util.Map;

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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Invalid credentials");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String activeToken = jwtService.generateActiveToken(userDetails.getUsername());
        String refreshToken = jwtService.generateRefreshToken(userDetails.getUsername());

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

    public Map<String, String> refresh(String refreshToken) {
        try {
            String username = jwtService.extractUserName(refreshToken);

            UserDetails userDetails = User.withUsername(username).password("").roles("").build();
            if (jwtService.validateToken(refreshToken, userDetails)) {
                String newActiveToken = jwtService.generateActiveToken(username);
                return Map.of("activeToken", newActiveToken);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("Error generate refresh token.");
    }

    public Map<String, String> logout(String username) {
        Users user = userRepository.findByUsername(username);
        if (user != null) {
            user.setRefresh_token(null);
            userRepository.save(user);
            return Map.of("msg", "User " + username + " successfully logout.");
        }
        throw new RuntimeException("Error Logout.");
    }
}
