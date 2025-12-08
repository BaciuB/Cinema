package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Hall;
import com.example.proiectul.Cinema.model.Theatre;
import com.example.proiectul.Cinema.service.HallService;
import com.example.proiectul.Cinema.service.TheatreService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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
    public String create(@Valid @ModelAttribute("hall") Hall hall,
                         BindingResult bindingResult,
                         Model model) {

        String theatreId = (hall.getTheatre() != null ? hall.getTheatre().getId() : null);

        // BUSINESS VALIDATION: Theatre obligatoriu
        if (theatreId == null || theatreId.isBlank()) {
            bindingResult.rejectValue("theatre", "theatre.required", "Theatre is required");
        } else {
            // BUSINESS VALIDATION: Theatre trebuie să existe în DB
            Optional<Theatre> theatreOpt = theatreService.findById(theatreId);
            if (theatreOpt.isEmpty()) {
                bindingResult.rejectValue("theatre", "theatre.notfound", "Selected theatre does not exist");
            } else {
                hall.setTheatre(theatreOpt.get());
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("theatres", theatreService.findAll());
            return "hall/form";
        }

        hallService.save(hall);
        return "redirect:/halls";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id,
                          Model model,
                          RedirectAttributes ra) {

        Hall hall = hallService.findHallWithRelations(id);
        if (hall == null) {
            ra.addFlashAttribute("globalError", "Hall not found.");
            return "redirect:/halls";
        }

        model.addAttribute("hall", hall);
        model.addAttribute("seats", hall.getSeats());
        model.addAttribute("screenings", hall.getScreenings());
        return "hall/details";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id,
                               Model model,
                               RedirectAttributes ra) {

        return hallService.findById(id)
                .map(hall -> {
                    model.addAttribute("hall", hall);
                    model.addAttribute("theatres", theatreService.findAll());
                    return "hall/edit";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Hall not found.");
                    return "redirect:/halls";
                });
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("hall") Hall hall,
                         BindingResult bindingResult,
                         Model model) {

        hall.setId(id);

        String theatreId = (hall.getTheatre() != null ? hall.getTheatre().getId() : null);

        // BUSINESS VALIDATION: Theatre obligatoriu și existent
        if (theatreId == null || theatreId.isBlank()) {
            bindingResult.rejectValue("theatre", "theatre.required", "Theatre is required");
        } else {
            Optional<Theatre> theatreOpt = theatreService.findById(theatreId);
            if (theatreOpt.isEmpty()) {
                bindingResult.rejectValue("theatre", "theatre.notfound", "Selected theatre does not exist");
            } else {
                hall.setTheatre(theatreOpt.get());
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("theatres", theatreService.findAll());
            return "hall/edit";
        }

        hallService.save(hall);
        return "redirect:/halls";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {

        Hall hall = hallService.findHallWithRelations(id);
        if (hall == null) {
            ra.addFlashAttribute("globalError", "Hall not found.");
            return "redirect:/halls";
        }

        // BUSINESS RULE: nu șterge hall dacă are seats sau screenings
        boolean hasSeats = hall.getSeats() != null && !hall.getSeats().isEmpty();
        boolean hasScreenings = hall.getScreenings() != null && !hall.getScreenings().isEmpty();

        if (hasSeats || hasScreenings) {
            ra.addFlashAttribute("globalError",
                    "Hall cannot be deleted because it has seats or screenings assigned.");
            return "redirect:/halls/" + id;
        }

        hallService.deleteById(id);
        ra.addFlashAttribute("successMessage", "Hall deleted successfully.");
        return "redirect:/halls";
    }
}
