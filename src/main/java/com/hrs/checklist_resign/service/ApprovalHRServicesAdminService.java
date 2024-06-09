package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalHRServicesAdmin;
import com.hrs.checklist_resign.repository.ApprovalHRServicesAdminRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprovalHRServicesAdminService {

    private final ApprovalHRServicesAdminRepository repository;

    @Autowired
    public ApprovalHRServicesAdminService(ApprovalHRServicesAdminRepository repository) {
        this.repository = repository;
    }

    public List<ApprovalHRServicesAdmin> findAll() {
        return repository.findAll();
    }

    public Optional<ApprovalHRServicesAdmin> findById(Long id) {
        return repository.findById(id);
    }

    public ResponseEntity<ApiResponse<ApprovalHRServicesAdmin>> save(ApprovalHRServicesAdmin approvalHRServicesAdmin) {
        ApprovalHRServicesAdmin savedApprovalHRServicesAdmin = repository.save(approvalHRServicesAdmin);
        if (savedApprovalHRServicesAdmin != null) {
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(savedApprovalHRServicesAdmin, true, "Operation succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(false, "Operation failed", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Operation failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<ApprovalHRServicesAdmin>> update(Long id, ApprovalHRServicesAdmin approvalHRServicesAdminDetails) {
        Optional<ApprovalHRServicesAdmin> optionalApprovalHRServicesAdmin = repository.findById(id);
        if (!optionalApprovalHRServicesAdmin.isPresent()) {
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalHRServicesAdmin not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalHRServicesAdmin approvalHRServicesAdmin = optionalApprovalHRServicesAdmin.get();
        approvalHRServicesAdmin.setExcessOfClaim(approvalHRServicesAdminDetails.getExcessOfClaim());
        approvalHRServicesAdmin.setPenyelesaianBiayaHR(approvalHRServicesAdminDetails.getPenyelesaianBiayaHR());
        approvalHRServicesAdmin.setPenonaktifanKartuElektronik(approvalHRServicesAdminDetails.getPenonaktifanKartuElektronik());
        approvalHRServicesAdmin.setApprovalHRServicesAdminStatus(approvalHRServicesAdminDetails.getApprovalHRServicesAdminStatus());

        ApprovalHRServicesAdmin updatedApprovalHRServicesAdmin = repository.save(approvalHRServicesAdmin);
        ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(updatedApprovalHRServicesAdmin, true, "Update succeeded", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
