package com.cfs.authservice.service;

import com.cfs.authservice.entity.User;
import com.cfs.authservice.repo.UserRepository;
import com.cfs.authservice.util.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private UserRepository userRepository;

    public String saveUser(User user){
        userRepository.save(user);
        return "User saved successfully";
    }

    public String login(User user){
        User existing = userRepository.findByUsername(user.getUsername())
                .orElseThrow();
        if(!existing.getPassword().equals(user.getPassword())){
            throw new RuntimeException("Invalid credentials");
        }
        return jwtUtility.generateToken(user.getUsername());
    }

}
