package com.dreamscene.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String password = "admin123";
        String hash = encoder.encode(password);
        
        System.out.println("Password: " + password);
        System.out.println("BCrypt Hash: " + hash);
        System.out.println();
        
        // Test the hash
        boolean matches = encoder.matches(password, hash);
        System.out.println("Password matches hash: " + matches);
        
        // Test with the hash from data.sql
        String existingHash = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";
        boolean matchesExisting = encoder.matches(password, existingHash);
        System.out.println("Password matches existing hash: " + matchesExisting);
    }
}
