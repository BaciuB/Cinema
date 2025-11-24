package com.example.proiectul.Cinema.controller;

import com.example.proiectul.Cinema.model.Customer;
import com.example.proiectul.Cinema.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("customers", service.findAll());
        return "customer/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        Customer c = new Customer();
        // protecție null pentru Thymeleaf
        if (c.getTickets() == null) c.setTickets(new ArrayList<>());
        model.addAttribute("customer", c);
        return "customer/form";
    }

    @PostMapping
    public String create(@ModelAttribute Customer customer) {
        if (customer.getTickets() == null) customer.setTickets(new ArrayList<>());
        service.save(customer);
        return "redirect:/customers";
    }

    @GetMapping("/{id}")
    public String details(@PathVariable String id, Model model) {
        Customer customer = service.findCustomerWithTickets(id);
        model.addAttribute("customer", customer);
        return "customer/details";
    }


    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable String id, Model model) {
        Customer c = service.findById(id).orElseThrow();
        if (c.getTickets() == null) c.setTickets(new ArrayList<>());
        model.addAttribute("customer", c);
        return "customer/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Customer customer) {
        customer.setId(id);
        if (customer.getTickets() == null) customer.setTickets(new ArrayList<>());
        service.save(customer);
        return "redirect:/customers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        service.deleteById(id);
        return "redirect:/customers";
    }
}
