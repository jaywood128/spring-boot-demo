package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.payload.request.LoginForm;
import com.johnathon.podcast_blast.payload.request.SignupForm;
import com.johnathon.podcast_blast.model.User;
import com.johnathon.podcast_blast.repository.UserRepository;
import com.johnathon.podcast_blast.security.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/auth")
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    private UserRepository userRepository;

    private UserDetailsServiceImpl userDetailsServiceImpl;

    public AuthController(){

    }

    public AuthController(UserRepository userRepository, UserDetailsServiceImpl userDetailsServiceImpl){
        super();
        this.userRepository = userRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

//    @Bean
//    public UserDetailsServiceImpl userDetailsServiceImpl(){
//        UserDetailsServiceImpl userDetailsServiceImpl = new UserDetailsServiceImpl();
//        return userDetailsServiceImpl;
//    }

    @PostMapping(value="/sign-up")
    public String createNewUser(@RequestBody SignupForm signupForm ){
        System.out.println(signupForm);
        User newUser = new User(signupForm.getName(), signupForm.getUsername(), signupForm.getEmail());

        if(newUser.getPassword() != null){
            userDetailsServiceImpl.signUpUser(newUser);
        }
        return newUser.getName() + " was signed up with the email of " + newUser.getEmail();
    }

    @PostMapping("/sign-in")
    public HttpStatus loginForm(@RequestBody LoginForm loginForm)  {
        UserDetailsServiceImpl authenticateUser = new UserDetailsServiceImpl();
        UserDetails userDetails = authenticateUser.loadUserByUsername(loginForm.getUserName());

        if (userDetails.getPassword().equals(loginForm.getPassword())) {
            return HttpStatus.OK;
        } else {
            return HttpStatus.UNAUTHORIZED;
        }
    }
}
