package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRLearning;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalHRLearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/approval-hr-learning")
public class ApprovalHRLearningController {

    private final ApprovalHRLearningService service;

    @Autowired
    public ApprovalHRLearningController(ApprovalHRLearningService service) {
        this.service = service;
    }

    @GetMapping("/karyawan-resign")
    public ResponseEntity<ApiResponse<ApprovalHRLearning>> getByNipKaryawanResign() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalHRLearning> approvalHRLearning = service.findByNipKaryawanResign(nipKaryawanResign);
        if (approvalHRLearning.isPresent()) {
            ApiResponse<ApprovalHRLearning> response = new ApiResponse<>(approvalHRLearning.get(), true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRLearning> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalHRLearning>>> getAll() {
        List<ApprovalHRLearning> approvalHRLearningList = service.findAll();
        ApiResponse<List<ApprovalHRLearning>> response = new ApiResponse<>(approvalHRLearningList, true, "Fetched all records successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalHRLearning>> getById(@PathVariable Long id) {
        Optional<ApprovalHRLearning> approvalHRLearning = service.findById(id);
        if (approvalHRLearning.isPresent()) {
            ApiResponse<ApprovalHRLearning> response = new ApiResponse<>(approvalHRLearning.get(), true, "Record found", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRLearning> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalHRLearning not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApprovalHRLearning>> create(@RequestBody ApprovalHRLearning approvalHRLearning) {
        return service.save(approvalHRLearning);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalHRLearning>> update(@PathVariable Long id,
                                                                  @RequestBody ApprovalHRLearning approvalHRLearning) {
        return service.update(id, approvalHRLearning);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalHRLearning not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        service.deleteById(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Record deleted successfully", HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
