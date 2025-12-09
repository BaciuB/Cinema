package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Ticket;
import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.model.Customer;
import com.example.proiectul.Cinema.model.Seat;
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

import java.util.Optional;

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
    public String create(@Valid @ModelAttribute("ticket") Ticket ticket,
                         BindingResult bindingResult,
                         Model model) {

        String screeningId = ticket.getScreening() != null ? ticket.getScreening().getId() : null;
        String customerId = ticket.getCustomer() != null ? ticket.getCustomer().getId() : null;
        String seatId = ticket.getSeat() != null ? ticket.getSeat().getId() : null;

        // BUSINESS: Screening obligatoriu și existent
        if (screeningId == null || screeningId.isBlank()) {
            bindingResult.rejectValue("screening", "screening.required", "Screening is required");
        } else {
            Optional<Screening> screeningOpt = screeningService.findById(screeningId);
            if (screeningOpt.isEmpty()) {
                bindingResult.rejectValue("screening", "screening.notfound", "Selected screening does not exist");
            } else {
                ticket.setScreening(screeningOpt.get());
            }
        }

        // BUSINESS: Customer obligatoriu și existent
        if (customerId == null || customerId.isBlank()) {
            bindingResult.rejectValue("customer", "customer.required", "Customer is required");
        } else {
            Optional<Customer> customerOpt = customerService.findById(customerId);
            if (customerOpt.isEmpty()) {
                bindingResult.rejectValue("customer", "customer.notfound", "Selected customer does not exist");
            } else {
                ticket.setCustomer(customerOpt.get());
            }
        }

        // BUSINESS: Seat obligatoriu și existent
        if (seatId == null || seatId.isBlank()) {
            bindingResult.rejectValue("seat", "seat.required", "Seat is required");
        } else {
            Optional<Seat> seatOpt = seatService.findById(seatId);
            if (seatOpt.isEmpty()) {
                bindingResult.rejectValue("seat", "seat.notfound", "Selected seat does not exist");
            } else {
                ticket.setSeat(seatOpt.get());
            }
        }

        // BUSINESS: seat să nu fie deja rezervat pentru screening (doar dacă screening + seat nu au erori)
        if (!bindingResult.hasFieldErrors("screening")
                && !bindingResult.hasFieldErrors("seat")
                && screeningId != null && seatId != null) {

            if (ticketService.seatAlreadyBookedForScreening(screeningId, seatId)) {
                bindingResult.rejectValue("seat", "seat.booked", "Seat already booked for this screening");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("screenings", screeningService.findAll());
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("seats", seatService.findAll());
            return "ticket/form";
        }

        ticketService.save(ticket);
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
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("ticket") Ticket ticket,
                         BindingResult bindingResult,
                         Model model) {

        ticket.setId(id);

        String screeningId = ticket.getScreening() != null ? ticket.getScreening().getId() : null;
        String customerId = ticket.getCustomer() != null ? ticket.getCustomer().getId() : null;
        String seatId = ticket.getSeat() != null ? ticket.getSeat().getId() : null;

        // BUSINESS: Screening obligatoriu și existent
        if (screeningId == null || screeningId.isBlank()) {
            bindingResult.rejectValue("screening", "screening.required", "Screening is required");
        } else {
            Optional<Screening> screeningOpt = screeningService.findById(screeningId);
            if (screeningOpt.isEmpty()) {
                bindingResult.rejectValue("screening", "screening.notfound", "Selected screening does not exist");
            } else {
                ticket.setScreening(screeningOpt.get());
            }
        }

        // BUSINESS: Customer obligatoriu și existent
        if (customerId == null || customerId.isBlank()) {
            bindingResult.rejectValue("customer", "customer.required", "Customer is required");
        } else {
            Optional<Customer> customerOpt = customerService.findById(customerId);
            if (customerOpt.isEmpty()) {
                bindingResult.rejectValue("customer", "customer.notfound", "Selected customer does not exist");
            } else {
                ticket.setCustomer(customerOpt.get());
            }
        }

        // BUSINESS: Seat obligatoriu și existent
        if (seatId == null || seatId.isBlank()) {
            bindingResult.rejectValue("seat", "seat.required", "Seat is required");
        } else {
            Optional<Seat> seatOpt = seatService.findById(seatId);
            if (seatOpt.isEmpty()) {
                bindingResult.rejectValue("seat", "seat.notfound", "Selected seat does not exist");
            } else {
                ticket.setSeat(seatOpt.get());
            }
        }

        // Pentru update nu mai verific seatAlreadyBookedForScreening,
        // ca să nu lovim propriul ticket (în funcție de implementarea din service).
        // Dacă vrei și acolo, putem face o metodă în service care ignoră ticketul curent.

        if (bindingResult.hasErrors()) {
            model.addAttribute("screenings", screeningService.findAll());
            model.addAttribute("customers", customerService.findAll());
            model.addAttribute("seats", seatService.findAll());
            return "ticket/edit";
        }

        ticketService.save(ticket);
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
