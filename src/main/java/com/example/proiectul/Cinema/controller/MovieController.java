package com.example.proiectul.Cinema.controller;
import java.time.LocalDate;
import com.example.proiectul.Cinema.model.Movie;
import com.example.proiectul.Cinema.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public String index(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Integer minDuration,
            @RequestParam(required = false) Integer maxDuration,
            @RequestParam(required = false) LocalDate releaseFrom,
            @RequestParam(required = false) LocalDate releaseTo,
            @RequestParam(defaultValue = "title") String sort,
            @RequestParam(defaultValue = "asc") String dir,
            Model model
    ) {

        model.addAttribute("movies",
                movieService.findFilteredAndSorted(
                        title,
                        minDuration,
                        maxDuration,
                        releaseFrom,
                        releaseTo,
                        sort,
                        dir
                )
        );

        /* păstrează valorile în formular */
        model.addAttribute("title", title);
        model.addAttribute("minDuration", minDuration);
        model.addAttribute("maxDuration", maxDuration);
        model.addAttribute("releaseFrom", releaseFrom);
        model.addAttribute("releaseTo", releaseTo);
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);

        return "movie/index";
    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movie/form";
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute("movie") Movie movie,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes ra) {

        // business rule: titlu unic
        if (!bindingResult.hasFieldErrors("title")
                && movieService.existsByTitle(movie.getTitle())) {
            bindingResult.rejectValue("title", "duplicate",
                    "A movie with this title already exists");
        }

        if (bindingResult.hasErrors()) {
            return "movie/form";
        }

        movieService.save(movie);
        ra.addFlashAttribute("successMessage", "Movie created successfully.");
        return "redirect:/movies";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            movieService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Movie deleted successfully.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("globalError", "Could not delete movie.");
        }
        return "redirect:/movies";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        return movieService.findById(id)
                .map(movie -> {
                    model.addAttribute("movie", movie);
                    model.addAttribute("screenings", movie.getScreenings());
                    return "movie/details";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("globalError", "Movie not found.");
                    return "redirect:/movies";
                });
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model, RedirectAttributes redirectAttributes) {
        return movieService.findById(id)
                .map(movie -> {
                    model.addAttribute("movie", movie);
                    return "movie/edit";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("globalError", "Movie not found.");
                    return "redirect:/movies";
                });
    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("movie") Movie movie,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes ra) {

        // business rule: titlu unic la alt film
        if (!bindingResult.hasFieldErrors("title")
                && movieService.existsAnotherWithTitle(id, movie.getTitle())) {
            bindingResult.rejectValue("title", "duplicate",
                    "Another movie with this title already exists");
        }

        if (bindingResult.hasErrors()) {
            return "movie/edit";
        }

        movie.setId(id);
        movieService.save(movie);
        ra.addFlashAttribute("successMessage", "Movie updated successfully.");
        return "redirect:/movies";
    }
}
