package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.StaffAssignment;
import com.example.proiectul.Cinema.service.StaffAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assignments")
public class StaffAssignmentController {
    private final StaffAssignmentService service;
    public StaffAssignmentController(StaffAssignmentService s){ this.service = s; }

    @GetMapping
    public String index(Model m){
        m.addAttribute("assignments", service.findAll());
        return "staffassignment/index";
    }

    @GetMapping("/new")
    public String newForm(Model m){
        m.addAttribute("assignment", new StaffAssignment());
        return "staffassignment/form";
    }

    @PostMapping
    public String create(@ModelAttribute StaffAssignment a){
        service.save(a);
        return "redirect:/assignments";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model m){
        m.addAttribute("assignment", service.findById(id).orElseThrow());
        return "staffassignment/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model m){
        m.addAttribute("assignment", service.findById(id).orElseThrow());
        return "staffassignment/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute StaffAssignment a){
        a.setId(id);
        service.save(a);
        return "redirect:/assignments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id){
        service.deleteById(id);
        return "redirect:/assignments";
    }
}
