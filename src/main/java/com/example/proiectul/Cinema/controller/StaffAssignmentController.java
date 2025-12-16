package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.StaffAssignment;
import com.example.proiectul.Cinema.service.ScreeningService;
import com.example.proiectul.Cinema.service.StaffAssignmentService;
import com.example.proiectul.Cinema.service.SupportStaffService;
import com.example.proiectul.Cinema.service.TechnicalOperatorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public String index(@RequestParam(required = false) String staffId,
                        @RequestParam(required = false) String screeningId,
                        @RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "asc") String dir,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "0") int page,
                        Model model) {

        Sort sort = "desc".equalsIgnoreCase(dir) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);

        var assignmentsPage = assignmentService.findWithFilters(staffId, screeningId, pageable);

        model.addAttribute("assignmentsPage", assignmentsPage);
        model.addAttribute("assignments", assignmentsPage.getContent());

        model.addAttribute("staffId", staffId);
        model.addAttribute("screeningId", screeningId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("dir", dir);
        model.addAttribute("size", size);

        return "staffassignment/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("assignment", new StaffAssignment());
        return "staffassignment/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("assignment") StaffAssignment a,
                         BindingResult br,
                         Model model,
                         RedirectAttributes ra) {

        if (a.getScreeningId() == null || a.getScreeningId().trim().isEmpty()) {
            br.rejectValue("screeningId", "screeningId.required", "Screening ID is required");
        }
        if (a.getStaffId() == null || a.getStaffId().trim().isEmpty()) {
            br.rejectValue("staffId", "staffId.required", "Staff ID is required");
        }

        if (br.hasErrors()) return "staffassignment/form";

        var screening = screeningService.findById(a.getScreeningId().trim()).orElse(null);
        if (screening == null) {
            br.rejectValue("screeningId", "screening.notFound", "Screening not found");
            return "staffassignment/form";
        }

        String staffId = a.getStaffId().trim();
        if (!(staffId.startsWith("TO") || staffId.startsWith("SST"))) {
            br.rejectValue("staffId", "staff.invalidPrefix", "Staff ID must start with TO or SST");
            return "staffassignment/form";
        }

        a.setTechnicalOperator(null);
        a.setSupportStaff(null);

        if (staffId.startsWith("TO")) {
            var toOpt = technicalOperatorService.findById(staffId);
            if (toOpt.isEmpty()) {
                br.rejectValue("staffId", "staff.notFound", "Technical Operator not found");
                return "staffassignment/form";
            }
            a.setTechnicalOperator(toOpt.get());
        } else {
            var ssOpt = supportStaffService.findById(staffId);
            if (ssOpt.isEmpty()) {
                br.rejectValue("staffId", "staff.notFound", "Support Staff not found");
                return "staffassignment/form";
            }
            a.setSupportStaff(ssOpt.get());
        }

        if (assignmentService.staffAlreadyAssignedForScreening(screening.getId(), staffId)) {
            br.rejectValue("staffId", "staff.duplicate", "This staff member is already assigned to this screening");
            return "staffassignment/form";
        }

        a.setScreening(screening);

        assignmentService.save(a);
        ra.addFlashAttribute("successMessage", "Assignment created successfully.");
        return "redirect:/assignments";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model, RedirectAttributes ra) {
        return assignmentService.findById(id)
                .map(a -> {
                    a.setScreeningId(a.getScreening().getId());
                    a.setStaffId(a.getAssignedStaffId());
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
                    a.setStaffId(a.getAssignedStaffId());
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
                         BindingResult br,
                         Model model,
                         RedirectAttributes ra) {

        if (a.getScreeningId() == null || a.getScreeningId().trim().isEmpty()) {
            br.rejectValue("screeningId", "screeningId.required", "Screening ID is required");
        }
        if (a.getStaffId() == null || a.getStaffId().trim().isEmpty()) {
            br.rejectValue("staffId", "staffId.required", "Staff ID is required");
        }

        if (br.hasErrors()) return "staffassignment/edit";

        var screening = screeningService.findById(a.getScreeningId().trim()).orElse(null);
        if (screening == null) {
            br.rejectValue("screeningId", "screening.notFound", "Screening not found");
            return "staffassignment/edit";
        }

        String staffId = a.getStaffId().trim();
        if (!(staffId.startsWith("TO") || staffId.startsWith("SST"))) {
            br.rejectValue("staffId", "staff.invalidPrefix", "Staff ID must start with TO or SST");
            return "staffassignment/edit";
        }

        a.setTechnicalOperator(null);
        a.setSupportStaff(null);

        if (staffId.startsWith("TO")) {
            var toOpt = technicalOperatorService.findById(staffId);
            if (toOpt.isEmpty()) {
                br.rejectValue("staffId", "staff.notFound", "Technical Operator not found");
                return "staffassignment/edit";
            }
            a.setTechnicalOperator(toOpt.get());
        } else {
            var ssOpt = supportStaffService.findById(staffId);
            if (ssOpt.isEmpty()) {
                br.rejectValue("staffId", "staff.notFound", "Support Staff not found");
                return "staffassignment/edit";
            }
            a.setSupportStaff(ssOpt.get());
        }

        if (assignmentService.staffAlreadyAssignedForScreeningExcept(id, screening.getId(), staffId)) {
            br.rejectValue("staffId", "staff.duplicate", "This staff member is already assigned to this screening");
            return "staffassignment/edit";
        }

        a.setId(id);
        a.setScreening(screening);

        assignmentService.save(a);
        ra.addFlashAttribute("successMessage", "Assignment updated successfully.");
        return "redirect:/assignments";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            assignmentService.deleteById(id);
            ra.addFlashAttribute("successMessage", "Assignment deleted successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("globalError", "Could not delete assignment.");
        }
        return "redirect:/assignments";
    }
}
