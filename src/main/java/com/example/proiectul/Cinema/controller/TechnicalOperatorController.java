package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.TechnicalOperator;
import com.example.proiectul.Cinema.service.TechnicalOperatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/operators")
public class TechnicalOperatorController {

    private final TechnicalOperatorService service;

    public TechnicalOperatorController(TechnicalOperatorService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("operators", service.findAll());
        return "technicaloperator/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("operator", new TechnicalOperator());
        return "technicaloperator/form";
    }

    @PostMapping
    public String create(@ModelAttribute TechnicalOperator operator) {
        service.save(operator);
        return "redirect:/operators";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/operators";
    }
}
