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
//        sendEmailWithTemplate(new EmailPayload(email, "Password Reset", "Please use the following code to reset your password: "));
//    }
//
//    private String generateResetToken() {
//        return generateCode();
//    }
//
//    private void sendResetLink(String email, String token) {
//        sendEmailWithTemplate(new EmailPayload(email, "Password Reset", "Please use the following code to reset your password: " + token));
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
//        sendEmailWithTemplate(new EmailPayload(email, "Password Reset Confirmation", "Your password has been successfully reset."));
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
//    public ResponseEntity<String> sendEmailWithTemplate(EmailPayload emailDTO) {
//        Object object = loadObjectByEmail(emailDTO.getTo());
//        if (object == null)
//        {
//            return ResponseEntity.badRequest().body("Email is not present!");
//        }
//
//        CodeSentToMail codeSentToMail = new CodeSentToMail();
//
//        codeSentToMail.setEmail(emailDTO.getTo());
//        String generatedPassword = generateCode();
//        codeSentToMail.setCode(generatedPassword);
//
//
//        if (codeSentToMailRepository.findByEmail(emailDTO.getTo()).isPresent())
//        {
//            codeSentToMailRepository.updateCode(generatedPassword, emailDTO.getTo());
//            System.out.println("Code updated successfully");
//        }
//        else
//        {
//            codeSentToMailRepository.save(codeSentToMail);
//        }
//        try
//        {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setFrom("admin@bonda.tech");
//            helper.setTo(emailDTO.getTo());
//            helper.setSubject(emailDTO.getSubject());
//
//            String emailContent = createEmailContent(emailDTO.getName(), generatedPassword);
//            helper.setText(emailContent, true); // Set the second parameter to true for HTML content
//
//            javaMailSender.send(message);
//            return ResponseEntity.ok("Email sent successfully");
//        } catch (MessagingException e)
//        {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
//        }
//    }
//
//    // Existing methods...
//}