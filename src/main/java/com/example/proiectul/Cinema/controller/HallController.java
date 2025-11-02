package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Hall;
import com.example.proiectul.Cinema.service.HallService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/halls")
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("halls", hallService.findAll());
        return "hall/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Hall h = new Hall();
        h.setSeats(new ArrayList<>());
        h.setScreenings(new ArrayList<>());
        model.addAttribute("hall", h);
        return "hall/form";
    }

    @PostMapping
    public String create(@ModelAttribute Hall hall) {
        if (hall.getSeats() == null) hall.setSeats(new ArrayList<>());
        if (hall.getScreenings() == null) hall.setScreenings(new ArrayList<>());
        hallService.save(hall);
        return "redirect:/halls";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        hallService.deleteById(id);
        return "redirect:/halls";
    }
}
