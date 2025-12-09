package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.SupportStaff;
import com.example.proiectul.Cinema.service.SupportStaffService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String create(@Valid @ModelAttribute("supportstaff") SupportStaff s,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "supportstaff/form";
        }

        service.save(s);
        return "redirect:/supportstaff";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id,
                          Model model,
                          RedirectAttributes ra) {

        return service.findById(id)
                .map(staff -> {
                    model.addAttribute("supportstaff", staff);
                    return "supportstaff/details";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Support staff not found.");
                    return "redirect:/supportstaff";
                });
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id,
                           Model model,
                           RedirectAttributes ra) {

        return service.findById(id)
                .map(staff -> {
                    model.addAttribute("supportstaff", staff);
                    return "supportstaff/edit";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Support staff not found.");
                    return "redirect:/supportstaff";
                });
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("supportstaff") SupportStaff s,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "supportstaff/edit";
        }

        s.setId(id);
        service.save(s);
        return "redirect:/supportstaff";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            service.deleteById(id);
            ra.addFlashAttribute("successMessage", "Support staff deleted successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("globalError", "Could not delete support staff.");
        }
        return "redirect:/supportstaff";
    }
}
