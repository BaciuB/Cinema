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
    public String index(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) com.example.proiectul.Cinema.model.Role role,
            @RequestParam(required = false) Integer minSalary,
            @RequestParam(required = false) Integer maxSalary,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        var result = service.search(name, role, minSalary, maxSalary, sortField, sortDir, page, size);

        model.addAttribute("page", result);
        model.addAttribute("supportstaff", result.getContent());

        model.addAttribute("name", name);
        model.addAttribute("role", role);
        model.addAttribute("minSalary", minSalary);
        model.addAttribute("maxSalary", maxSalary);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("size", size);

        model.addAttribute("roles", com.example.proiectul.Cinema.model.Role.values());

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
