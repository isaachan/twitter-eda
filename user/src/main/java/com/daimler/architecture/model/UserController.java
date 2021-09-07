package com.daimler.architecture.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @PostMapping("/users/")
    public void newUser(@RequestBody User u) {

    }

    @GetMapping("/users/{id}")
    public User findBy(@PathVariable("id") Long id) {
        return null;
    }
}
