package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalSecurityAdministrator;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalSecurityAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/approval-security-administrator")
public class ApprovalSecurityAdministratorController {

    private final ApprovalSecurityAdministratorService service;

    @Autowired
    public ApprovalSecurityAdministratorController(ApprovalSecurityAdministratorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalSecurityAdministrator>>> getAll() {
        List<ApprovalSecurityAdministrator> approvalSecurityAdministratorList = service.findAll();
        ApiResponse<List<ApprovalSecurityAdministrator>> response = new ApiResponse<>(approvalSecurityAdministratorList, true, "Fetched all records successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalSecurityAdministrator>> getById(@PathVariable Long id) {
        Optional<ApprovalSecurityAdministrator> approvalSecurityAdministrator = service.findById(id);
        if (approvalSecurityAdministrator.isPresent()) {
            ApiResponse<ApprovalSecurityAdministrator> response = new ApiResponse<>(approvalSecurityAdministrator.get(), true, "Record found", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalSecurityAdministrator> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalSecurityAdministrator not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApprovalSecurityAdministrator>> create(@RequestBody ApprovalSecurityAdministrator approvalSecurityAdministrator) {
        return service.save(approvalSecurityAdministrator);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalSecurityAdministrator>> update(@PathVariable Long id,
                                                                             @RequestBody ApprovalSecurityAdministrator approvalSecurityAdministrator) {
        return service.update(id, approvalSecurityAdministrator);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalSecurityAdministrator not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        service.deleteById(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Record deleted successfully", HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
