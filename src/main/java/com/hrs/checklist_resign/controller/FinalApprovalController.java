package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.PengajuanResign;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.dto.FinalApprovalDTO;
import com.hrs.checklist_resign.Model.FinalApproval;
import com.hrs.checklist_resign.dto.PostFinalApprovalDTO;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.FinalApprovalService;
import com.hrs.checklist_resign.service.PengajuanResignService;
import com.hrs.checklist_resign.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/final-approval")
public class FinalApprovalController {

    @Autowired
    private FinalApprovalService finalApprovalService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PengajuanResignService pengajuanResignService;

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<FinalApproval> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        String nipApprover = authentication.getName();

        UserDetail userDetailAtasan =  userDetailsService.findByUsername(nipApprover);




        String namaApprover = userDetailAtasan.getNama();

        // Retrieve the current final approval
        Optional<FinalApproval> finalApprovalObj = finalApprovalService.getFinalApprovalByIdv2(id);

        if (finalApprovalObj.isPresent()) {
            FinalApproval finalApproval = finalApprovalObj.get();

            // Update final approval
            finalApproval.setRemarks(postFinalApprovalDTO.getRemarks());
            finalApproval.setFinalApprovalStatus(postFinalApprovalDTO.getFinalApprovalStatus());

            if (finalApproval.getFinalApprovalStatus().equals("accept"))
            {
                //get pengajuanResign

                Optional<PengajuanResign> pengajuanResignOpt = pengajuanResignService.getResignationByNipUser(finalApproval.getNipKaryawanResign());

                PengajuanResign pengajuanResign = pengajuanResignOpt.get();

                //set approvedDate pengajuanResign
                finalApproval.setApprovedDate(new Date());
                finalApproval.setApprovedBy(namaApprover);
                pengajuanResign.setApprovedDateFinal(new Date());

                //save pengajuan resign
                pengajuanResignService.saveResignation(pengajuanResign);

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

    @GetMapping("/V2")
    public ResponseEntity<ApiResponse<Page<FinalApprovalDTO>>> getFinalApprovals(
            @RequestParam(required = false) String nipKaryawanResign,
            @RequestParam(required = false) String namaKaryawan,
            @RequestParam(required = false) String finalApprovalStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Page<FinalApprovalDTO> finalApprovals = finalApprovalService.getFinalApprovals(
                nipKaryawanResign, namaKaryawan, finalApprovalStatus, page, size, sortBy, sortDirection);

        if (finalApprovals.hasContent()) {
            ApiResponse<Page<FinalApprovalDTO>> response = new ApiResponse<>(
                    finalApprovals, true, "Fetch succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Page<FinalApprovalDTO>> response = new ApiResponse<>(
                    false, "No records found", HttpStatus.NOT_FOUND.value(), "FinalApproval not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}
