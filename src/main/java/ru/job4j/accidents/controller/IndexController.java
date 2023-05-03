package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.service.AccidentService;

@Controller
@ThreadSafe
@AllArgsConstructor
public class IndexController {
    private final AccidentService accidentService;

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        model.addAttribute("all_accidents", accidentService.findAll());
        /**
         * После авторизации Spring создает объект SecurityContextHolder
         * в котором держит информацию об авторизованном пользователе.
         * По аналогии HttpSession в Servlet.
         */
        model.addAttribute("user",
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal());
        return "index";
    }
}