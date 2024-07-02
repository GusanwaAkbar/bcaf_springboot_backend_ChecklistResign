package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.dto.PostChangeRoleDTO;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserDetailController {

    @Autowired
    private UserDetailsService userDetailService;

    // Existing getUserDetails method
    @GetMapping("/user-detail")
    public ResponseEntity<ApiResponse<UserDetail>> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<UserDetail> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName();
        Optional<UserDetail> userDetailOpt = Optional.ofNullable(userDetailService.findByUsername(username));

        if (!userDetailOpt.isPresent()) {
            ApiResponse<UserDetail> response = new ApiResponse<>(true, "User Detail Not Found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        UserDetail userDetail = userDetailOpt.get();
        ApiResponse<UserDetail> response = new ApiResponse<>(userDetail, true, "User details retrieved successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // New updateUserDetails method
    @PutMapping("/user-detail/complete-update")
    public ResponseEntity<ApiResponse<UserDetail>> updateUserDetails(@RequestBody UserDetail userDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<UserDetail> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName();
        Optional<UserDetail> userDetailOpt = Optional.ofNullable(userDetailService.findByUsername(username));

        if (!userDetailOpt.isPresent()) {
            ApiResponse<UserDetail> response = new ApiResponse<>(true, "User Detail Not Found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        UserDetail existingUserDetail = userDetailOpt.get();
        userDetails.setId(existingUserDetail.getId());
        userDetails.setUser(existingUserDetail.getUser()); // Keep the existing User object

        UserDetail updatedUserDetail = userDetailService.updateUserDetails(userDetails);
        ApiResponse<UserDetail> response = new ApiResponse<>(updatedUserDetail, true, "User details updated successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    //partial update
    @PutMapping("/user-detail")
    public ResponseEntity<ApiResponse<UserDetail>> updateUserDetails(@RequestBody Map<String, Object> updates) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<UserDetail> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName();
        Optional<UserDetail> userDetailOpt = Optional.ofNullable(userDetailService.findByUsername(username));

        if (!userDetailOpt.isPresent()) {
            ApiResponse<UserDetail> response = new ApiResponse<>(true, "User Detail Not Found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        UserDetail existingUserDetail = userDetailOpt.get();

        // Update only specified fields
        updates.forEach((key, value) -> {
            switch (key) {
                case "email":
                    existingUserDetail.setEmail((String) value);
                    break;
                case "nomerWA":
                    existingUserDetail.setNomerWA((String) value);
                    break;
                // Add cases for other fields if needed
            }
        });

        UserDetail updatedUserDetail = userDetailService.updateUserDetails(existingUserDetail);
        ApiResponse<UserDetail> response = new ApiResponse<>(updatedUserDetail, true, "User details updated successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
