package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.ApprovalHRTalent;
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
@RequestMapping("/api/approval-hr-ir")
public class ApprovalHRIRController {

    @Autowired
    ApprovalHRIRService approvalHRIRService;

    @Autowired
    ApprovalHRTalentService approvalHRTalentService;

    @GetMapping()
    public ResponseEntity<?> getAllApprovalHRIR()
    {
        // get all item
       List<ApprovalHRIR> allApprovalHRIR = approvalHRIRService.findAllApprovalHRIR();

       if(  !allApprovalHRIR.isEmpty())
       {
           ApiResponse<List<ApprovalHRIR>> response = new ApiResponse<>(allApprovalHRIR, true, "Find All Approval HRIR Succeeded", HttpStatus.OK.value());
           return new ResponseEntity<>(response, HttpStatus.OK);
       } else
       {
           ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Find All Failed", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Find All HRIR Failed to response");
           return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
       }


    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApprovalHRIRbyId(@PathVariable Long id)
    {

        // get item by id using service
        Optional<ApprovalHRIR> approvalHRIR = approvalHRIRService.findApprovalHRIRById(id);


        if (approvalHRIR.isPresent())
        {

            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(approvalHRIR.get(), true, "Find Approval HRIR  by ID Succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else
        {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Approval HRIR Not Found", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Approval HRIR with ID" + id.toString() + "is not Found");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //if updated by manager
    @PutMapping("/{id}")
    public ResponseEntity<?> updateApprovalHRIR(@RequestBody ApprovalHRIR approvalHRIRDetails, @PathVariable Long id) {

        return approvalHRIRService.update(id, approvalHRIRDetails);
    }


}
