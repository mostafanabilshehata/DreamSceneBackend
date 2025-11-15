package com.dreamscene.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class PasswordTestController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/generate-hash/{password}")
    public String generateHash(@PathVariable String password) {
        String hash = passwordEncoder.encode(password);
        return "Password: " + password + "<br>Hash: " + hash + "<br><br>SQL to update:<br>UPDATE users SET password = '" + hash + "' WHERE username = 'admin';";
    }

    @GetMapping("/verify/{password}/{hash}")
    public String verifyPassword(@PathVariable String password, @PathVariable String hash) {
        boolean matches = passwordEncoder.matches(password, hash);
        return "Password: " + password + "<br>Hash: " + hash + "<br>Matches: " + matches;
    }
}
