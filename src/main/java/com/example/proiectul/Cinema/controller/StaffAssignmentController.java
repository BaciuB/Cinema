package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Staff;
import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.model.StaffAssignment;
import com.example.proiectul.Cinema.service.ScreeningService;
import com.example.proiectul.Cinema.service.StaffAssignmentService;
import com.example.proiectul.Cinema.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/assignments")
public class StaffAssignmentController {

    private final StaffAssignmentService service;
    private final ScreeningService screeningService;
    private final StaffService staffService;

    public StaffAssignmentController(
            StaffAssignmentService service,
            ScreeningService screeningService,
            StaffService staffService
    ) {
        this.service = service;
        this.screeningService = screeningService;
        this.staffService = staffService;
    }

    // LIST
    @GetMapping
    public String index(Model m){
        m.addAttribute("assignments", service.findAll());
        return "staffassignment/index";
    }

    // CREATE FORM
    @GetMapping("/new")
    public String newForm(Model m){
        m.addAttribute("assignment", new StaffAssignment());
        m.addAttribute("screenings", screeningService.findAll());
        m.addAttribute("staffList", staffService.findAll());
        return "staffassignment/form";
    }

    // CREATE POST
    @PostMapping
    public String create(
            @Valid @ModelAttribute("assignment") StaffAssignment assignment,
            BindingResult binding,
            @RequestParam("screeningId") String screeningId,
            @RequestParam("staffId") String staffId,
            Model m,
            RedirectAttributes ra
    ) {

        // business validation
        Screening screening = screeningService.findById(screeningId).orElse(null);
        Staff staff = staffService.findById(staffId).orElse(null);

        if (screening == null) {
            binding.rejectValue("screening", "screening.invalid", "Invalid screening selected");
        }
        if (staff == null) {
            binding.rejectValue("staff", "staff.invalid", "Invalid staff selected");
        }

        if (binding.hasErrors()) {
            m.addAttribute("screenings", screeningService.findAll());
            m.addAttribute("staffList", staffService.findAll());
            return "staffassignment/form";
        }

        assignment.setScreening(screening);
        assignment.setStaff(staff);

        service.save(assignment);
        ra.addFlashAttribute("successMessage", "Assignment added successfully!");
        return "redirect:/assignments";
    }

    // DETAILS
    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model m){
        m.addAttribute("assignment", service.findById(id).orElseThrow());
        return "staffassignment/details";
    }

    // EDIT FORM
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model m){
        m.addAttribute("assignment", service.findById(id).orElseThrow());
        m.addAttribute("screenings", screeningService.findAll());
        m.addAttribute("staffList", staffService.findAll());
        return "staffassignment/edit";
    }

    // UPDATE POST
    @PostMapping("/{id}")
    public String update(
            @PathVariable String id,
            @Valid @ModelAttribute("assignment") StaffAssignment assignment,
            BindingResult binding,
            @RequestParam("screeningId") String screeningId,
            @RequestParam("staffId") String staffId,
            Model m,
            RedirectAttributes ra
    ) {

        Screening screening = screeningService.findById(screeningId).orElse(null);
        Staff staff = staffService.findById(staffId).orElse(null);

        if (screening == null) {
            binding.rejectValue("screening", "screening.invalid", "Invalid screening selected");
        }
        if (staff == null) {
            binding.rejectValue("staff", "staff.invalid", "Invalid staff selected");
        }

        if (binding.hasErrors()) {
            m.addAttribute("screenings", screeningService.findAll());
            m.addAttribute("staffList", staffService.findAll());
            return "staffassignment/edit";
        }

        assignment.setId(id);
        assignment.setScreening(screening);
        assignment.setStaff(staff);

        service.save(assignment);
        ra.addFlashAttribute("successMessage", "Assignment updated successfully!");
        return "redirect:/assignments";
    }

    // DELETE
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id){
        service.deleteById(id);
        return "redirect:/assignments";
    }
}
