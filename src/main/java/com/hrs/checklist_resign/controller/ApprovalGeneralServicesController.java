package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalGeneralServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/approval-general-services")
public class ApprovalGeneralServicesController {

    private final ApprovalGeneralServicesService service;

    @Autowired
    public ApprovalGeneralServicesController(ApprovalGeneralServicesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalGeneralServices>>> getAll() {
        List<ApprovalGeneralServices> approvalGeneralServicesList = service.findAll();
        ApiResponse<List<ApprovalGeneralServices>> response = new ApiResponse<>(approvalGeneralServicesList, true, "Fetched all records successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalGeneralServices>> getById(@PathVariable Long id) {
        Optional<ApprovalGeneralServices> approvalGeneralServices = service.findById(id);
        if (approvalGeneralServices.isPresent()) {
            ApiResponse<ApprovalGeneralServices> response = new ApiResponse<>(approvalGeneralServices.get(), true, "Record found", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalGeneralServices> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApprovalGeneralServices>> create(@RequestBody ApprovalGeneralServices approvalGeneralServices) {
        return service.save(approvalGeneralServices);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalGeneralServices>> update(@PathVariable Long id,
                                                                       @RequestBody ApprovalGeneralServices approvalGeneralServices) {
        return service.update(id, approvalGeneralServices);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        service.deleteById(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Record deleted successfully", HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
