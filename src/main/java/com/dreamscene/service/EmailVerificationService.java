package com.dreamscene.service;

import com.dreamscene.entity.VerificationCode;
import com.dreamscene.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailVerificationService {
    
    private final JavaMailSender mailSender;
    private final VerificationCodeRepository verificationCodeRepository;
    
    @Transactional
    public String sendVerificationCode(String email) {
        // Delete any existing codes for this email
        verificationCodeRepository.deleteByEmail(email);
        
        // Generate 6-digit code
        String code = String.format("%06d", new Random().nextInt(999999));
        
        // Create verification code entity
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setEmail(email);
        verificationCode.setCode(code);
        verificationCode.setExpiresAt(LocalDateTime.now().plusMinutes(10)); // Expires in 10 minutes
        verificationCodeRepository.save(verificationCode);
        
        // Send email
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Order Tracking Verification Code");
            message.setText("Your verification code is: " + code + "\n\nThis code will expire in 10 minutes.\n\nIf you didn't request this code, please ignore this email.");
            mailSender.send(message);
            return "Verification code sent successfully";
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
    
    public boolean verifyCode(String email, String code) {
        return verificationCodeRepository.findByEmailAndCodeAndVerifiedFalse(email, code)
                .map(verificationCode -> {
                    if (verificationCode.isExpired()) {
                        return false;
                    }
                    verificationCode.setVerified(true);
                    verificationCodeRepository.save(verificationCode);
                    return true;
                })
                .orElse(false);
    }
}
