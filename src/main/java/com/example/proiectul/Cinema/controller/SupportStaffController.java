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
    public String showCreateForm(Model model) {
        model.addAttribute("staff", new SupportStaff());
        return "supportstaff/form";
    }

    @PostMapping
    public String create(@ModelAttribute SupportStaff staff) {
        service.save(staff);
        return "redirect:/supportstaff";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/supportstaff";
    }
}

