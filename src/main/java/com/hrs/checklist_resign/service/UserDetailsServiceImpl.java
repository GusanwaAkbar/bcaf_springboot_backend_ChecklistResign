package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.User;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.Model.UserDetailsImpl;
import com.hrs.checklist_resign.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("=========================");
        System.out.println(username);

        User user = userRepository.findById(Long.valueOf(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        UserDetail userDetail = user.getUserDetails();
        return new UserDetailsImpl(user.getUsername(), user.getPassword(), user.getAuthorities(), userDetail);
    }
}