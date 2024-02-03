//package org.example.events.npmg.service;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.example.events.npmg.repository.CodeSentToMailRepository;
//import org.example.events.npmg.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.TemplateEngine;
//
//import java.util.Optional;
//import java.util.Random;
//
//@Service
//@Transactional
//@RequiredArgsConstructor
//public class ForgotPasswordService {
//
//    private final JavaMailSender javaMailSender;
//    private final CodeSentToMailRepository codeSentToMailRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final TemplateEngine templateEngine;
//    private final UserRepository userRepo;
//
//    @Value("${bonda.app.mail.code.length}")
//    private int CODE_LENGTH;
//
//    public void requestPasswordReset(String email) {
//        sendEmailWithTemplate(new EmailDTO(email, "Password Reset", "Please use the following code to reset your password: "));
//    }
//
//    private String generateResetToken() {
//        return generateCode();
//    }
//
//    private void sendResetLink(String email, String token) {
//        sendEmailWithTemplate(new EmailDTO(email, "Password Reset", "Please use the following code to reset your password: " + token));
//    }
//
//    public boolean validateResetToken(String token, String email) {
//        String passwordFromDB = codeSentToMailRepository.findByEmail(email).get().getCode();
//        return passwordFromDB.equals(token);
//    }
//
//    public void updatePassword(String email, String newPassword) {
//        User user = userRepo.findByEmail(email).get();
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepo.updatePassword(user.getId(), user.getPassword());
//    }
//
//    public void sendConfirmation(String email) {
//        sendEmailWithTemplate(new EmailDTO(email, "Password Reset Confirmation", "Your password has been successfully reset."));
//    }
//
//    private String generateCode() {
//        Random random = new Random();
//        StringBuilder code = new StringBuilder(CODE_LENGTH);
//        for (int i = 0; i < CODE_LENGTH; i++) {
//            int digit = random.nextInt(10);
//            code.append(digit);
//        }
//        return code.toString();
//    }
//
//    public ResponseEntity<String> sendEmailWithTemplate(EmailDTO emailDTO) {
//        // Existing logic to send email with reset code
//    }
//
//    // Existing methods...
//}