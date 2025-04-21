package com.example.vehicle_parking.beckend.Controller;

import com.example.vehicle_parking.beckend.Model.User;
import com.example.vehicle_parking.beckend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/park")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/user/{userID}")
    public User findById(@PathVariable("userID") int id) {
        return userService.findById(id);
    }

    @GetMapping("user/{FirstName}")
    public List<User> findByFirstName(@PathVariable("FirstName") String name) {
        return userService.findByFirstname(name);
    }

    @PostMapping("/user")
    public void save(@RequestBody User user) {
        userService.save(user);
    }

    @DeleteMapping("/user")
    public void delete(User user) {
        userService.delete(user);
    }

    @DeleteMapping("user/{userID}")
    public void delete(@PathVariable("userID") int id) {
        userService.delete(id);
    }

    @PutMapping("/user")
    public void update(User user) {
        userService.updateUser(user);
    }
}
