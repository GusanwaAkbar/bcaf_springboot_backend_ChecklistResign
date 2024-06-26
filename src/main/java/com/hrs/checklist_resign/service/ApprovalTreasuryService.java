package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalTreasury;
import com.hrs.checklist_resign.repository.ApprovalTreasuryRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ApprovalTreasuryService {

    private final ApprovalTreasuryRepository repository;

    @Autowired
    private CheckingAllApprovalsStatus checkingAllApprovalsStatus;

    @Autowired
    public ApprovalTreasuryService(ApprovalTreasuryRepository repository) {
        this.repository = repository;
    }

    public List<ApprovalTreasury> findAll() {
        return repository.findAll();
    }

    public Optional<ApprovalTreasury> findByNipKaryawanResign(String nipKaryawanResign) {
        return repository.findByNipKaryawanResign(nipKaryawanResign);
    }

    public Optional<ApprovalTreasury> findById(Long id) {
        return repository.findById(id);
    }

    public ResponseEntity<ApiResponse<ApprovalTreasury>> save(ApprovalTreasury approvalTreasury) {
        ApprovalTreasury savedApprovalTreasury = repository.save(approvalTreasury);
        if (savedApprovalTreasury != null) {
            ApiResponse<ApprovalTreasury> response = new ApiResponse<>(savedApprovalTreasury, true, "Operation succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalTreasury> response = new ApiResponse<>(false, "Operation failed", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Operation failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<ApiResponse<ApprovalTreasury>> update(@PathVariable Long id, @RequestBody ApprovalTreasury approvalTreasuryDetails) {
        Optional<ApprovalTreasury> optionalApprovalTreasury = repository.findById(id);
        if (!optionalApprovalTreasury.isPresent()) {
            ApiResponse<ApprovalTreasury> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalTreasury not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalTreasury approvalTreasury = optionalApprovalTreasury.get();
        approvalTreasury.setBiayaAdvance(approvalTreasuryDetails.getBiayaAdvance());
        approvalTreasury.setBlokirFleet(approvalTreasuryDetails.getBlokirFleet());
        approvalTreasury.setApprovalTreasuryStatus(approvalTreasuryDetails.getApprovalTreasuryStatus());
        approvalTreasury.setRemarks(approvalTreasuryDetails.getRemarks());

        //checking all approval statuslogAction(id, "Final form not created due to pending approvals");
        boolean allApprove = checkingAllApprovalsStatus.doCheck(id);

        if (allApprove) {
            // Create the final form
            checkingAllApprovalsStatus.createFinalApproval(id);
        } else {
            // Log or take other actions if final form is not created

        }

        ApprovalTreasury updatedApprovalTreasury = repository.save(approvalTreasury);
        ApiResponse<ApprovalTreasury> response = new ApiResponse<>(updatedApprovalTreasury, true, "Update succeeded", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
