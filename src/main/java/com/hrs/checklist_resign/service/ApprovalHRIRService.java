package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.repository.ApprovalHRIRRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ApprovalHRIRService {

    @Autowired
    ApprovalHRIRRepository approvalHRIRRepository;

    @Autowired
    private CheckingAllApprovalsStatus checkingAllApprovalsStatus;

    public Optional<ApprovalHRIR> findByNipKaryawanResign(String nipKaryawanResign) {
        return approvalHRIRRepository.findByNipKaryawanResign(nipKaryawanResign);
    }

    public Optional<ApprovalHRIR> findApprovalHRIRById (Long id)
    {
        //get item by id using Repository
        Optional<ApprovalHRIR> approvalHRIRObj  = approvalHRIRRepository.findById(id);

        return approvalHRIRObj;
    }

    public List<ApprovalHRIR> findAllApprovalHRIR()
    {
        //get all item
        List<ApprovalHRIR> allApprovalHRIR = approvalHRIRRepository.findAll();

        return  allApprovalHRIR;
    }

    public  ApprovalHRIR saveApprovalHRIR(ApprovalHRIR approvalHRIR)
    {
        return approvalHRIRRepository.save(approvalHRIR);
    }

    public void deleteApprovalHRIR(Long id)
    {
        approvalHRIRRepository.deleteById(id);
    }

    public ResponseEntity<?> update(Long id, ApprovalHRIR approvalHRIRDetails) {

        // Start Authentication checking
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        // Checking current approval HRIR
        Optional<ApprovalHRIR> approvalHRIRObj = approvalHRIRRepository.findById(id);
        if (approvalHRIRObj == null) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Approval HRIR Not Found", HttpStatus.NOT_FOUND.value(), "Approval HRIR with ID " + id + " is not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalHRIR approvalHRIR = approvalHRIRObj.get();

        // Update the approval HRIR
        approvalHRIR.setExitInterview(approvalHRIRDetails.getExitInterview());
        approvalHRIR.setApprovalHRIRStatus(approvalHRIRDetails.getApprovalHRIRStatus());
        approvalHRIR.setRemarks(approvalHRIRDetails.getRemarks());

        if (approvalHRIR.getApprovalHRIRStatus().equals("accept"))
        {
            approvalHRIR.setApprovedDate(new Date());
        }

        // Save the instance
        ApprovalHRIR savedApprovalHRIR = approvalHRIRRepository.save(approvalHRIR);

        //checking all approval statuslogAction(id, "Final form not created due to pending approvals");
        boolean allApprove = checkingAllApprovalsStatus.doCheck(id);

        if (allApprove) {
            // Create the final form
            checkingAllApprovalsStatus.createFinalApproval(id);
        } else {
            // Log or take other actions if final form is not created

        }

        // Response based on save result
        if (savedApprovalHRIR != null) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(savedApprovalHRIR, true, "Update Approval HRIR Succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Update Approval HRIR Failed", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Update approval HRIR failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
