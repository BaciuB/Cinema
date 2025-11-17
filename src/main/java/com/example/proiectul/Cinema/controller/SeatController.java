package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Seat;
import com.example.proiectul.Cinema.service.SeatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/seats")
public class SeatController {
    private final SeatService service;
    public SeatController(SeatService service){ this.service = service; }

    @GetMapping
    public String index(Model m){
        m.addAttribute("seats", service.findAll());
        return "seat/index";
    }

    @GetMapping("/new")
    public String newForm(Model m){
        m.addAttribute("seat", new Seat());
        return "seat/form";
    }

    @PostMapping
    public String create(@ModelAttribute Seat s){
        service.save(s);
        return "redirect:/seats";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model m){
        m.addAttribute("seat", service.findById(id).orElseThrow());
        return "seat/details";
    }
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model m){
        m.addAttribute("seat", service.findById(id).orElseThrow());
        return "seat/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Seat s){
        s.setId(id);
        service.save(s);
        return "redirect:/seats";
    }
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id){
        service.deleteById(id);
        return "redirect:/seats";
    }
}
