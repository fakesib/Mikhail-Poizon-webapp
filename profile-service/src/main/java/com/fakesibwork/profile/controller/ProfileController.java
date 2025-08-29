package com.fakesibwork.profile.controller;

import com.fakesibwork.profile.service.UserService;
import dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String profile(Authentication authentication, Model model) {
        UserDto userData = userService.getUser(authentication.getName());
        model.addAttribute("user", userData);
        return "profile";
    }

    @PostMapping("/send-confirm-mail")
    public String sendConfirmMail(@ModelAttribute("user") UserDto userDto,
                                  RedirectAttributes redirectAttributes) {

        userService.sendConfirmationMail(userDto);
        redirectAttributes.addFlashAttribute("message", "Письмо отправлено, проверьте почту");
        return "redirect:/profile";
    }

    @PostMapping("/update")
    public String updateProfile(Authentication authentication,
                                @ModelAttribute("user") UserDto userDto) {
        userService.updateUser(authentication.getName(), userDto);
        return "redirect:/profile";
    }


    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String admin() {
        return "admin";
    }
}
