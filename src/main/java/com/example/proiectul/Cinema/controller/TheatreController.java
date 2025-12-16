package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Theatre;
import com.example.proiectul.Cinema.service.HallService;
import com.example.proiectul.Cinema.service.TheatreService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/theatres")
public class TheatreController {

    private final TheatreService theatreService;
    private final HallService hallService;

    public TheatreController(TheatreService theatreService, HallService hallService) {
        this.theatreService = theatreService;
        this.hallService = hallService;
    }

    @GetMapping
    public String index(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String dir,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        var sort = "DESC".equalsIgnoreCase(dir)
                ? org.springframework.data.domain.Sort.by(sortBy).descending()
                : org.springframework.data.domain.Sort.by(sortBy).ascending();

        var pageable = org.springframework.data.domain.PageRequest.of(page, size, sort);

        var theatresPage = theatreService.findWithFilters(name, city, pageable);

        model.addAttribute("theatresPage", theatresPage);
        model.addAttribute("theatres", theatresPage.getContent());

        model.addAttribute("name", name);
        model.addAttribute("city", city);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("dir", dir);
        model.addAttribute("size", size);

        return "theatre/index";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("theatre", new Theatre());
        return "theatre/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("theatre") Theatre theatre,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "theatre/form";
        }

        theatreService.save(theatre);
        return "redirect:/theatres";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model, RedirectAttributes ra) {
        return theatreService.findById(id)
                .map(theatre -> {
                    model.addAttribute("theatre", theatre);
                    model.addAttribute("halls", hallService.findByTheatreId(id));
                    return "theatre/details";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Theatre not found");
                    return "redirect:/theatres";
                });
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes ra) {
        return theatreService.findById(id)
                .map(theatre -> {
                    model.addAttribute("theatre", theatre);
                    return "theatre/edit";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Theatre not found");
                    return "redirect:/theatres";
                });
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("theatre") Theatre theatre,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "theatre/edit";
        }

        theatre.setId(id);
        theatreService.save(theatre);
        return "redirect:/theatres";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            theatreService.deleteById(id);
            ra.addFlashAttribute("successMessage", "Theatre deleted successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("globalError", "Could not delete theatre.");
        }
        return "redirect:/theatres";
    }
}
