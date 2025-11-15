package com.dreamscene.controller;

import com.dreamscene.dto.request.LoginRequest;
import com.dreamscene.dto.response.ApiResponse;
import com.dreamscene.dto.response.LoginResponse;
import com.dreamscene.entity.User;
import com.dreamscene.repository.UserRepository;
import com.dreamscene.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(ApiResponse.success("Login successful", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Invalid username or password"));
        }
    }
    
    @GetMapping("/reset-admin-password")
    public ResponseEntity<String> resetAdminPassword() {
        try {
            User admin = userRepository.findByUsername("admin").orElse(null);
            if (admin == null) {
                admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@dreamscene.com");
                admin.setRole(User.Role.ADMIN);
                admin.setCreatedAt(LocalDateTime.now());
            }
            
            String newPassword = "admin123";
            String encodedPassword = passwordEncoder.encode(newPassword);
            admin.setPassword(encodedPassword);
            
            User savedUser = userRepository.save(admin);
            
            // Verify it was saved
            User verifyUser = userRepository.findByUsername("admin").orElse(null);
            boolean passwordMatches = passwordEncoder.matches(newPassword, verifyUser.getPassword());
            
            return ResponseEntity.ok(
                "Admin password reset successfully!<br>" +
                "Username: admin<br>" +
                "Password: " + newPassword + "<br>" +
                "Encoded: " + encodedPassword + "<br>" +
                "Saved ID: " + savedUser.getId() + "<br>" +
                "Verification matches: " + passwordMatches
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage() + "<br>Stack: " + e.getStackTrace()[0]);
        }
    }
}