package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalTreasury;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.repository.ApprovalTreasuryRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ApprovalTreasuryService {

    private final ApprovalTreasuryRepository repository;

    @Autowired
    private UserDetailsService userDetailsService;

    private final String uploadDir = "/home/gusanwa/AA_Programming/checklist-resign-app/checklist-resign/storage/ApprovalTreasury";

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<ApprovalTreasury> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        String nipApprover = authentication.getName();

        UserDetail userDetailAtasan =  userDetailsService.findByUsername(nipApprover);
        String namaApprover = userDetailAtasan.getNama();

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
        boolean allApprove = checkingAllApprovalsStatus.doCheck(id, "TREASURY");

        if(approvalTreasury.getApprovalTreasuryStatus().equals("accept"))
        {
            approvalTreasury.setApprovedDate(new Date());
            approvalTreasury.setApprovedBy(namaApprover);
        }

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

    public ResponseEntity<ApiResponse<ApprovalTreasury>> handleFileUpload(ApprovalTreasury approvalTreasury, MultipartFile file) throws IOException
    {

        String fileName = file.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), path);

        approvalTreasury.setDocumentPath(path.toString());
        return save(approvalTreasury);
    }
}
