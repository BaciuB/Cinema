package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Hall;
import com.example.proiectul.Cinema.service.HallService;
import com.example.proiectul.Cinema.service.TheatreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public String index(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minCapacity,
            @RequestParam(required = false) Integer maxCapacity,
            @RequestParam(required = false) String theatreId,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String dir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        Sort sort = "DESC".equalsIgnoreCase(dir) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);

        var hallsPage = hallService.findWithFilters(name, minCapacity, maxCapacity, theatreId, pageable);

        model.addAttribute("hallsPage", hallsPage);
        model.addAttribute("halls", hallsPage.getContent());

        model.addAttribute("name", name);
        model.addAttribute("minCapacity", minCapacity);
        model.addAttribute("maxCapacity", maxCapacity);
        model.addAttribute("theatreId", theatreId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("dir", dir);
        model.addAttribute("size", size);

        model.addAttribute("theatres", theatreService.findAll());

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
            Model model,
            RedirectAttributes ra) {

        String theatreId = hall.getTheatre() != null ? hall.getTheatre().getId() : null;

        if (theatreId == null || theatreId.isBlank()) {
            bindingResult.rejectValue("theatre", "theatre.required", "Theatre is required");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("theatres", theatreService.findAll());
            return "hall/form";
        }

        hall.setTheatre(theatreService.findById(theatreId).orElseThrow());
        hallService.save(hall);
        ra.addFlashAttribute("successMessage", "Hall created successfully.");
        return "redirect:/halls";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model, RedirectAttributes ra) {
        try {
            Hall hall = hallService.findHallWithRelations(id);
            if (hall == null) {
                ra.addFlashAttribute("globalError", "Hall not found");
                return "redirect:/halls";
            }
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
            Model model,
            RedirectAttributes ra) {

        String theatreId = hall.getTheatre() != null ? hall.getTheatre().getId() : null;

        if (theatreId == null || theatreId.isBlank()) {
            bindingResult.rejectValue("theatre", "theatre.required", "Theatre is required");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("theatres", theatreService.findAll());
            return "hall/edit";
        }

        hall.setId(id);
        hall.setTheatre(theatreService.findById(theatreId).orElseThrow());
        hallService.save(hall);
        ra.addFlashAttribute("successMessage", "Hall updated successfully.");
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
