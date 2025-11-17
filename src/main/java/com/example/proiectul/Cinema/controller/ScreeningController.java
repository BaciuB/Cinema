package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.service.ScreeningService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/screenings")
public class ScreeningController {

    private final ScreeningService service;

    public ScreeningController(ScreeningService service) { this.service = service; }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("screenings", service.findAll());
        return "screening/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Screening s = new Screening();
        if (s.getTickets() == null) s.setTickets(new ArrayList<>());
        if (s.getAssignments() == null) s.setAssignments(new ArrayList<>());
        model.addAttribute("screening", s);
        return "screening/form";
    }

    @PostMapping
    public String create(@ModelAttribute Screening screening) {
        if (screening.getTickets() == null) screening.setTickets(new ArrayList<>());
        if (screening.getAssignments() == null) screening.setAssignments(new ArrayList<>());
        service.save(screening);
        return "redirect:/screenings";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        Screening s = service.findById(id).orElseThrow();
        model.addAttribute("screening", s);
        return "screening/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Screening s = service.findById(id).orElseThrow();
        model.addAttribute("screening", s);
        return "screening/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Screening screening) {
        screening.setId(id);
        service.save(screening);
        return "redirect:/screenings";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/screenings";
    }
}
