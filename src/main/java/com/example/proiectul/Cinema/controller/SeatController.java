package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Seat;
import com.example.proiectul.Cinema.service.SeatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/seats")
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("seats", seatService.findAll());
        return "seats/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("seat", new Seat());
        return "seats/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Seat seat) {
        seatService.save(seat);
        return "redirect:/seats";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        seatService.deleteById(id);
        return "redirect:/seats";
    }
}
