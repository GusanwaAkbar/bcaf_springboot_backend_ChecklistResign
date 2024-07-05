package com.hrs.checklist_resign.controller;


import com.hrs.checklist_resign.Model.ApprovalHRLearning;
import com.hrs.checklist_resign.Model.ApprovalTreasury;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalTreasuryService;
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
@RequestMapping("/api/approval-treasury")
public class ApprovalTreasuryController {

    private final ApprovalTreasuryService service;

    @Autowired
    public ApprovalTreasuryController(ApprovalTreasuryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalTreasury>>> getAll() {
        List<ApprovalTreasury> approvalTreasuryList = service.findAll();
        ApiResponse<List<ApprovalTreasury>> response = new ApiResponse<>(approvalTreasuryList, true, "Fetched all records successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/karyawan-resign")
    public ResponseEntity<ApiResponse<ApprovalTreasury>> getByNipKaryawanResign() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalTreasury> approvalTreasury = service.findByNipKaryawanResign(nipKaryawanResign);
        if (approvalTreasury.isPresent()) {
            ApiResponse<ApprovalTreasury> response = new ApiResponse<>(approvalTreasury.get(), true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalTreasury> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalTreasury>> getById(@PathVariable Long id) {
        Optional<ApprovalTreasury> approvalTreasury = service.findById(id);
        if (approvalTreasury.isPresent()) {
            ApiResponse<ApprovalTreasury> response = new ApiResponse<>(approvalTreasury.get(), true, "Record found", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalTreasury> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalTreasury not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApprovalTreasury>> create(@RequestBody ApprovalTreasury approvalTreasury) {
        return service.save(approvalTreasury);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalTreasury>> update(@PathVariable Long id,
                                                                @RequestBody ApprovalTreasury approvalTreasury) {
        return service.update(id, approvalTreasury);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalTreasury not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        service.deleteById(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Record deleted successfully", HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/upload-treasury")
    public ResponseEntity<ApiResponse<ApprovalTreasury>> uploadFileTreasury(@RequestParam("file") MultipartFile file) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalTreasury> approvalOpt = service.findByNipKaryawanResign(nipKaryawanResign);
        ApprovalTreasury approval = approvalOpt.get();

        if (approval == null) {
            throw new RuntimeException("ApprovalTreasury not found");
        }

        try {
            ResponseEntity<ApiResponse<ApprovalTreasury>> updatedApproval = service.handleFileUpload(approval, file);
            ApiResponse<ApprovalTreasury> response = new ApiResponse(updatedApproval, true, "File uploaded successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            ApiResponse<ApprovalTreasury> response = new ApiResponse<>(null, false, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
