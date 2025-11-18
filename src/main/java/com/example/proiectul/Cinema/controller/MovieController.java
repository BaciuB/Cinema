package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Movie;
import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.service.MovieService;
import com.example.proiectul.Cinema.service.ScreeningService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final ScreeningService screeningService;

    public MovieController(MovieService movieService, ScreeningService screeningService) {
        this.movieService = movieService;
        this.screeningService = screeningService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("movies", movieService.findAll());
        return "movie/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "movie/form";
    }

    @PostMapping
    public String create(@ModelAttribute Movie movie) {
        movieService.save(movie);
        return "redirect:/movies";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        movieService.deleteById(id);
        return "redirect:/movies";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        Movie m = movieService.findById(id).orElseThrow();
        List<Screening> screenings = screeningService.findByMovieId(id);
        model.addAttribute("movie", m);
        model.addAttribute("screenings", screenings);
        return "movie/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Movie m = movieService.findById(id).orElseThrow();
        model.addAttribute("movie", m);
        return "movie/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Movie movie) {
        movie.setId(id);
        movieService.save(movie);
        return "redirect:/movies";
    }

}
//sa o pun partea cu screening in movie service
//baze de date si asa mai departe
//sa populez in .json ce nu e populat