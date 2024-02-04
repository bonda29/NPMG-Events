package org.example.events.npmg.controller;

import lombok.RequiredArgsConstructor;
import org.example.events.npmg.models.PasswordResetToken;
import org.example.events.npmg.models.User;
import org.example.events.npmg.payload.DTOs.UserDto;
import org.example.events.npmg.repository.PasswordResetTokenRepository;
import org.example.events.npmg.repository.UserRepository;
import org.example.events.npmg.service.ForgotPasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    private final UserRepository userRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/forgotPassword")
    public String showForgotPasswordPage(Model model) {
        // add attributes to the model as necessary
        return "forgotPassword";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassordProcess(@ModelAttribute UserDto userDto) {
        String output = "";
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
            output = forgotPasswordService.sendEmail(user.get());
        }
        if (output.equals("success")) {
            return "redirect:/forgotPassword?success";
        }
        return "redirect:/login?error";
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
    public String passwordResetProcess(@ModelAttribute UserDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        if (user.isPresent()) {
            User user1 = user.get();
            user1.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userRepository.save(user1);
        }
        return "redirect:/login";
    }

}
