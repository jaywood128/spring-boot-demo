package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.SpringBootDemoApplication;
import com.johnathon.podcast_blast.repository.UserRepository;
import com.johnathon.podcast_blast.security.services.UserDetailsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    private UserRepository userRepository;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public HomeController(UserRepository userRepository, UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userRepository = userRepository;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

//    @RequestMapping("/")
//    public String homePage() {
//        return "home.jsp";
//    }

    // use react for GET /login

//    @GetMapping("/login")
//    public String loginPage() {
//        return "login.jsp";
//    }

    @RequestMapping("/logout-succes")
    public String logoutPage() {
        return "login.jsp";
    }
}
