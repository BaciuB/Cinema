package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Hall;
import com.example.proiectul.Cinema.service.HallService;
import com.example.proiectul.Cinema.service.TheatreService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/halls")
public class HallController {

    private final HallService hallService;
    private final TheatreService theatreService;

    public HallController(HallService hallService, TheatreService theatreService) {
        this.hallService = hallService;
        this.theatreService = theatreService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("halls", hallService.findAll());
        return "hall/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("hall", new Hall());
        model.addAttribute("theatres", theatreService.findAll());
        return "hall/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("hall") Hall hall,
            BindingResult bindingResult,
            Model model) {

        String theatreId = hall.getTheatre() != null ? hall.getTheatre().getId() : null;

        if (theatreId == null) {
            bindingResult.rejectValue("theatre", "theatre.required", "Theatre is required");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("theatres", theatreService.findAll());
            return "hall/form";
        }

        hall.setTheatre(theatreService.findById(theatreId).orElseThrow());
        hallService.save(hall);
        return "redirect:/halls";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model, RedirectAttributes ra) {
        try {
            Hall hall = hallService.findHallWithRelations(id);
            model.addAttribute("hall", hall);
            model.addAttribute("seats", hall.getSeats());
            model.addAttribute("screenings", hall.getScreenings());
            return "hall/details";
        } catch (Exception e) {
            ra.addFlashAttribute("globalError", "Hall not found");
            return "redirect:/halls";
        }
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes ra) {
        return hallService.findById(id)
                .map(hall -> {
                    model.addAttribute("hall", hall);
                    model.addAttribute("theatres", theatreService.findAll());
                    return "hall/edit";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Hall not found");
                    return "redirect:/halls";
                });
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("hall") Hall hall,
            BindingResult bindingResult,
            Model model) {

        String theatreId = hall.getTheatre() != null ? hall.getTheatre().getId() : null;

        if (theatreId == null) {
            bindingResult.rejectValue("theatre", "theatre.required", "Theatre is required");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("theatres", theatreService.findAll());
            return "hall/edit";
        }

        hall.setId(id);
        hall.setTheatre(theatreService.findById(theatreId).orElseThrow());
        hallService.save(hall);
        return "redirect:/halls";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            hallService.deleteById(id);
            ra.addFlashAttribute("successMessage", "Hall deleted successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("globalError", "Could not delete hall.");
        }
        return "redirect:/halls";
    }
}
