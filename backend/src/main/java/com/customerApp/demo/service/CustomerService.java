package com.customerApp.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import com.customerApp.demo.model.Customer;
import com.customerApp.demo.repository.CustomerRepository;


@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;

    }
    // Getting customer by id
    public Optional <Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Getting all customers
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    //Create a new customer
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    //Updating the exsisting customer
    public Optional <Customer> updateCustomer(Long id, Customer customerDetails) {
        Optional <Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            customer.setName(customerDetails.getName());
            customer.setEmail(customerDetails.getEmail());
            customerRepository.save(customer);
            return Optional.of(customer);
        } else {
            return Optional.empty();
        }
    };

    //Delete customer
    public boolean deleteCustomer(Long id) {
        Optional <Customer> customerOpt = customerRepository.findById(id);
        if (customerOpt.isPresent()) {
            customerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}