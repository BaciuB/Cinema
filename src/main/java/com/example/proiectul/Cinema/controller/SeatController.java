package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Seat;
import com.example.proiectul.Cinema.service.HallService;
import com.example.proiectul.Cinema.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/seats")
public class SeatController {

    private final SeatService seatService;
    private final HallService hallService;

    public SeatController(SeatService seatService, HallService hallService) {
        this.seatService = seatService;
        this.hallService = hallService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("seats", seatService.findAll());
        return "seat/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("seat", new Seat());
        model.addAttribute("halls", hallService.findAll());
        return "seat/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("seat") Seat seat,
                         BindingResult bindingResult,
                         Model model) {

        String hallId = (seat.getHall() != null ? seat.getHall().getId() : null);

        // BUSINESS VALIDATION: hall obligatoriu și existent
        if (hallId == null || hallId.isBlank()) {
            bindingResult.rejectValue("hall", "hall.required", "Hall is required");
        } else {
            var hallOpt = hallService.findById(hallId);
            if (hallOpt.isEmpty()) {
                bindingResult.rejectValue("hall", "hall.notfound", "Selected hall does not exist");
            } else {
                seat.setHall(hallOpt.get());
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("halls", hallService.findAll());
            return "seat/form";
        }

        seatService.save(seat);
        return "redirect:/seats";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model, RedirectAttributes ra) {
        return seatService.findById(id)
                .map(seat -> {
                    model.addAttribute("seat", seat);
                    return "seat/details";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Seat not found");
                    return "redirect:/seats";
                });
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes ra) {
        return seatService.findById(id)
                .map(seat -> {
                    model.addAttribute("seat", seat);
                    model.addAttribute("halls", hallService.findAll());
                    return "seat/edit";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Seat not found");
                    return "redirect:/seats";
                });
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("seat") Seat seat,
                         BindingResult bindingResult,
                         Model model) {

        seat.setId(id);
        String hallId = (seat.getHall() != null ? seat.getHall().getId() : null);

        // BUSINESS VALIDATION: hall obligatoriu și existent
        if (hallId == null || hallId.isBlank()) {
            bindingResult.rejectValue("hall", "hall.required", "Hall is required");
        } else {
            var hallOpt = hallService.findById(hallId);
            if (hallOpt.isEmpty()) {
                bindingResult.rejectValue("hall", "hall.notfound", "Selected hall does not exist");
            } else {
                seat.setHall(hallOpt.get());
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("halls", hallService.findAll());
            return "seat/edit";
        }

        seatService.save(seat);
        return "redirect:/seats";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            seatService.deleteById(id);
            ra.addFlashAttribute("successMessage", "Seat deleted successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("globalError", "Could not delete seat.");
        }
        return "redirect:/seats";
    }
}
