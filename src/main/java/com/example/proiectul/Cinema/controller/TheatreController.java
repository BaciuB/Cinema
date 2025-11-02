package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Theatre;
import com.example.proiectul.Cinema.service.TheatreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/theatres")
public class TheatreController {

    private final TheatreService service;

    public TheatreController(TheatreService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("theatres", service.findAll());
        return "theatre/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Theatre t = new Theatre();
        t.setHalls(new ArrayList<>());
        model.addAttribute("theatre", t);
        return "theatre/form";
    }

    @PostMapping
    public String create(@ModelAttribute Theatre theatre) {
        if (theatre.getHalls() == null) theatre.setHalls(new ArrayList<>());
        service.save(theatre);
        return "redirect:/theatres";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/theatres";
    }
}
