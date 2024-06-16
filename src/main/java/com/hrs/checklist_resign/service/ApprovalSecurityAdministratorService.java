package com.hrs.checklist_resign.service;


import com.hrs.checklist_resign.Model.ApprovalSecurityAdministrator;
import com.hrs.checklist_resign.repository.ApprovalSecurityAdministratorRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprovalSecurityAdministratorService {

    private final ApprovalSecurityAdministratorRepository repository;

    @Autowired
    private CheckingAllApprovalsStatus checkingAllApprovalsStatus;

    @Autowired
    public ApprovalSecurityAdministratorService(ApprovalSecurityAdministratorRepository repository) {
        this.repository = repository;
    }

    public List<ApprovalSecurityAdministrator> findAll() {
        return repository.findAll();
    }

    public Optional<ApprovalSecurityAdministrator> findById(Long id) {
        return repository.findById(id);
    }

    public ResponseEntity<ApiResponse<ApprovalSecurityAdministrator>> save(ApprovalSecurityAdministrator approvalSecurityAdministrator) {
        ApprovalSecurityAdministrator savedApprovalSecurityAdministrator = repository.save(approvalSecurityAdministrator);
        if (savedApprovalSecurityAdministrator != null) {
            ApiResponse<ApprovalSecurityAdministrator> response = new ApiResponse<>(savedApprovalSecurityAdministrator, true, "Operation succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalSecurityAdministrator> response = new ApiResponse<>(false, "Operation failed", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Operation failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<ApprovalSecurityAdministrator>> update(Long id, ApprovalSecurityAdministrator approvalSecurityAdministratorDetails) {
        Optional<ApprovalSecurityAdministrator> optionalApprovalSecurityAdministrator = repository.findById(id);
        if (!optionalApprovalSecurityAdministrator.isPresent()) {
            ApiResponse<ApprovalSecurityAdministrator> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalSecurityAdministrator not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalSecurityAdministrator approvalSecurityAdministrator = optionalApprovalSecurityAdministrator.get();
        approvalSecurityAdministrator.setPermohonanPenutupanUser(approvalSecurityAdministratorDetails.getPermohonanPenutupanUser());
        approvalSecurityAdministrator.setPenutupanEmailBCA(approvalSecurityAdministratorDetails.getPenutupanEmailBCA());
        approvalSecurityAdministrator.setPengembalianToken(approvalSecurityAdministratorDetails.getPengembalianToken());
        approvalSecurityAdministrator.setApprovalSecurityAdministratorStatus(approvalSecurityAdministratorDetails.getApprovalSecurityAdministratorStatus());
        approvalSecurityAdministrator.setRemarks(approvalSecurityAdministratorDetails.getRemarks());

        //checking all approval statuslogAction(id, "Final form not created due to pending approvals");
        boolean allApprove = checkingAllApprovalsStatus.doCheck(id);

        if (allApprove) {
            // Create the final form
            System.out.println("========== should be do creating ==========");
            checkingAllApprovalsStatus.createFinalApproval(id);
        } else {
            // Log or take other actions if final form is not created

        }

        ApprovalSecurityAdministrator updatedApprovalSecurityAdministrator = repository.save(approvalSecurityAdministrator);
        ApiResponse<ApprovalSecurityAdministrator> response = new ApiResponse<>(updatedApprovalSecurityAdministrator, true, "Update succeeded", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
