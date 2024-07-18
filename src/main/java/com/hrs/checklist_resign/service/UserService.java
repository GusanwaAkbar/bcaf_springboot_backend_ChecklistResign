package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.User;
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

    public User saveUser(User user) {

        System.out.println("--------- bintang -----------");
        System.out.println(user.getPassword());
        System.out.println("---------------------");
        //Passsword Encoder
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Save User
        userRepository.save(user);


        return user;
    }



    public boolean changeUserRole(String username, String newRole) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setRoles(newRole);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }



}

/////////


