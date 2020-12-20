package com.johnathon.podcast_blast.controller;

import com.johnathon.podcast_blast.model.User;
import com.johnathon.podcast_blast.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private MyUserDetails myUserDetails;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("Getting access details from employee dao !!");

        User foundUser = userRepository.findByUsername(userName);
        if(foundUser == null){
            throw new UsernameNotFoundException("User 404");
        }
        return new MyUserDetails(foundUser);
    }
    public User signUpUser(User user) {
        MyUserDetails myUserDetails = new MyUserDetails();
        System.out.println("Inside signup user in MyUserDetailsService");
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }
//    public boolean loginUser(String username, String password){
//        User loginUser = userRepository.findByUsername(username);
//        if(loginUser != null && myUserDetails.getPassword().equals(password)){
//
//
//        }
//    }
}
