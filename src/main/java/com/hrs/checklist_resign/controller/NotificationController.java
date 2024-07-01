package com.hrs.checklist_resign.controller;


import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.Model.Notification;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> getNotificationByUser()
    {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipUser = authentication.getName();

        List<Notification> notification = notificationService.findByRecepient(nipUser);
        if (!notification.isEmpty()) {
            ApiResponse<List<Notification>> response = new ApiResponse<>(notification, true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<List<Notification>> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "Notification not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }
}
