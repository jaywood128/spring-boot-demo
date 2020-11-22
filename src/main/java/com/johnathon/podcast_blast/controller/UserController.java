package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.model.User;
import com.johnathon.podcast_blast.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        super();
        this.userRepository = userRepository;
    }
    @PostMapping(path = "/users", consumes="application/json", produces = "application/json")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }
//    @PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
//    public User login(@RequestBody User user){
//        // how to authenticate user password?
////        return userRepository.
//    }
    @GetMapping(path= "/users", consumes = "application/json", produces = "application/json")
    public Collection<User> getUsers(){
        return userRepository.findAll();
    }
}
