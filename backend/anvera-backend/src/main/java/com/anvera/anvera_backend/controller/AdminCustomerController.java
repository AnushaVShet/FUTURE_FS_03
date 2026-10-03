package com.anvera.anvera_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.anvera.anvera_backend.entity.Customer;
import com.anvera.anvera_backend.repository.CustomerRepository;

@RestController
@RequestMapping("/api/admin/protected/customers")
public class AdminCustomerController {

    private final CustomerRepository customerRepository;

    public AdminCustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Get all customers
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}