package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.payload.request.LoginForm;
import com.johnathon.podcast_blast.payload.request.SignupForm;
import com.johnathon.podcast_blast.model.User;
import com.johnathon.podcast_blast.repository.UserRepository;
import com.johnathon.podcast_blast.security.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/auth")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepository;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public UserController(UserRepository userRepository, UserDetailsServiceImpl userDetailsServiceImpl){
        super();
        this.userRepository = userRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @PostMapping(value="/sign-up")
    public String createNewUser(@RequestBody SignupForm signupForm ){
        System.out.println(signupForm);
        User newUser = new User(signupForm.getName(), signupForm.getUsername(), signupForm.getEmail());
        newUser.setPassword(signupForm.getPassword());
        System.out.println("password has been set");
        System.out.println("Random change");

        if(newUser.getPassword() != null){
            userDetailsServiceImpl.signUpUser(newUser);
        }
        return newUser.getName() + " was signed up with the email of " + newUser.getEmail();
    }

    @PostMapping("/sign-in")
    public HttpStatus loginForm(@RequestBody LoginForm loginForm)  {
        UserDetailsServiceImpl authenticateUser = new UserDetailsServiceImpl();
        UserDetails userDetails = authenticateUser.loadUserByUsername(loginForm.getUserName());

        if (authenticateUser != null && userDetails.getPassword() == loginForm.getPassword()) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.UNAUTHORIZED;
        }
    }

    @GetMapping(path= "/users", consumes = "application/json", produces = "application/json")
    public Collection<User> getUsers(){
        return userRepository.findAll();
    }
}
