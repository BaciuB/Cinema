package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Movie;
import com.example.proiectul.Cinema.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
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
        Movie movie = movieService.findMovieWithScreenings(id);
        model.addAttribute("movie", movie);
        model.addAttribute("screenings", movie.getScreenings());
        return "movie/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        model.addAttribute("movie", movieService.findById(id).orElseThrow());
        return "movie/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Movie movie) {
        movie.setId(id);
        movieService.save(movie);
        return "redirect:/movies";
    }
}