package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.TechnicalOperator;
import com.example.proiectul.Cinema.service.TechnicalOperatorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/technicaloperators")
public class TechnicalOperatorController {

    private final TechnicalOperatorService service;

    public TechnicalOperatorController(TechnicalOperatorService s) {
        this.service = s;
    }

    @GetMapping
    public String index(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) com.example.proiectul.Cinema.model.Specialization specialization,
            @RequestParam(required = false) Integer minSalary,
            @RequestParam(required = false) Integer maxSalary,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model m
    ) {
        var result = service.search(name, specialization, minSalary, maxSalary, sortField, sortDir, page, size);

        m.addAttribute("page", result);
        m.addAttribute("technicaloperators", result.getContent());

        m.addAttribute("name", name);
        m.addAttribute("specialization", specialization);
        m.addAttribute("minSalary", minSalary);
        m.addAttribute("maxSalary", maxSalary);
        m.addAttribute("sortField", sortField);
        m.addAttribute("sortDir", sortDir);
        m.addAttribute("size", size);

        m.addAttribute("specializations", com.example.proiectul.Cinema.model.Specialization.values());

        return "technicaloperator/index";
    }


    @GetMapping("/new")
    public String newForm(Model m) {
        m.addAttribute("technicaloperator", new TechnicalOperator());
        return "technicaloperator/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("technicaloperator") TechnicalOperator o,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "technicaloperator/form";
        }

        service.save(o);
        return "redirect:/technicaloperators";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model m, RedirectAttributes ra) {
        return service.findById(id)
                .map(op -> {
                    m.addAttribute("technicaloperator", op);
                    return "technicaloperator/details";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Technical operator not found.");
                    return "redirect:/technicaloperators";
                });
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model m, RedirectAttributes ra) {
        return service.findById(id)
                .map(op -> {
                    m.addAttribute("technicaloperator", op);
                    return "technicaloperator/edit";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Technical operator not found.");
                    return "redirect:/technicaloperators";
                });
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("technicaloperator") TechnicalOperator o,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "technicaloperator/edit";
        }

        o.setId(id);
        service.save(o);
        return "redirect:/technicaloperators";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            service.deleteById(id);
            ra.addFlashAttribute("successMessage", "Technical operator deleted successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("globalError", "Could not delete technical operator.");
        }
        return "redirect:/technicaloperators";
    }
}
