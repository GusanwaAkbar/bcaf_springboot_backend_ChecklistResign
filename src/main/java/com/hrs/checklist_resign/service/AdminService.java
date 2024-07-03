package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.User;
import com.hrs.checklist_resign.dto.UserResponseDTO;
import com.hrs.checklist_resign.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    public List<User>  findUsersWithRolesNotContaining(String role)
    {

       List<User> listUser = userRepository.findUsersWithRolesNotContaining("USER");

       return listUser;
    }

    public List<UserResponseDTO> findUsersWithRolesNotContainingV2(String role)
    {

        List<UserResponseDTO> listUser = userRepository.findUsersWithRolesNotContainingV2("USER");

        return listUser;
    }

}