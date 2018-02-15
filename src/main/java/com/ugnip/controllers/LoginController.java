package com.ugnip.controllers;

import com.ugnip.models.employee.Employee;
import com.ugnip.models.user.User;
import com.ugnip.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    //check login credentials
    @GetMapping
    public Employee checkUser(@RequestParam String email, @RequestParam String password) {
        Employee user = userRepository.findByEmailAndPassword(email, password);

        if(user == null) {
            return null;
        } else {
            return user;
        }
    }
}
