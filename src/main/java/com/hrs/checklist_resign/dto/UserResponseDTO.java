package com.hrs.checklist_resign.dto;

import com.hrs.checklist_resign.Model.UserDetail;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserResponseDTO {
    private String username;
    private UserDetail userDetails;
    private List<String> authorities;
    private List<String> rolesList;

    // Constructors, Getters, and Setters
    public UserResponseDTO(String username, UserDetail userDetails, String roles) {
        this.username = username;
        this.userDetails = userDetails;
        this.rolesList = Arrays.asList(roles.split(","));
        this.authorities = this.rolesList.stream()
                .map(role -> "ROLE_" + role.trim())
                .collect(Collectors.toList());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserDetail getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetail userDetails) {
        this.userDetails = userDetails;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    public List<String> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<String> rolesList) {
        this.rolesList = rolesList;
    }
}
