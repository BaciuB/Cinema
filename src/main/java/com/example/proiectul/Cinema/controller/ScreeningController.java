package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.service.HallService;
import com.example.proiectul.Cinema.service.MovieService;
import com.example.proiectul.Cinema.service.ScreeningService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/screenings")
public class ScreeningController {

    private final ScreeningService screeningService;
    private final HallService hallService;
    private final MovieService movieService;

    public ScreeningController(ScreeningService screeningService,
                               HallService hallService,
                               MovieService movieService) {
        this.screeningService = screeningService;
        this.hallService = hallService;
        this.movieService = movieService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("screenings", screeningService.findAll());
        return "screening/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("screening", new Screening());
        model.addAttribute("halls", hallService.findAll());
        model.addAttribute("movies", movieService.findAll());
        return "screening/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("screening") Screening screening,
            BindingResult bindingResult,
            Model model) {

        String hallId = screening.getHall() != null ? screening.getHall().getId() : null;
        String movieId = screening.getMovie() != null ? screening.getMovie().getId() : null;

        if (hallId == null) {
            bindingResult.rejectValue("hall", "hall.required", "Hall is required");
        }
        if (movieId == null) {
            bindingResult.rejectValue("movie", "movie.required", "Movie is required");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("halls", hallService.findAll());
            model.addAttribute("movies", movieService.findAll());
            return "screening/form";
        }

        screening.setHall(hallService.findById(hallId).orElseThrow());
        screening.setMovie(movieService.findById(movieId).orElseThrow());

        screeningService.save(screening);
        return "redirect:/screenings";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model, RedirectAttributes ra) {
        return screeningService.findById(id)
                .map(screening -> {
                    model.addAttribute("screening", screening);
                    return "screening/details";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Screening not found");
                    return "redirect:/screenings";
                });
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes ra) {
        return screeningService.findById(id)
                .map(screening -> {
                    model.addAttribute("screening", screening);
                    model.addAttribute("halls", hallService.findAll());
                    model.addAttribute("movies", movieService.findAll());
                    return "screening/edit";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Screening not found");
                    return "redirect:/screenings";
                });
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("screening") Screening screening,
            BindingResult bindingResult,
            Model model) {

        String hallId = screening.getHall() != null ? screening.getHall().getId() : null;
        String movieId = screening.getMovie() != null ? screening.getMovie().getId() : null;

        if (hallId == null) {
            bindingResult.rejectValue("hall", "hall.required", "Hall is required");
        }
        if (movieId == null) {
            bindingResult.rejectValue("movie", "movie.required", "Movie is required");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("halls", hallService.findAll());
            model.addAttribute("movies", movieService.findAll());
            return "screening/edit";
        }

        screening.setId(id);
        screening.setHall(hallService.findById(hallId).orElseThrow());
        screening.setMovie(movieService.findById(movieId).orElseThrow());

        screeningService.save(screening);
        return "redirect:/screenings";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            screeningService.deleteById(id);
            ra.addFlashAttribute("successMessage", "Screening deleted successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("globalError", "Could not delete screening.");
        }
        return "redirect:/screenings";
    }
}
