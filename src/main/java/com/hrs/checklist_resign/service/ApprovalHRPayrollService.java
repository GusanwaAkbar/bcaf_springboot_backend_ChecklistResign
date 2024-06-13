package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalHRPayroll;
import com.hrs.checklist_resign.repository.ApprovalHRPayrollRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprovalHRPayrollService {

    private final ApprovalHRPayrollRepository repository;

    @Autowired
    public ApprovalHRPayrollService(ApprovalHRPayrollRepository repository) {
        this.repository = repository;
    }

    public List<ApprovalHRPayroll> findAll() {
        return repository.findAll();
    }

    public Optional<ApprovalHRPayroll> findById(Long id) {
        return repository.findById(id);
    }

    public ResponseEntity<ApiResponse<ApprovalHRPayroll>> save(ApprovalHRPayroll approvalHRPayroll) {
        ApprovalHRPayroll savedApprovalHRPayroll = repository.save(approvalHRPayroll);
        if (savedApprovalHRPayroll != null) {
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(savedApprovalHRPayroll, true, "Operation succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(false, "Operation failed", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Operation failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<ApprovalHRPayroll>> update(Long id, ApprovalHRPayroll approvalHRPayrollDetails) {
        Optional<ApprovalHRPayroll> optionalApprovalHRPayroll = repository.findById(id);
        if (!optionalApprovalHRPayroll.isPresent()) {
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalHRPayroll not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalHRPayroll approvalHRPayroll = optionalApprovalHRPayroll.get();
        approvalHRPayroll.setSoftLoan(approvalHRPayrollDetails.getSoftLoan());
        approvalHRPayroll.setEmergencyLoan(approvalHRPayrollDetails.getEmergencyLoan());
        approvalHRPayroll.setSmartphoneLoan(approvalHRPayrollDetails.getSmartphoneLoan());
        approvalHRPayroll.setMotorLoan(approvalHRPayrollDetails.getMotorLoan());
        approvalHRPayroll.setUmkLoan(approvalHRPayrollDetails.getUmkLoan());
        approvalHRPayroll.setLaptopLoan(approvalHRPayrollDetails.getLaptopLoan());
        approvalHRPayroll.setApprovalHRPayrollStatus(approvalHRPayrollDetails.getApprovalHRPayrollStatus());
        approvalHRPayroll.setRemarks(approvalHRPayrollDetails.getRemarks());

        ApprovalHRPayroll updatedApprovalHRPayroll = repository.save(approvalHRPayroll);
        ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(updatedApprovalHRPayroll, true, "Update succeeded", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
