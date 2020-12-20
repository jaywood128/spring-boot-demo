package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.model.User;
import com.johnathon.podcast_blast.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepository;

    private final MyUserDetailsService myUserDetailsService;

    public UserController(UserRepository userRepository, MyUserDetailsService myUserDetailsService){
        super();
        this.userRepository = userRepository;
        this.myUserDetailsService = myUserDetailsService;
    }
    @GetMapping(value="/signup")
    public String signupUser(){
        return "signup.jsp";
    }

    @PostMapping(value="/signup")
    public String createNewUser(@RequestParam String name, @RequestParam String username, @RequestParam String email, @RequestParam String password ){
        User newUser = new User(name, username, email);
        newUser.setPassword(password);
        System.out.println("password has been set");

        if(newUser.getPassword() != null){
            myUserDetailsService.signUpUser(newUser);
        }
        return newUser.getName() + " was signed up with the email of " + newUser.getEmail();
    }

    @GetMapping(path= "/users", consumes = "application/json", produces = "application/json")
    public Collection<User> getUsers(){
        return userRepository.findAll();
    }
}
