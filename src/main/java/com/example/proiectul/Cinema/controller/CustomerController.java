// src/main/java/com/example/proiectul/Cinema/controller/CustomerController.java
package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Customer;
import com.example.proiectul.Cinema.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(required = false) String name,
                        @RequestParam(required = false) String email,
                        @RequestParam(defaultValue = "id") String sortBy,
                        @RequestParam(defaultValue = "ASC") String dir,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(defaultValue = "0") int page,
                        @ModelAttribute("errorMessage") String errorMessage,
                        @ModelAttribute("successMessage") String successMessage) {

        if (size < 1) size = 10;
        if (page < 0) page = 0;

        Sort.Direction direction = "DESC".equalsIgnoreCase(dir) ? Sort.Direction.DESC : Sort.Direction.ASC;

        String sortProp = switch (sortBy) {
            case "name" -> "name";
            case "email" -> "email";
            default -> "id";
        };

        PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, sortProp));
        Page<Customer> customersPage = service.findWithFilters(name, email, pageable);

        model.addAttribute("customersPage", customersPage);
        model.addAttribute("customers", customersPage.getContent());

        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("dir", dir.toUpperCase());
        model.addAttribute("size", size);

        if (errorMessage != null && !errorMessage.isBlank()) model.addAttribute("errorMessage", errorMessage);
        if (successMessage != null && !successMessage.isBlank()) model.addAttribute("successMessage", successMessage);

        return "customer/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Customer c = new Customer();
        if (c.getTickets() == null) c.setTickets(new ArrayList<>());
        model.addAttribute("customer", c);
        return "customer/form";
    }

    @PostMapping
    public String create(@ModelAttribute("customer") @Valid Customer customer,
                         BindingResult bindingResult,
                         Model model) {

        if (customer.getTickets() == null) customer.setTickets(new ArrayList<>());

        if (!bindingResult.hasFieldErrors("email") && customer.getEmail() != null) {
            service.findByEmail(customer.getEmail()).ifPresent(existing ->
                    bindingResult.rejectValue("email", "duplicateEmail", "A customer with this email already exists.")
            );
        }

        if (bindingResult.hasErrors()) return "customer/form";

        service.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id,
                          Model model,
                          @ModelAttribute("errorMessage") String errorMessage,
                          RedirectAttributes redirectAttributes) {

        Customer customer = service.findCustomerWithTickets(id);
        if (customer == null) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Customer with id '" + id + "' was not found.");
            return "redirect:/customers";
        }

        model.addAttribute("customer", customer);
        if (errorMessage != null && !errorMessage.isBlank()) model.addAttribute("errorMessage", errorMessage);

        return "customer/details";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id,
                           Model model,
                           RedirectAttributes redirectAttributes) {

        Optional<Customer> opt = service.findById(id);
        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Customer with id '" + id + "' was not found.");
            return "redirect:/customers";
        }

        Customer c = opt.get();
        if (c.getTickets() == null) c.setTickets(new ArrayList<>());
        model.addAttribute("customer", c);
        return "customer/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("customer") @Valid Customer customer,
                         BindingResult bindingResult,
                         Model model) {

        customer.setId(id);
        if (customer.getTickets() == null) customer.setTickets(new ArrayList<>());

        if (!bindingResult.hasFieldErrors("email") && customer.getEmail() != null) {
            service.findByEmail(customer.getEmail()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    bindingResult.rejectValue("email", "duplicateEmail",
                            "Another customer with this email already exists.");
                }
            });
        }

        if (bindingResult.hasErrors()) return "customer/edit";

        service.save(customer);
        return "redirect:/customers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id,
                         RedirectAttributes redirectAttributes) {

        Customer customer = service.findCustomerWithTickets(id);
        if (customer == null) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Customer with id '" + id + "' was not found.");
            return "redirect:/customers";
        }

        if (customer.getTickets() != null && !customer.getTickets().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Customer cannot be deleted because there are tickets linked to them.");
            return "redirect:/customers/" + id;
        }

        service.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Customer deleted successfully.");
        return "redirect:/customers";
    }
}
