package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Movie;
import com.example.proiectul.Cinema.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(required = false) String title,
                        @RequestParam(required = false) Integer minDuration,
                        @RequestParam(required = false) Integer maxDuration,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseFrom,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseTo,
                        @RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "ASC") String dir,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "0") int page) {

        Sort sort = Sort.by(Sort.Direction.fromString(dir), sortBy);
        var pageable = PageRequest.of(page, size, sort);

        var moviesPage = service.findWithFilters(title, minDuration, maxDuration, releaseFrom, releaseTo, pageable);

        model.addAttribute("moviesPage", moviesPage);

        model.addAttribute("title", title);
        model.addAttribute("minDuration", minDuration);
        model.addAttribute("maxDuration", maxDuration);
        model.addAttribute("releaseFrom", releaseFrom);
        model.addAttribute("releaseTo", releaseTo);

        model.addAttribute("sortBy", sortBy);
        model.addAttribute("dir", dir);
        model.addAttribute("size", size);

        return "movie/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movie/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("movie") Movie movie,
                         BindingResult br) {

        if (service.existsByTitle(movie.getTitle())) {
            br.rejectValue("title", "duplicate", "A movie with this title already exists");
        }

        if (br.hasErrors()) return "movie/form";

        service.save(movie);
        return "redirect:/movies";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            service.deleteById(id);
            ra.addFlashAttribute("successMessage", "Movie deleted successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("globalError", "Could not delete movie.");
        }
        return "redirect:/movies";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model, RedirectAttributes ra) {
        return service.findById(id)
                .map(movie -> {
                    model.addAttribute("movie", movie);
                    model.addAttribute("screenings", movie.getScreenings());
                    return "movie/details";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Movie not found.");
                    return "redirect:/movies";
                });
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model, RedirectAttributes ra) {
        return service.findById(id)
                .map(movie -> {
                    model.addAttribute("movie", movie);
                    return "movie/edit";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Movie not found.");
                    return "redirect:/movies";
                });
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("movie") Movie movie,
                         BindingResult br) {

        if (service.existsAnotherWithTitle(id, movie.getTitle())) {
            br.rejectValue("title", "duplicate", "Another movie with this title already exists");
        }

        if (br.hasErrors()) return "movie/edit";

        movie.setId(id);
        service.save(movie);
        return "redirect:/movies";
    }
}
