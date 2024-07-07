package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalHRLearning;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.repository.ApprovalHRLearningRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ApprovalHRLearningService {

    private final ApprovalHRLearningRepository repository;

    @Autowired
    private UserDetailsService userDetailsService;

    private final String uploadDir = "/home/gusanwa/AA_Programming/checklist-resign-app/checklist-resign/storage/ApprovalHRLearning";

    @Autowired
    private CheckingAllApprovalsStatus checkingAllApprovalsStatus;

    @Autowired
    public ApprovalHRLearningService(ApprovalHRLearningRepository repository) {
        this.repository = repository;
    }


    public List<ApprovalHRLearning> findAll() {
        return repository.findAll();
    }

    public Optional<ApprovalHRLearning> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<ApprovalHRLearning> findByNipKaryawanResign(String nipKaryawanResign) {
        return repository.findByNipKaryawanResign(nipKaryawanResign);
    }

    public ResponseEntity<ApiResponse<ApprovalHRLearning>> save(ApprovalHRLearning approvalHRLearning) {
        ApprovalHRLearning savedApprovalHRLearning = repository.save(approvalHRLearning);
        if (savedApprovalHRLearning != null) {
            ApiResponse<ApprovalHRLearning> response = new ApiResponse<>(savedApprovalHRLearning, true, "Operation succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRLearning> response = new ApiResponse<>(false, "Operation failed", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Operation failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<ApprovalHRLearning>> update(Long id, ApprovalHRLearning approvalHRLearningDetails) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<ApprovalHRLearning> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        String nipApprover = authentication.getName();

        UserDetail userDetailAtasan =  userDetailsService.findByUsername(nipApprover);
        String namaApprover = userDetailAtasan.getNama();

        Optional<ApprovalHRLearning> optionalApprovalHRLearning = repository.findById(id);
        if (!optionalApprovalHRLearning.isPresent()) {
            ApiResponse<ApprovalHRLearning> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalHRLearning not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalHRLearning approvalHRLearning = optionalApprovalHRLearning.get();
        approvalHRLearning.setPengecekanBiayaTraining(approvalHRLearningDetails.getPengecekanBiayaTraining());
        approvalHRLearning.setApprovalHRLearningStatus(approvalHRLearningDetails.getApprovalHRLearningStatus());
        approvalHRLearning.setRemarks(approvalHRLearningDetails.getRemarks());

        if(approvalHRLearning.getApprovalHRLearningStatus().equals("accept"))
        {
            approvalHRLearning.setApprovedDate(new Date());
            approvalHRLearning.setApprovedBy(namaApprover);
        }

        ApprovalHRLearning updatedApprovalHRLearning = repository.save(approvalHRLearning);

        //checking all approval statuslogAction(id, "Final form not created due to pending approvals");
        boolean allApprove = checkingAllApprovalsStatus.doCheck(id);

        if (allApprove) {
            // Create the final form
            checkingAllApprovalsStatus.createFinalApproval(id);
        } else {
            // Log or take other actions if final form is not created

        }



        ApiResponse<ApprovalHRLearning> response = new ApiResponse<>(updatedApprovalHRLearning, true, "Update succeeded", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ResponseEntity<ApiResponse<ApprovalHRLearning>> handleFileUpload(ApprovalHRLearning approvalHRLearning, MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), path);

        approvalHRLearning.setDocumentPath(path.toString());
        return save(approvalHRLearning);
    }
}
