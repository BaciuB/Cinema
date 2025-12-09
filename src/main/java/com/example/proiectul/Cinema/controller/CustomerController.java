package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Customer;
import com.example.proiectul.Cinema.service.CustomerService;
import jakarta.validation.Valid;
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

    // LISTĂ
    @GetMapping
    public String index(Model model,
                        @ModelAttribute("errorMessage") String errorMessage,
                        @ModelAttribute("successMessage") String successMessage) {

        model.addAttribute("customers", service.findAll());

        if (errorMessage != null && !errorMessage.isBlank()) {
            model.addAttribute("errorMessage", errorMessage);
        }
        if (successMessage != null && !successMessage.isBlank()) {
            model.addAttribute("successMessage", successMessage);
        }

        return "customer/index";
    }

    // FORM CREATE
    @GetMapping("/new")
    public String newForm(Model model) {
        Customer c = new Customer();
        if (c.getTickets() == null) c.setTickets(new ArrayList<>());
        model.addAttribute("customer", c);
        return "customer/form";
    }

    // CREATE cu Bean Validation + Business Validation
    @PostMapping
    public String create(@ModelAttribute("customer") @Valid Customer customer,
                         BindingResult bindingResult,
                         Model model) {

        if (customer.getTickets() == null) customer.setTickets(new ArrayList<>());

        // BUSINESS: email unic (doar dacă email-ul nu e deja invalid)
        if (!bindingResult.hasFieldErrors("email") && customer.getEmail() != null) {
            service.findByEmail(customer.getEmail()).ifPresent(existing ->
                    bindingResult.rejectValue(
                            "email",
                            "duplicateEmail",
                            "A customer with this email already exists."
                    )
            );
        }

        // dacă există ORICE eroare -> rămânem în formular
        if (bindingResult.hasErrors()) {
            return "customer/form";
        }

        service.save(customer);
        return "redirect:/customers";
    }

    // DETALII
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
        if (errorMessage != null && !errorMessage.isBlank()) {
            model.addAttribute("errorMessage", errorMessage);
        }

        return "customer/details";
    }

    // FORM EDIT
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

    // UPDATE cu Bean Validation + Business Validation
    @PostMapping("/{id}")
    public String update(@PathVariable String id,
                         @ModelAttribute("customer") @Valid Customer customer,
                         BindingResult bindingResult,
                         Model model) {

        customer.setId(id);
        if (customer.getTickets() == null) customer.setTickets(new ArrayList<>());

        // BUSINESS: email unic și la update
        if (!bindingResult.hasFieldErrors("email") && customer.getEmail() != null) {
            service.findByEmail(customer.getEmail()).ifPresent(existing -> {
                if (!existing.getId().equals(id)) {
                    bindingResult.rejectValue(
                            "email",
                            "duplicateEmail",
                            "Another customer with this email already exists."
                    );
                }
            });
        }

        if (bindingResult.hasErrors()) {
            return "customer/edit";
        }

        service.save(customer);
        return "redirect:/customers";
    }

    // DELETE cu business rule: nu ștergem dacă are bilete
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
        redirectAttributes.addFlashAttribute("successMessage",
                "Customer deleted successfully.");
        return "redirect:/customers";
    }
}
