package com.johnathon.podcast_blast.security.services;

import com.johnathon.podcast_blast.model.User;
import com.johnathon.podcast_blast.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;


public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserDetailsImpl userDetailsImpl;

    @Override
    @Transactional
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        user.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user.get());
    }

//    @Override
//    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
//        System.out.println("Getting access details from employee dao !!");
//
//        User foundUser = userRepository.findByUsername(userName);
//        if(foundUser == null){
//            throw new UsernameNotFoundException("User 404");
//        }
//        return new UserDetailsImpl(foundUser);
//    }

    public User signUpUser(User user) throws NullPointerException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("password has been set");
        return userRepository.save(user);
    }
}
