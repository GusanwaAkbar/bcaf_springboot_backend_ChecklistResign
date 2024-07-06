package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.*;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalHRIRService;
import com.hrs.checklist_resign.service.ApprovalHRTalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/karyawan-resign")
    public ResponseEntity<ApiResponse<ApprovalHRTalent>> getByNipKaryawanResign() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalHRTalent> approvalHRTalent = approvalHRTalentService.findByNipKaryawanResign(nipKaryawanResign);
        if (approvalHRTalent.isPresent()) {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(approvalHRTalent.get(), true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
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
        return approvalHRTalentService.update(id, approvalHRTalentDetails);
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ApprovalHRTalent>> uploadFileHRTalent(@RequestParam("file") MultipartFile file) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalHRTalent> approvalOpt = approvalHRTalentService.findByNipKaryawanResign(nipKaryawanResign);
        ApprovalHRTalent approval = approvalOpt.get();

        if (approval == null) {
            throw new RuntimeException("ApprovalHRTalent not found");
        }

        try {
            ApprovalHRTalent updatedApproval = approvalHRTalentService.handleFileUpload(approval, file);
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(updatedApproval, true, "File uploaded successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(null, false, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

