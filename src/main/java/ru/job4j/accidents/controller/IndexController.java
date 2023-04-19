package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
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
        return "index";
    }
}