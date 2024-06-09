package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRTalent;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalHRIRService;
import com.hrs.checklist_resign.service.ApprovalHRTalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/approval-hr-ir")
public class ApprovalHRIRController {

    @Autowired
    ApprovalHRIRService approvalHRIRService;

    @Autowired
    ApprovalHRTalentService approvalHRTalentService;

    @GetMapping()
    public ResponseEntity<?> getAllApprovalHRIR()
    {
        // get all item
       List<ApprovalHRIR> allApprovalHRIR = approvalHRIRService.findAllApprovalHRIR();

       if(  !allApprovalHRIR.isEmpty())
       {
           ApiResponse<List<ApprovalHRIR>> response = new ApiResponse<>(allApprovalHRIR, true, "Find All Approval HRIR Succeeded", HttpStatus.OK.value());
           return new ResponseEntity<>(response, HttpStatus.OK);
       } else
       {
           ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Find All Failed", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Find All HRIR Failed to response");
           return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }


    }

    @GetMapping("/id")
    public ResponseEntity<?> getApprovalHRIRbyId(@PathVariable Long id)
    {

        // get item by id using service
        ApprovalHRIR approvalHRIR = approvalHRIRService.findApprovalHRIRById(id);


        if (approvalHRIR != null)
        {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(approvalHRIR, true, "Find Approval HRIR  by ID Succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else
        {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Approval HRIR Not Found", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Approval HRIR with ID" + id.toString() + "is not Found");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //if updated by manager
    @PutMapping("/{id}")
    public ResponseEntity<?> updateApprovalHRIR(@RequestBody ApprovalHRIR approvalHRIRDetails, @PathVariable Long id) {

        // Start Authentication checking
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        // Get current approval HRIR
        ApprovalHRIR approvalHRIRObj = approvalHRIRService.findApprovalHRIRById(id);
        if (approvalHRIRObj == null) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Approval HRIR Not Found", HttpStatus.NOT_FOUND.value(), "Approval HRIR with ID " + id + " is not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Get current approval HR Talent
        Optional<ApprovalHRTalent> approvalHRTalent = approvalHRTalentService.findById(id);
        if (!approvalHRTalent.isPresent()) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Approval HRTalent Not Found", HttpStatus.NOT_FOUND.value(), "Approval HRTalent with ID " + id + " is not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Update the approval HRIR
        approvalHRIRObj.setExitInterview(approvalHRIRDetails.getExitInterview());
        approvalHRIRObj.setApprovalHRIRStatus(approvalHRIRDetails.isApprovalHRIRStatus());

        // Save updated Approval HRIR Entity
        ApprovalHRIR savedApprovalHRIR = approvalHRIRService.saveApprovalHRIR(approvalHRIRObj);

        // If all approvals are approved by Manager
        if (true) {
            // Create final form entity
            // Add your logic here for creating the final form entity
        }

        // Response based on save result
        if (savedApprovalHRIR != null) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(savedApprovalHRIR, true, "Update Approval HRIR Succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Update Approval HRIR Failed", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Update approval HRIR failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
