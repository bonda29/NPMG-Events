package org.example.events.npmg.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.events.npmg.models.PasswordResetToken;
import org.example.events.npmg.models.User;
import org.example.events.npmg.repository.PasswordResetTokenRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final JavaMailSender javaMailSender;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final TemplateEngine templateEngine;

    public String sendEmail(User user) {
        try {
            String resetLink = generateResetToken(user);

            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setFrom("admin@bonda.tech");
            helper.setTo(user.getEmail());
            helper.setSubject("Reset Password Link");

            Context context = new Context();
            context.setVariable("forgotPasswordToken", resetLink);
            String html = templateEngine.process("forgotPasswordEmailTemplate", context);

            helper.setText(html, true);

            javaMailSender.send(msg);

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    public String generateResetToken(User user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expiryDateTime = currentDateTime.plusMinutes(30);
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(uuid.toString());
        resetToken.setExpiryDateTime(expiryDateTime);
        resetToken.setUser(user);
        PasswordResetToken token = passwordResetTokenRepository.save(resetToken);
        if (token != null) {
            return resetToken.getToken();
        }
        return "";
    }


    public boolean hasExipred(LocalDateTime expiryDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(currentDateTime);
    }
}
