package org.example.events.npmg.controller;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.models.PasswordResetToken;
import org.example.events.npmg.payload.request.RegisterPayload;
import org.example.events.npmg.payload.response.MessageResponse;
import org.example.events.npmg.repository.PasswordResetTokenRepository;
import org.example.events.npmg.security.services.AuthService;
import org.example.events.npmg.security.services.ForgotPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
//@RequestMapping("/api/auth")
public class ForgotPasswordController {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final ForgotPasswordService forgotPasswordService;
    private final AuthService authService;

    @PostMapping("/forgotPassword")
    public ResponseEntity<MessageResponse> sendEmailWithResetLink(@RequestParam String email) {
        return forgotPasswordService.sendEmailWithResetLink(email);
    }

    @GetMapping("/resetPassword/{token}")
    public String resetPasswordForm(@PathVariable String token, Model model) {
        PasswordResetToken reset = passwordResetTokenRepository.findByToken(token);
        if (reset != null && forgotPasswordService.hasExipred(reset.getExpiryDateTime())) {
            model.addAttribute("email", reset.getUser().getEmail());
            return "resetPassword";
        }
        return "redirect:/forgotPassword?error";
    }

    @PostMapping("/resetPassword")
    public String passwordResetProcess(@ModelAttribute RegisterPayload data) {
        authService.changePassword(data);

        return "redirect:/login";
    }

}
