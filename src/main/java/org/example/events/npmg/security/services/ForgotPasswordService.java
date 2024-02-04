package org.example.events.npmg.security.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.example.events.npmg.models.PasswordResetToken;
import org.example.events.npmg.models.User;
import org.example.events.npmg.payload.response.MessageResponse;
import org.example.events.npmg.repository.PasswordResetTokenRepository;
import org.example.events.npmg.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.example.events.npmg.util.RepositoryUtil.findByEmail;


@Service
@Transactional
@RequiredArgsConstructor
public class ForgotPasswordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ForgotPasswordService.class);
    private static final String RESET_TOKEN_URL = "http://localhost:8080/resetPassword/";
    private static final Integer MULTIPART_MODE = MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED;
    private static final String CHARSET = StandardCharsets.UTF_8.name();
    private static final String FROM_ADDRESS = "admin@bonda.tech";
    private static final String EMAIL_SUBJECT = "Reset Password Link";
    private static final String TEMPLATE_NAME = "forgotPasswordEmailTemplate";
    private static final String CONTEXT_KEY = "forgotPasswordLink";
    private static final String SUCCESS_MESSAGE = "Reset link sent to your email";
    private static final String ERROR_MESSAGE = "Error occurred while sending reset link";

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final TemplateEngine templateEngine;

    public ResponseEntity<MessageResponse> sendEmailWithResetLink(String email) {
        User user = findByEmail(userRepository, email);
        try {
            sendResetLink(user);
            return ResponseEntity.ok(new MessageResponse(SUCCESS_MESSAGE));
        } catch (MessagingException e) {
            LOGGER.error(ERROR_MESSAGE, e);
            return ResponseEntity.internalServerError().body(new MessageResponse(ERROR_MESSAGE));
        }
    }

    private void sendResetLink(User user) throws MessagingException {
        String resetLink = generateResetLink(user);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE, CHARSET);

        helper.setFrom(FROM_ADDRESS);
        helper.setTo(user.getEmail());
        helper.setSubject(EMAIL_SUBJECT);

        Context context = new Context();
        context.setVariable(CONTEXT_KEY, resetLink);
        String html = templateEngine.process(TEMPLATE_NAME, context);

        helper.setText(html, true);

        javaMailSender.send(mimeMessage);
    }

    public String generateResetLink(User user) {
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDateTime(LocalDateTime.now().plusMinutes(30))
                .build();

        PasswordResetToken savedToken = passwordResetTokenRepository.save(resetToken);

        return Optional.of(savedToken)
                .map(token -> RESET_TOKEN_URL + savedToken.getToken())
                .orElseThrow(() -> new RuntimeException("Error occurred while generating reset token"));
    }


    public boolean hasExipred(LocalDateTime expiryDateTime) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        return expiryDateTime.isAfter(currentDateTime);
    }
}
