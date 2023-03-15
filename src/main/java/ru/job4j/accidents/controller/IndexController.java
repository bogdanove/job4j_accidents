package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.accidents.service.AccidentService;

@Controller
@AllArgsConstructor
@RequestMapping("/")
public class IndexController {

    private AccidentService accidentService;


    @GetMapping
    public String index(Model model) {
        model.addAttribute("user", "User");
        model.addAttribute("accidents", accidentService.findAllAccidents());
        return "index";
    }
}
