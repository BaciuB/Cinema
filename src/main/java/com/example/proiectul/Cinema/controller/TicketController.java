package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Ticket;
import com.example.proiectul.Cinema.service.CustomerService;
import com.example.proiectul.Cinema.service.ScreeningService;
import com.example.proiectul.Cinema.service.SeatService;
import com.example.proiectul.Cinema.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final ScreeningService screeningService;
    private final CustomerService customerService;
    private final SeatService seatService;

    public TicketController(TicketService ticketService,
                            ScreeningService screeningService,
                            CustomerService customerService,
                            SeatService seatService) {
        this.ticketService = ticketService;
        this.screeningService = screeningService;
        this.customerService = customerService;
        this.seatService = seatService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tickets", ticketService.findAll());
        return "ticket/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("screenings", screeningService.findAll());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("seats", seatService.findAll());
        return "ticket/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("ticket") Ticket ticket,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes ra) {

        String screeningId = ticket.getScreening() != null ? ticket.getScreening().getId() : null;
        String customerId = ticket.getCustomer() != null ? ticket.getCustomer().getId() : null;
        String seatId = ticket.getSeat() != null ? ticket.getSeat().getId() : null;

        if (screeningId == null)
            bindingResult.rejectValue("screening", "screening.required", "Screening is required");
        if (customerId == null)
            bindingResult.rejectValue("customer", "customer.required", "Customer is required");
        if (seatId == null)
            bindingResult.rejectValue("seat", "seat.required", "Seat is required");

        if (screeningId != null && seatId != null &&
                ticketService.seatAlreadyBookedForScreening(screeningId, seatId)) {
            bindingResult.rejectValue("seat", "seat.booked", "Seat already booked for this screening");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("screenings", screeningService.findAll());
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("seats", seatService.findAll());
            return "ticket/form";
        }

        ticket.setScreening(screeningService.findById(screeningId).orElseThrow());
        ticket.setCustomer(customerService.findById(customerId).orElseThrow());
        ticket.setSeat(seatService.findById(seatId).orElseThrow());

        ticketService.save(ticket);
        ra.addFlashAttribute("successMessage", "Ticket created successfully!");
        return "redirect:/tickets";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model, RedirectAttributes ra) {
        return ticketService.findById(id)
                .map(ticket -> {
                    model.addAttribute("ticket", ticket);
                    return "ticket/details";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Ticket not found");
                    return "redirect:/tickets";
                });
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes ra) {
        return ticketService.findById(id)
                .map(ticket -> {
                    model.addAttribute("ticket", ticket);
                    model.addAttribute("screenings", screeningService.findAll());
                    model.addAttribute("customers", customerService.findAll());
                    model.addAttribute("seats", seatService.findAll());
                    return "ticket/edit";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Ticket not found");
                    return "redirect:/tickets";
                });
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("ticket") Ticket ticket,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes ra) {

        String screeningId = ticket.getScreening() != null ? ticket.getScreening().getId() : null;
        String customerId = ticket.getCustomer() != null ? ticket.getCustomer().getId() : null;
        String seatId = ticket.getSeat() != null ? ticket.getSeat().getId() : null;

        if (screeningId == null)
            bindingResult.rejectValue("screening", "screening.required", "Screening is required");
        if (customerId == null)
            bindingResult.rejectValue("customer", "customer.required", "Customer is required");
        if (seatId == null)
            bindingResult.rejectValue("seat", "seat.required", "Seat is required");

        if (bindingResult.hasErrors()) {
            model.addAttribute("screenings", screeningService.findAll());
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("seats", seatService.findAll());
            return "ticket/edit";
        }

        ticket.setId(id);
        ticket.setScreening(screeningService.findById(screeningId).orElseThrow());
        ticket.setCustomer(customerService.findById(customerId).orElseThrow());
        ticket.setSeat(seatService.findById(seatId).orElseThrow());

        ticketService.save(ticket);
        ra.addFlashAttribute("successMessage", "Ticket updated successfully!");
        return "redirect:/tickets";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            ticketService.deleteById(id);
            ra.addFlashAttribute("successMessage", "Ticket deleted successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("globalError", "Could not delete ticket.");
        }
        return "redirect:/tickets";
    }
}
