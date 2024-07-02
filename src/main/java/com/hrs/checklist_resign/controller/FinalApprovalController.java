package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.dto.FinalApprovalDTO;
import com.hrs.checklist_resign.Model.FinalApproval;
import com.hrs.checklist_resign.dto.PostFinalApprovalDTO;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.FinalApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/final-approval")
public class FinalApprovalController {

    @Autowired
    private FinalApprovalService finalApprovalService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FinalApprovalDTO>> getFinalApprovalById(@PathVariable Long id) {
        Optional<FinalApprovalDTO> finalApproval = finalApprovalService.getFinalApprovalById(id);
        if (finalApproval.isPresent()) {
            ApiResponse<FinalApprovalDTO> response = new ApiResponse<>(finalApproval.get(), true, "Fetch succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<FinalApprovalDTO> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "FinalApproval not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<FinalApprovalDTO>>> getAllFinalApproval() {
        List<FinalApprovalDTO> finalApproval = finalApprovalService.getAllFinalApproval();
        if (!finalApproval.isEmpty()) {
            ApiResponse<List<FinalApprovalDTO>> response = new ApiResponse<>(finalApproval, true, "Fetch succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<List<FinalApprovalDTO>> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "FinalApproval not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FinalApproval>> createFinalApproval(@RequestBody FinalApproval finalApproval) {
        FinalApproval createdFinalApproval = finalApprovalService.createFinalApproval(finalApproval);
        ApiResponse<FinalApproval> response = new ApiResponse<>(createdFinalApproval, true, "Creation succeeded", HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FinalApproval>> updateFinalApproval(
            @PathVariable Long id,
            @RequestBody PostFinalApprovalDTO postFinalApprovalDTO) {

        // Retrieve the current final approval
        Optional<FinalApproval> finalApprovalObj = finalApprovalService.getFinalApprovalByIdv2(id);

        if (finalApprovalObj.isPresent()) {
            FinalApproval finalApproval = finalApprovalObj.get();

            // Update final approval
            finalApproval.setRemarks(postFinalApprovalDTO.getRemarks());
            finalApproval.setFinalApprovalStatus(postFinalApprovalDTO.getFinalApprovalStatus());

            if (finalApproval.getFinalApprovalStatus().equals("accept"))
            {
                finalApproval.setApprovedDate(new Date());
            }

            // Save updated final approval
            FinalApproval updatedFinalApproval = finalApprovalService.updateFinalApproval(finalApproval);

            ApiResponse<FinalApproval> response = new ApiResponse<>(updatedFinalApproval, true, "Update succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<FinalApproval> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "FinalApproval not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
