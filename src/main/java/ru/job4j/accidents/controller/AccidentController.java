package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import java.util.Set;

@Controller
@AllArgsConstructor
@ThreadSafe
public class AccidentController {
    private final AccidentService accidents;
    private final AccidentTypeService accidentTypes;
    private final RuleService rules;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidentTypes.findAll());
        model.addAttribute("rules", rules.findAll());
        return "createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, @RequestParam("type.id") int id,
                       @RequestParam(required = false) Set<Integer> rIds) {
        accidents.create(accident, id, rIds);
        return "redirect:/index";
    }

    @GetMapping("/formUpdateAccident")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidents.findById(id).get());
        model.addAttribute("types", accidentTypes.findAll());
        model.addAttribute("rules", rules.findAll());
        return "editAccident";
    }

    @PostMapping("/updateAccident")
    public String edit(@ModelAttribute Accident accident, @RequestParam(required = false) Set<Integer> rIds) {
        accidents.update(accident, rIds);
        return "redirect:/index";
    }
}