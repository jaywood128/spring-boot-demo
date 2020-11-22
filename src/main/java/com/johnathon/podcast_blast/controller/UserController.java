package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.model.User;
import com.johnathon.podcast_blast.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        super();
        this.userRepository = userRepository;
    }
//    @PostMapping(path = "/users", consumes="application/json", produces = "application/json")
//    public String addNewUser(@RequestParam String name, @RequestParam String username, @RequestParam String email,  @RequestParam String password){
//        User newUser = new User(name, username, email, password);
//        return userRepository.save(newUser);
//    }

//    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
//    public User login(@RequestParam String username, @RequestParam String password){
//        // how to authenticate user password?
////        return userRepository.
//    }
    @GetMapping(path= "/users", consumes = "application/json", produces = "application/json")
    public Collection<User> getUsers(){
        return userRepository.findAll();
    }
}
