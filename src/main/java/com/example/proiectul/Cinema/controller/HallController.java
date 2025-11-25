package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Hall;
import com.example.proiectul.Cinema.service.HallService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/halls")
public class HallController {

    private final HallService service;

    public HallController(HallService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("halls", service.findAll());
        return "hall/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("hall", new Hall());
        return "hall/form";
    }

    @PostMapping
    public String create(@ModelAttribute Hall hall) {
        service.save(hall);
        return "redirect:/halls";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        Hall hall = service.findHallWithRelations(id);
        model.addAttribute("hall", hall);
        return "hall/details";
    }


    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Hall h = service.findById(id).orElseThrow();
        model.addAttribute("hall", h);
        return "hall/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Hall hall) {
        hall.setId(id);
        service.save(hall);
        return "redirect:/halls";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/halls";
    }
}
