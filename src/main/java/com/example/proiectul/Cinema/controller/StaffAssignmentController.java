package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Screening;
import com.example.proiectul.Cinema.model.Staff;
import com.example.proiectul.Cinema.model.StaffAssignment;
import com.example.proiectul.Cinema.service.ScreeningService;
import com.example.proiectul.Cinema.service.StaffAssignmentService;
import com.example.proiectul.Cinema.service.SupportStaffService;
import com.example.proiectul.Cinema.service.TechnicalOperatorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/assignments")
public class StaffAssignmentController {

    private final StaffAssignmentService assignmentService;
    private final ScreeningService screeningService;
    private final TechnicalOperatorService technicalOperatorService;
    private final SupportStaffService supportStaffService;

    public StaffAssignmentController(StaffAssignmentService assignmentService,
                                     ScreeningService screeningService,
                                     TechnicalOperatorService technicalOperatorService,
                                     SupportStaffService supportStaffService) {
        this.assignmentService = assignmentService;
        this.screeningService = screeningService;
        this.technicalOperatorService = technicalOperatorService;
        this.supportStaffService = supportStaffService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("assignments", assignmentService.findAll());
        return "staffassignment/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("assignment", new StaffAssignment());
        return "staffassignment/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("assignment") StaffAssignment a,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            return "staffassignment/form";
        }

        Screening screening = screeningService.findById(a.getScreeningId())
                .orElse(null);
        if (screening == null) {
            bindingResult.rejectValue("screeningId", "screening.notFound", "Screening not found");
        }

        Staff staff = technicalOperatorService.findById(a.getStaffId())
                .map(t -> (Staff) t)
                .orElseGet(() ->
                        supportStaffService.findById(a.getStaffId())
                                .map(s -> (Staff) s)
                                .orElse(null)
                );
        if (staff == null) {
            bindingResult.rejectValue("staffId", "staff.notFound", "Staff not found (TO/SST)");
        }

        if (bindingResult.hasErrors()) {
            return "staffassignment/form";
        }

        a.setScreening(screening);
        a.setStaff(staff);

        assignmentService.save(a);
        return "redirect:/assignments";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model, RedirectAttributes ra) {
        return assignmentService.findById(id)
                .map(a -> {
                    a.setScreeningId(a.getScreening().getId());
                    a.setStaffId(a.getStaff().getId());
                    model.addAttribute("assignment", a);
                    return "staffassignment/details";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Assignment not found.");
                    return "redirect:/assignments";
                });
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable String id, Model model, RedirectAttributes ra) {
        return assignmentService.findById(id)
                .map(a -> {
                    a.setScreeningId(a.getScreening().getId());
                    a.setStaffId(a.getStaff().getId());
                    model.addAttribute("assignment", a);
                    return "staffassignment/edit";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("globalError", "Assignment not found.");
                    return "redirect:/assignments";
                });
    }


    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @Valid @ModelAttribute("assignment") StaffAssignment a,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            return "staffassignment/edit";
        }

        Screening screening = screeningService.findById(a.getScreeningId())
                .orElse(null);
        if (screening == null) {
            bindingResult.rejectValue("screeningId", "screening.notFound", "Screening not found");
        }

        Staff staff = technicalOperatorService.findById(a.getStaffId())
                .map(t -> (Staff) t)
                .orElseGet(() ->
                        supportStaffService.findById(a.getStaffId())
                                .map(s -> (Staff) s)
                                .orElse(null)
                );
        if (staff == null) {
            bindingResult.rejectValue("staffId", "staff.notFound", "Staff not found (TO/SST)");
        }

        if (bindingResult.hasErrors()) {
            return "staffassignment/edit";
        }

        a.setId(id);
        a.setScreening(screening);
        a.setStaff(staff);

        assignmentService.save(a);
        return "redirect:/assignments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            assignmentService.deleteById(id);
            ra.addFlashAttribute("successMessage", "Assignment deleted.");
        } catch (Exception e) {
            ra.addFlashAttribute("globalError", "Could not delete assignment.");
        }
        return "redirect:/assignments";
    }
}
