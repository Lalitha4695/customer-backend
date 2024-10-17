package com.customerApp.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customerApp.demo.model.Customer;
import com.customerApp.demo.service.CustomerService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/api/customers")

public class CustomerController {

    @Autowired
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Getting all customers
    @GetMapping
    public ResponseEntity <Iterable<Customer>> getAllCustomers(HttpServletRequest request) {
        String username = (String) request.getAttribute("username");
        System.out.println(username);

        Iterable<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    // Getting customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional <Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Creating a new customer
    @PostMapping
    public ResponseEntity <Customer> createCustomer (@RequestBody Customer customer) {
        Customer savedCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    // Updating an existing customer
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customerDetails) {
        Optional <Customer> customer = customerService.updateCustomer(id, customerDetails);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deleting a customer
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        boolean isDeleted = customerService.deleteCustomer(id);
        if (isDeleted) {
            return ResponseEntity.ok("Customer Deleted Successfully");
        } else {
            return ResponseEntity.notFound().build();

        }
    }
}