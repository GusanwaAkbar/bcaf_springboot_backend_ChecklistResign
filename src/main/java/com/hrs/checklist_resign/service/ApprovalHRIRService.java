package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRTalent;
import com.hrs.checklist_resign.repository.ApprovalHRIRRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprovalHRIRService {

    @Autowired
    ApprovalHRIRRepository approvalHRIRRepository;
    public ApprovalHRIR findApprovalHRIRById (Long id)
    {
        //get item by id using Repository
        ApprovalHRIR approvalHRIRObj  = approvalHRIRRepository.getReferenceById(id);

        return approvalHRIRObj;
    }

    public List<ApprovalHRIR> findAllApprovalHRIR()
    {
        //get all item
        List<ApprovalHRIR> allApprovalHRIR = approvalHRIRRepository.findAll();

        return  allApprovalHRIR;
    }

    public  ApprovalHRIR saveApprovalHRIR(ApprovalHRIR approvalHRIR)
    {
        return approvalHRIRRepository.save(approvalHRIR);
    }

    public void deleteApprovalHRIR(Long id)
    {
        approvalHRIRRepository.deleteById(id);
    }

    public ResponseEntity<?> update(Long id, ApprovalHRIR approvalHRIRDetails) {

        // Start Authentication checking
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        // Checking current approval HRIR
        Optional<ApprovalHRIR> approvalHRIRObj = approvalHRIRRepository.findById(id);
        if (approvalHRIRObj == null) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Approval HRIR Not Found", HttpStatus.NOT_FOUND.value(), "Approval HRIR with ID " + id + " is not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Update the approval HRIR
        approvalHRIRObj.get().setExitInterview(approvalHRIRDetails.getExitInterview());
        approvalHRIRObj.get().setApprovalHRIRStatus(approvalHRIRDetails.getApprovalHRIRStatus());
        approvalHRIRObj.get().setRemarks(approvalHRIRDetails.getRemarks());

        // Save the instance
        ApprovalHRIR savedApprovalHRIR = approvalHRIRRepository.save(approvalHRIRObj.get());

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
