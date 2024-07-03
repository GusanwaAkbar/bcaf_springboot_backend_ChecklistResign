package com.hrs.checklist_resign.controller;


import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.User;
import com.hrs.checklist_resign.dto.UserResponseDTO;
import com.hrs.checklist_resign.repository.UserRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.AdminService;
import com.hrs.checklist_resign.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import  com.hrs.checklist_resign.dto.RoleUpdateRequest;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/non-user-authorities")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getUsersWithNonUserAuthorities() {
        List<UserResponseDTO> listUserDTO = adminService.findUsersWithRolesNotContainingV2("USER");

        if (!listUserDTO.isEmpty()) {
            ApiResponse<List<UserResponseDTO>> response = new ApiResponse<>(listUserDTO, true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<List<UserResponseDTO>> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalAtasan not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}
