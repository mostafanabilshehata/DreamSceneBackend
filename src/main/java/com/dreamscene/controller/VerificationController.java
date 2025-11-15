package com.dreamscene.controller;

import com.dreamscene.dto.request.SendCodeRequest;
import com.dreamscene.dto.request.VerifyCodeRequest;
import com.dreamscene.dto.response.ApiResponse;
import com.dreamscene.service.EmailVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verification")
@RequiredArgsConstructor
public class VerificationController {
    
    private final EmailVerificationService emailVerificationService;
    
    @PostMapping("/send-code")
    public ResponseEntity<ApiResponse<String>> sendVerificationCode(@Valid @RequestBody SendCodeRequest request) {
        String message = emailVerificationService.sendVerificationCode(request.getEmail());
        return ResponseEntity.ok(ApiResponse.success(message, null));
    }
    
    @PostMapping("/verify-code")
    public ResponseEntity<ApiResponse<Boolean>> verifyCode(@Valid @RequestBody VerifyCodeRequest request) {
        boolean verified = emailVerificationService.verifyCode(request.getEmail(), request.getCode());
        if (verified) {
            return ResponseEntity.ok(ApiResponse.success("Code verified successfully", true));
        } else {
            return ResponseEntity.ok(ApiResponse.error("Invalid or expired verification code"));
        }
    }
}
