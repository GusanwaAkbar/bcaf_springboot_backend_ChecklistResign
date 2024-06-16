package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRTalent;
import com.hrs.checklist_resign.repository.ApprovalHRTalentRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.management.ListenerNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ApprovalHRTalentService {

    @Autowired
    ApprovalHRTalentRepository approvalHRTalentRepository;

    @Autowired
    private CheckingAllApprovalsStatus checkingAllApprovalsStatus;

    public ApprovalHRTalent saveApprovalHRTalent (ApprovalHRTalent approvalHRTalent)
    {
        return approvalHRTalentRepository.save(approvalHRTalent);
    }

    public void deleteApprovalHRTalent (Long id)
    {
        approvalHRTalentRepository.deleteById(id);
    }

    public Optional<ApprovalHRTalent> findById (Long id)
    {
        return approvalHRTalentRepository.findById(id);
    }

    public List<ApprovalHRTalent> findAll ()
    {
        return approvalHRTalentRepository.findAll();
    }

    public ResponseEntity<?> update(Long id, ApprovalHRTalent approvalHRTalentDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        // Get existing item
        Optional<ApprovalHRTalent> approvalHRTalentOptional = approvalHRTalentRepository.findById(id);
        if (!approvalHRTalentOptional.isPresent()) {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(false, "Approval HR Talent Not Found", HttpStatus.NOT_FOUND.value(), "Approval HR Talent with ID " + id + " is not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalHRTalent existingApprovalHRTalent = approvalHRTalentOptional.get();
        // Update fields
        existingApprovalHRTalent.setPengecekanBiaya(approvalHRTalentDetails.getPengecekanBiaya());
        existingApprovalHRTalent.setApprovalHRTalentStatus(approvalHRTalentDetails.getApprovalHRTalentStatus());
        existingApprovalHRTalent.setRemarks(approvalHRTalentDetails.getRemarks());

        //save the instance
        ApprovalHRTalent approvalHRTalent = approvalHRTalentRepository.save(existingApprovalHRTalent);

        //checking all approval statuslogAction(id, "Final form not created due to pending approvals");
        boolean allApprove = checkingAllApprovalsStatus.doCheck(id);

        if (allApprove) {
            // Create the final form
            checkingAllApprovalsStatus.createFinalApproval(id);
        } else {
            // Log or take other actions if final form is not created

        }

        ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(approvalHRTalent, true, "Approval HR Talent Updated", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    }

