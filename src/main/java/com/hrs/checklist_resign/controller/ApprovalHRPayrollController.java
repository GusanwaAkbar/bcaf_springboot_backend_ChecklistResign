package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalHRLearning;
import com.hrs.checklist_resign.Model.ApprovalHRPayroll;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalHRPayrollService;
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
@RequestMapping("/api/approval-hr-payroll")
public class ApprovalHRPayrollController {

    private final ApprovalHRPayrollService service;

    @Autowired
    public ApprovalHRPayrollController(ApprovalHRPayrollService service) {
        this.service = service;
    }


    @GetMapping("/karyawan-resign")
    public ResponseEntity<ApiResponse<ApprovalHRPayroll>> getByNipKaryawanResign() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalHRPayroll> approvalHRPayroll = service.findByNipKaryawanResign(nipKaryawanResign);
        if (approvalHRPayroll.isPresent()) {
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(approvalHRPayroll.get(), true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalHRPayroll>>> getAll() {
        List<ApprovalHRPayroll> approvalHRPayrollList = service.findAll();
        ApiResponse<List<ApprovalHRPayroll>> response = new ApiResponse<>(approvalHRPayrollList, true, "Fetched all records successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalHRPayroll>> getById(@PathVariable Long id) {
        Optional<ApprovalHRPayroll> approvalHRPayroll = service.findById(id);
        if (approvalHRPayroll.isPresent()) {
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(approvalHRPayroll.get(), true, "Record found", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalHRPayroll not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApprovalHRPayroll>> create(@RequestBody ApprovalHRPayroll approvalHRPayroll) {
        return service.save(approvalHRPayroll);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalHRPayroll>> update(@PathVariable Long id,
                                                                 @RequestBody ApprovalHRPayroll approvalHRPayroll) {
        return service.update(id, approvalHRPayroll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalHRPayroll not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        service.deleteById(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Record deleted successfully", HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }


    @PostMapping("/upload-hrpayroll")
    public ResponseEntity<ApiResponse<ApprovalHRPayroll>> uploadFileHRPayroll(@RequestParam("file") MultipartFile file) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalHRPayroll> approvalOpt = service.findByNipKaryawanResign(nipKaryawanResign);
        ApprovalHRPayroll approval = approvalOpt.get();

        if (approval == null) {
            throw new RuntimeException("ApprovalHRPayroll not found");
        }

        try {
            ResponseEntity<ApiResponse<ApprovalHRPayroll>> updatedApproval = service.handleFileUpload(approval, file);
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse(updatedApproval, true, "File uploaded successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(null, false, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
