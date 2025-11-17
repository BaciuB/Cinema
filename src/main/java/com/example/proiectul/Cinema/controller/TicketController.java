package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Ticket;
import com.example.proiectul.Cinema.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) { this.service = service; }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tickets", service.findAll());
        return "ticket/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "ticket/form";
    }

    @PostMapping
    public String create(@ModelAttribute Ticket ticket) {
        service.save(ticket);
        return "redirect:/tickets";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        model.addAttribute("ticket", service.findById(id).orElseThrow());
        return "ticket/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("ticket", service.findById(id).orElseThrow());
        return "ticket/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Ticket ticket) {
        ticket.setId(id);
        service.save(ticket);
        return "redirect:/tickets";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/tickets";
    }
}
