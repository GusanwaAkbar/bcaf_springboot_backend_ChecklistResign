//package com.hrs.checklist_resign.controller;
//
//
//import com.hrs.checklist_resign.Model.User;
//import com.hrs.checklist_resign.response.ApiResponse;
//import com.hrs.checklist_resign.service.AdminService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import  com.hrs.checklist_resign.dto.RoleUpdateRequest;
//
//import jakarta.validation.Valid;
//
//@RestController
//@RequestMapping("/api/admin")
//public class AdminController {
//
//    @Autowired
//    private AdminService adminService;
//
//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/users/{username}/roles")
//    public ResponseEntity<ApiResponse<User>> updateUserRoles(@PathVariable String username, @Valid @RequestBody RoleUpdateRequest roleUpdateRequest) {
//        ApiResponse<User> response = adminService.updateUserRoles(username, roleUpdateRequest.getRoles());
//        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
//    }
//}
