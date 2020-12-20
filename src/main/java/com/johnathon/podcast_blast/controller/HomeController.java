package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.repository.UserRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.FailedLoginException;

@Controller
public class HomeController {
    private UserRepository userRepository;

    private final MyUserDetailsService myUserDetailsService;

    public HomeController(UserRepository userRepository, MyUserDetailsService myUserDetailsService) {
        this.userRepository = userRepository;
        this.myUserDetailsService = myUserDetailsService;
    }

    @RequestMapping("/")
    public String homePage() {
        return "home.jsp";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login.jsp";
    }

//    @PostMapping("/login")
//    public String loginForm(@RequestParam userName, @RequestParam password)  {
//        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);
//        if(userDetails.isEnabled() && userDetails.getPassword() == password){
//            return "home.jsp";
//        }
//        return "login.jsp";
//    }

    @RequestMapping("/logout-succes")
    public String logoutPage() {
        return "login.jsp";
    }
}
