package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Seat;
import com.example.proiectul.Cinema.service.HallService;
import com.example.proiectul.Cinema.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public String index(Model model,
                        @RequestParam(required = false) String hallId,
                        @RequestParam(required = false) Integer rowNumber,
                        @RequestParam(required = false) Integer columnNumber,
                        @RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "ASC") String dir,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "0") int page) {

        Sort sort = dir.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);

        var seatsPage = seatService.findWithFilters(hallId, rowNumber, columnNumber, pageable);

        model.addAttribute("seatsPage", seatsPage);
        model.addAttribute("halls", hallService.findAll());

        model.addAttribute("hallId", hallId);
        model.addAttribute("rowNumber", rowNumber);
        model.addAttribute("columnNumber", columnNumber);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("dir", dir);
        model.addAttribute("size", size);

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
                         BindingResult br,
                         Model model,
                         RedirectAttributes ra) {

        String hallId = seat.getHall() != null ? seat.getHall().getId() : null;
        if (hallId == null || hallId.isBlank()) br.rejectValue("hall", "hall.required", "Hall is required");

        if (!br.hasErrors() && seat.getRowNumber() != null && seat.getColumnNumber() != null && hallId != null) {
            if (seatService.seatExistsInHall(hallId, seat.getRowNumber(), seat.getColumnNumber())) {
                br.rejectValue("columnNumber", "seat.duplicate", "Seat already exists in this hall (row + column)");
            }
        }

        if (br.hasErrors()) {
            model.addAttribute("halls", hallService.findAll());
            return "seat/form";
        }

        seat.setHall(hallService.findById(hallId).orElseThrow());
        seatService.save(seat);

        ra.addFlashAttribute("successMessage", "Seat created successfully.");
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
                         BindingResult br,
                         Model model,
                         RedirectAttributes ra) {

        String hallId = seat.getHall() != null ? seat.getHall().getId() : null;
        if (hallId == null || hallId.isBlank()) br.rejectValue("hall", "hall.required", "Hall is required");

        if (!br.hasErrors() && seat.getRowNumber() != null && seat.getColumnNumber() != null && hallId != null) {
            if (seatService.seatExistsInHallExcept(id, hallId, seat.getRowNumber(), seat.getColumnNumber())) {
                br.rejectValue("columnNumber", "seat.duplicate", "Seat already exists in this hall (row + column)");
            }
        }

        if (br.hasErrors()) {
            model.addAttribute("halls", hallService.findAll());
            return "seat/edit";
        }

        seat.setId(id);
        seat.setHall(hallService.findById(hallId).orElseThrow());
        seatService.save(seat);

        ra.addFlashAttribute("successMessage", "Seat updated successfully.");
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
