package com.hrs.checklist_resign.controller;


import com.hrs.checklist_resign.Model.ApprovalTreasury;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalTreasuryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
