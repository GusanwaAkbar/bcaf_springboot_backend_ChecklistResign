package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.User;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.repository.UserDetailsRepository;
import com.hrs.checklist_resign.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsService userDetailsService;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {

        System.out.println("--------- bintang -----------");
        System.out.println(user.getPassword());
        System.out.println("---------------------");
        //Passsword Encoder
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Save User
        userRepository.save(user);



    }
}

/////////


