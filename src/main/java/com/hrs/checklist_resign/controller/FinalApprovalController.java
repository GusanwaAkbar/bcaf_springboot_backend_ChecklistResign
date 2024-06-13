package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.FinalApproval;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.FinalApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/finalApproval")
public class FinalApprovalController {

    @Autowired
    private FinalApprovalService finalApprovalService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FinalApproval>> getFinalApprovalById(@PathVariable Long id) {
        Optional<FinalApproval> finalApproval = finalApprovalService.getFinalApprovalById(id);
        if (finalApproval.isPresent()) {
            ApiResponse<FinalApproval> response = new ApiResponse<>(finalApproval.get(), true, "Fetch succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<FinalApproval> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "FinalApproval not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FinalApproval>> createFinalApproval(@RequestBody FinalApproval finalApproval) {
        FinalApproval createdFinalApproval = finalApprovalService.createFinalApproval(finalApproval);
        ApiResponse<FinalApproval> response = new ApiResponse<>(createdFinalApproval, true, "Creation succeeded", HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FinalApproval>> updateFinalApproval(@PathVariable Long id, @RequestBody FinalApproval finalApprovalDetails) {
        FinalApproval updatedFinalApproval = finalApprovalService.updateFinalApproval(id, finalApprovalDetails);
        if (updatedFinalApproval != null) {
            ApiResponse<FinalApproval> response = new ApiResponse<>(updatedFinalApproval, true, "Update succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<FinalApproval> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "FinalApproval not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
