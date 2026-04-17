package com.easytask.backend.services;

import com.easytask.backend.models.Users;
import com.easytask.backend.repositories.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.InvalidKeyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey;
    @Autowired
    private UserRepository userRepository;

    private Date extractExpiration(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public JwtService( @Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); // Convert secret string to SecretKey
    }

    public String generateActiveToken(String username){ // Generate Active Token for 2 minutes
        try {
            Users users = userRepository.findByUsername(username)
                    .orElseThrow(()->new UsernameNotFoundException("User Not Found."));
            return Jwts.builder()
                    .subject(username)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 2))
                    .signWith(secretKey, SignatureAlgorithm.HS256)
                    .claim("userid", users.getId())
                    .compact();
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateRefreshToken(String username){ // Generate Refresh token for 2 hours
        try {
            Users users = userRepository.findByUsername(username)
                    .orElseThrow(()->new UsernameNotFoundException("User Not Found."));
            return Jwts.builder()
                    .subject(username)
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 2 ))
                    .signWith(secretKey, SignatureAlgorithm.HS256)
                    .claim("userid", users.getId())
                    .compact();
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public String extractUserName(String token) { // Extract Username from Token
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            String username = extractUserName(token);
            Date expiration = extractExpiration(token);
            return username.equals(userDetails.getUsername()) && !expiration.before(new Date());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
