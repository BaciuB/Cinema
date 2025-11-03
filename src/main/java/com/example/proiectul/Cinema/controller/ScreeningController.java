// java
package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.service.ScreeningService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/screenings")
public class ScreeningController {

    private final ScreeningService screeningService;

    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("screenings", screeningService.findAll());
        return "screening/index";
    }

    @GetMapping("/new")
    public String newScreeningForm(Model model) {
        model.addAttribute("screening", new Screening());
        return "screening/form";
    }

    @PostMapping
    public String create(@ModelAttribute Screening screening) {
        screeningService.save(screening);
        return "redirect:/screenings";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        screeningService.deleteById(id);
        return "redirect:/screenings";
    }
}
