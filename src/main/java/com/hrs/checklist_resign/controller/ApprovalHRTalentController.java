package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRTalent;
import com.hrs.checklist_resign.Model.PengajuanResign;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalHRIRService;
import com.hrs.checklist_resign.service.ApprovalHRTalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/approval-hr-talent")
public class ApprovalHRTalentController {

    @Autowired
    private ApprovalHRTalentService approvalHRTalentService;

    @Autowired
    private ApprovalHRIRService approvalHRIRService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalHRTalent>>> findAllApprovalHRTalent() {
        List<ApprovalHRTalent> approvalHRTalents = approvalHRTalentService.findAll();
        ApiResponse<List<ApprovalHRTalent>> response = new ApiResponse<>(approvalHRTalents, true, "Fetched all resignations", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalHRTalent>> getApprovalHRTalentById(@PathVariable Long id) {
        Optional<ApprovalHRTalent> approvalHRTalent = approvalHRTalentService.findById(id);

        if (approvalHRTalent.isPresent()) {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(approvalHRTalent.get(), true, "Approval HR Talent Found", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(false, "Get data failed", HttpStatus.NOT_FOUND.value(), "No resignation found with ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateApprovalHRTalent(@PathVariable Long id, @RequestBody ApprovalHRTalent approvalHRTalentDetails) {
        // Start Authentication checking
        return approvalHRTalentService.update(id, approvalHRTalentDetails);
    }
}

