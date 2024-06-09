package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRTalent;
import com.hrs.checklist_resign.Model.PengajuanResign;
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
@RequestMapping("/api/approval-hr-talent")
public class ApprovalHRTalentController {

    @Autowired
    private ApprovalHRTalentService approvalHRTalentService;

    @Autowired
    private ApprovalHRIRService approvalHRIRService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalHRTalent>>> findAllApprovalHRTalent() {
        List<ApprovalHRTalent> approvalHRTalents = approvalHRTalentService.findAll();
        ApiResponse<List<ApprovalHRTalent>> response = new ApiResponse<>(approvalHRTalents, true, "Fetched all resignations", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalHRTalent>> getApprovalHRTalentById(@PathVariable Long id) {
        Optional<ApprovalHRTalent> approvalHRTalent = approvalHRTalentService.findById(id);

        if (approvalHRTalent.isPresent()) {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(approvalHRTalent.get(), true, "Approval HR Talent Found", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(false, "Get data failed", HttpStatus.NOT_FOUND.value(), "No resignation found with ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateApprovalHRTalent(@PathVariable Long id, @RequestBody ApprovalHRTalent approvalHRTalentDetails) {
        // Start Authentication checking
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        // Get existing item
        Optional<ApprovalHRTalent> approvalHRTalentOptional = approvalHRTalentService.findById(id);
        if (!approvalHRTalentOptional.isPresent()) {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(false, "Approval HR Talent Not Found", HttpStatus.NOT_FOUND.value(), "Approval HR Talent with ID " + id + " is not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalHRTalent existingApprovalHRTalent = approvalHRTalentOptional.get();
        // Update fields
        existingApprovalHRTalent.setPengecekanBiaya(approvalHRTalentDetails.getPengecekanBiaya());
        existingApprovalHRTalent.setFilePengecekanBiaya(approvalHRTalentDetails.getFilePengecekanBiaya());
        existingApprovalHRTalent.setApprovalHRTalentStatus(approvalHRTalentDetails.getApprovalHRTalentStatus());
        existingApprovalHRTalent.setPengajuanResign(approvalHRTalentDetails.getPengajuanResign());
        //existingApprovalHRTalent.setApprovalAtasan(approvalHRTalentDetails.getApprovalAtasan());

        ApprovalHRTalent updatedApprovalHRTalent = approvalHRTalentService.saveApprovalHRTalent(existingApprovalHRTalent);

        // Check ApprovalHRIR status
        ApprovalHRIR approvalHRIR = approvalHRIRService.findApprovalHRIRById(id);
        if (approvalHRIR == null) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Approval HRIR Not Found", HttpStatus.NOT_FOUND.value(), "Approval HRIR with ID " + id + " is not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // If both ApprovalHRTalent and ApprovalHRIR are approved
        if (true) {
            // Create final form entity
            // Add your logic here for creating the final form entity
        }

        ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(updatedApprovalHRTalent, true, "Approval HR Talent Updated", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }
}

