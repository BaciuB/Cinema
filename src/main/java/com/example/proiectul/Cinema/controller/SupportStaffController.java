package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.SupportStaff;
import com.example.proiectul.Cinema.service.SupportStaffService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/supportstaff")
public class SupportStaffController {

    private final SupportStaffService service;

    public SupportStaffController(SupportStaffService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("supportstaff", service.findAll());
        return "supportstaff/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("supportstaff", new SupportStaff());
        return "supportstaff/form";
    }

    @PostMapping
    public String create(@ModelAttribute SupportStaff s) {
        service.save(s);
        return "redirect:/supportstaff";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        model.addAttribute("supportstaff", service.findById(id).orElseThrow());
        return "supportstaff/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("supportstaff", service.findById(id).orElseThrow());
        return "supportstaff/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute SupportStaff s) {
        s.setId(id);
        service.save(s);
        return "redirect:/supportstaff";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/supportstaff";
    }
}
