package com.johnathon.podcast_blast.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String homePage(){
        return "home.jsp";
    }
    @RequestMapping("/login")
    public String loginPage(){
        return "login.jsp";
    }
    @RequestMapping("/logout-succes")
    public String logoutPage(){
        return "logout.jsp";
    }
}
