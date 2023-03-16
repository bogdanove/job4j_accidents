package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.service.AccidentService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@AllArgsConstructor
public class AccidentController {

    private final AccidentService accidents;

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", accidents.findAllAccidentTypes());
        model.addAttribute("rules", accidents.findAllRules());
        return "accidents/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, Model model, HttpServletRequest req) {
        if (!accidents.add(accident, req)) {
            model.addAttribute("message", "Произошла ошибка, инцедент не сохранен!");
            return "errors/404";
        }
        return "redirect:/";
    }

    @GetMapping("/formUpdate")
    public String formUpdateAccident(Model model, @RequestParam("id") int id) {
        model.addAttribute("accident", accidents.findById(id));
        return "accidents/editAccident";
    }

    @PostMapping("/update")
    public String doneAccident(@ModelAttribute Accident accident, Model model) {
        if (!accidents.replace(accident)) {
            model.addAttribute("message", "Произошла ошибка, обновление не выполнено!");
            return "errors/404";
        }
        return "redirect:/";
    }
}
