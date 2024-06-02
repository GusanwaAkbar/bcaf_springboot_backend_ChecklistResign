package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public void saveUserDetails(UserDetail userDetails) {
        userDetailsRepository.save(userDetails);
    }

    public UserDetail findByUsername(String username) {
        return userDetailsRepository.findByUserUsername(username);
    }
}
