package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.model.Hall;
import com.example.proiectul.Cinema.model.Movie;
import com.example.proiectul.Cinema.service.HallService;
import com.example.proiectul.Cinema.service.MovieService;
import com.example.proiectul.Cinema.service.ScreeningService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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
    public String create(@Valid @ModelAttribute("screening") Screening screening,
                         BindingResult bindingResult,
                         Model model) {

        String hallId = screening.getHall() != null ? screening.getHall().getId() : null;
        String movieId = screening.getMovie() != null ? screening.getMovie().getId() : null;

        // BUSINESS: Hall obligatoriu și existent
        if (hallId == null || hallId.isBlank()) {
            bindingResult.rejectValue("hall", "hall.required", "Hall is required");
        } else {
            Optional<Hall> hallOpt = hallService.findById(hallId);
            if (hallOpt.isEmpty()) {
                bindingResult.rejectValue("hall", "hall.notfound", "Selected hall does not exist");
            } else {
                screening.setHall(hallOpt.get());
            }
        }

        // BUSINESS: Movie obligatoriu și existent
        if (movieId == null || movieId.isBlank()) {
            bindingResult.rejectValue("movie", "movie.required", "Movie is required");
        } else {
            Optional<Movie> movieOpt = movieService.findById(movieId);
            if (movieOpt.isEmpty()) {
                bindingResult.rejectValue("movie", "movie.notfound", "Selected movie does not exist");
            } else {
                screening.setMovie(movieOpt.get());
            }
        }

        // Bean Validation (id, dateTime) + business -> rămânem în form dacă sunt erori
        if (bindingResult.hasErrors()) {
            model.addAttribute("halls", hallService.findAll());
            model.addAttribute("movies", movieService.findAll());
            return "screening/form";
        }

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
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("screening") Screening screening,
                         BindingResult bindingResult,
                         Model model) {

        screening.setId(id);

        String hallId = screening.getHall() != null ? screening.getHall().getId() : null;
        String movieId = screening.getMovie() != null ? screening.getMovie().getId() : null;

        // BUSINESS: Hall obligatoriu și existent
        if (hallId == null || hallId.isBlank()) {
            bindingResult.rejectValue("hall", "hall.required", "Hall is required");
        } else {
            Optional<Hall> hallOpt = hallService.findById(hallId);
            if (hallOpt.isEmpty()) {
                bindingResult.rejectValue("hall", "hall.notfound", "Selected hall does not exist");
            } else {
                screening.setHall(hallOpt.get());
            }
        }

        // BUSINESS: Movie obligatoriu și existent
        if (movieId == null || movieId.isBlank()) {
            bindingResult.rejectValue("movie", "movie.required", "Movie is required");
        } else {
            Optional<Movie> movieOpt = movieService.findById(movieId);
            if (movieOpt.isEmpty()) {
                bindingResult.rejectValue("movie", "movie.notfound", "Selected movie does not exist");
            } else {
                screening.setMovie(movieOpt.get());
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("halls", hallService.findAll());
            model.addAttribute("movies", movieService.findAll());
            return "screening/edit";
        }

        screeningService.save(screening);
        return "redirect:/screenings";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {

        var opt = screeningService.findById(id);
        if (opt.isEmpty()) {
            ra.addFlashAttribute("globalError", "Screening not found");
            return "redirect:/screenings";
        }

        var screening = opt.get();
        boolean hasTickets = screening.getTickets() != null && !screening.getTickets().isEmpty();
        boolean hasAssignments = screening.getAssignments() != null && !screening.getAssignments().isEmpty();

        // BUSINESS: nu șterge screening cu bilete sau staff assignments
        if (hasTickets || hasAssignments) {
            ra.addFlashAttribute("globalError",
                    "Screening cannot be deleted because it has tickets or staff assignments.");
            return "redirect:/screenings/" + id;
        }

        screeningService.deleteById(id);
        ra.addFlashAttribute("successMessage", "Screening deleted successfully.");
        return "redirect:/screenings";
    }
}
