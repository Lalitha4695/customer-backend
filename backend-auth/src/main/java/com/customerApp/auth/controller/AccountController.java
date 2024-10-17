package com.customerApp.auth.controller;

import java.util.HashMap;
import java.util.Map;
import com.customerApp.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.customerApp.auth.repository.UserRepository;
import com.customerApp.auth.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Autowired
    private UserRepository userRepository;

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<Map <String, String>> registerUser(@RequestBody User user) {
        // checking if user already exsits
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "User already exsists");
            return ResponseEntity.badRequest().body(response);
        }
        // Register new
        userRepository.addUser(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        System.out.println(user.getUsername() + "password is " + user.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/token")
    public ResponseEntity<Map <String, String>> getToken(@RequestBody User credentials) {
        String username = credentials.getUsername();
        String password = credentials.getPassword();

        // Validating credentials
        return userRepository.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> {
                    String token = accountService.generateToken(username);
                    Map<String, String> response = new HashMap<>();
                    response.put("token", token);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("error", "Invalid credentials");
                    return ResponseEntity.badRequest().body(response);
                });
    }
}
