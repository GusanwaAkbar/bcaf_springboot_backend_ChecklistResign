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
        Optional<User> existingUserOptional = userRepository.findByUsername(user.getUsername());

        if (existingUserOptional.isPresent()) {
            // Update existing user
            User existingUser = existingUserOptional.get();

            // Only encode the password if it's being updated
            if (!existingUser.getPassword().equals(user.getPassword())) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            // Update other fields
            existingUser.setUsername(user.getUsername());
            existingUser.setRoles(user.getRoles());
            // Update other fields as necessary

            return userRepository.save(existingUser);
        } else {
            // New user
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
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


