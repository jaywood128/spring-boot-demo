package com.johnathon.podcast_blast.security.services;

import com.johnathon.podcast_blast.model.User;
import com.johnathon.podcast_blast.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDetailsImpl userDetailsImpl;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println("Getting access details from employee dao !!");

        User foundUser = userRepository.findByUsername(userName);
        if(foundUser == null){
            throw new UsernameNotFoundException("User 404");
        }
        return new UserDetailsImpl(foundUser);
    }
    public User signUpUser(User user) {
        UserDetailsImpl userDetailsImpl = new UserDetailsImpl();
        System.out.println("Inside signup user in MyUserDetailsService");
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }
}
