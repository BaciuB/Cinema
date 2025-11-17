package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.TechnicalOperator;
import com.example.proiectul.Cinema.service.TechnicalOperatorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller @RequestMapping("/technicaloperators")
public class TechnicalOperatorController {
    private final TechnicalOperatorService service;
    public TechnicalOperatorController(TechnicalOperatorService s){ this.service = s; }

    @GetMapping
    public String index(Model m){
        m.addAttribute("technicaloperators", service.findAll());
        return "technicaloperator/index";
    }

    @GetMapping("/new")
    public String newForm(Model m){
        m.addAttribute("technicaloperator", new TechnicalOperator());
        return "technicaloperator/form";
    }

    @PostMapping
    public String create(@ModelAttribute TechnicalOperator o){
        service.save(o);
        return "redirect:/technicaloperators";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model m){
        m.addAttribute("technicaloperator", service.findById(id).orElseThrow());
        return "technicaloperator/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model m){
        m.addAttribute("technicaloperator", service.findById(id).orElseThrow());
        return "technicaloperator/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute TechnicalOperator o){
        o.setId(id); service.save(o);
        return "redirect:/technicaloperators";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id){
        service.deleteById(id);
        return "redirect:/technicaloperators";
    }

}