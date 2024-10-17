package com.customerApp.demo.repository;

import com.customerApp.demo.model.Customer;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {

    private List <Customer> customers = new ArrayList<>();

    public CustomerRepository() {
        customers.add(new Customer(1L, "John Doe", "john.doe@abc.com"));
        customers.add(new Customer(2L, "Jane Smith", "jane.smith@example.com"));
        customers.add(new Customer(3L, "Odilia Fintoph","ofintoph0@miibeian.gov.cn"));
        customers.add(new Customer(4L, "Milicent Rouby", "mrouby1@ebay.co.uk"));
        customers.add(new Customer(5L, "Ulric Eldritt", "ueldritt2@pinterest.com"));
        customers.add(new Customer(6L, "Elsbeth Ovington", "eovington8@cdc.gov"));
        customers.add(new Customer(7L, "Inna Letertre", "iletertre9@meetup.com"));
    }

    public List<Customer> findAll() {
        return customers;
    }

    public Optional<Customer> findById(Long id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

    public Customer save(Customer customer) {
        customers.removeIf(c -> c.getId().equals(customer.getId()));
        customers.add(customer);
        return customer;
    }

    public boolean deleteById(Long id) {
        return customers.removeIf(customer -> customer.getId().equals(id));
    }
}