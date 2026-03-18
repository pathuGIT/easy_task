package com.easytask.backend.config;

import com.easytask.backend.services.JwtService;
import com.easytask.backend.services.MyUserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private MyUserService myUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authentication"); // Get header request

        if(authHeader != null && authHeader.startsWith("Bearer ")){ // Ensure request not null & header start with 'Bearer'
            String token = authHeader.substring(7); // Split the token from the request (without 'Bearer')

            if (!token.equals("null") && !token.equals("undefined") && !token.isBlank()) { // Verify is there valid token?
                try {
                    String username = jwtService.extractUserName(token); // Extract the username from token

                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = myUserService.loadUserByUsername(username);
                        if (jwtService.validateToken(token, userDetails)) {  // Validate the token & username

                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null); //
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken); // store the authentication in the SecurityContext
                        }
                    }
                } catch (Exception e) {
                    System.err.println("JWT Token processing error: " + e.getMessage());
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
