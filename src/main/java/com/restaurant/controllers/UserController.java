package com.restaurant.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.restaurant.entities.User;
import com.restaurant.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/all/add")
    public User createUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/all/modif/{id}")
    public User modifUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id); 
        return userService.modifUser(user);  
    }


    @GetMapping("/admin/get")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/all/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/all/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }
    
    @DeleteMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User with ID " + id + " has been deleted.";
    }
}
