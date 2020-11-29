package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.model.User;
import com.johnathon.podcast_blast.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private UserRepository userRepository;

    public UserController(UserRepository userRepository){
        super();
        this.userRepository = userRepository;
    }
    @RequestMapping(value="/signup", method= RequestMethod.GET)
    public String signupUser(){
        return "signup.jsp";
    }


//    @PostMapping(path = "/users", consumes="application/json", produces = "application/json")
//    public String createNewUser(@RequestParam String name, @RequestParam String username, @RequestParam String email,  @RequestParam String password){
//        User newUser = new User(name, username, email, password);
//        if(newUser != null){
//            return userRepository.save(newUser);
//        } else{
//
//        }
//
//    }

    @GetMapping(path= "/users", consumes = "application/json", produces = "application/json")
    public Collection<User> getUsers(){
        return userRepository.findAll();
    }
}
