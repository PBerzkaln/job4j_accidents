package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.SimpleAuthorityService;
import ru.job4j.accidents.service.SimpleUserService;

@Controller
@AllArgsConstructor
@ThreadSafe
public class RegController {
    private final PasswordEncoder encoder;
    private final SimpleUserService simpleUserService;
    private final SimpleAuthorityService simpleAuthorityService;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(simpleAuthorityService.findByAuthority("ROLE_USER"));
        var savedUser = simpleUserService.create(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "errors/404";
        }
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "users/register";
    }
}