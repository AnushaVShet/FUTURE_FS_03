package com.anvera.anvera_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anvera.anvera_backend.entity.Customer;
import com.anvera.anvera_backend.repository.CustomerRepository;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @PostMapping
    public Customer addCustomer(@RequestBody Customer customer) {
        return customerRepository.save(customer);
    }
    @PutMapping("/{id}")
public Customer updateCustomer(@PathVariable Integer id, @RequestBody Customer customer) {

    customer.setId(id);

    return customerRepository.save(customer);
}

@DeleteMapping("/{id}")
public String deleteCustomer(@PathVariable Integer id) {

    customerRepository.deleteById(id);

    return "Customer deleted successfully";
}
}