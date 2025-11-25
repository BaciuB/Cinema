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

    public TheatreController(TheatreService service) { this.service = service; }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("theatres", service.findAll());
        return "theatre/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Theatre t = new Theatre();
        if (t.getHalls() == null) t.setHalls(new ArrayList<>());
        model.addAttribute("theatre", t);
        return "theatre/form";
    }

    @PostMapping
    public String create(@ModelAttribute Theatre theatre) {
        if (theatre.getHalls() == null) theatre.setHalls(new ArrayList<>());
        service.save(theatre);
        return "redirect:/theatres";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        Theatre theatre = service.findTheatreWithHalls(id);
        model.addAttribute("theatre", theatre);
        return "theatre/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Theatre t = service.findById(id).orElseThrow();
        if (t.getHalls() == null) t.setHalls(new ArrayList<>());
        model.addAttribute("theatre", t);
        return "theatre/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Theatre theatre) {
        theatre.setId(id);
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
