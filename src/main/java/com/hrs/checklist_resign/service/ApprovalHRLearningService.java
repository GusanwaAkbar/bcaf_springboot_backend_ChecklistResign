package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalHRLearning;
import com.hrs.checklist_resign.repository.ApprovalHRLearningRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprovalHRLearningService {

    private final ApprovalHRLearningRepository repository;

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
        Optional<ApprovalHRLearning> optionalApprovalHRLearning = repository.findById(id);
        if (!optionalApprovalHRLearning.isPresent()) {
            ApiResponse<ApprovalHRLearning> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalHRLearning not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalHRLearning approvalHRLearning = optionalApprovalHRLearning.get();
        approvalHRLearning.setPengecekanBiayaTraining(approvalHRLearningDetails.getPengecekanBiayaTraining());
        approvalHRLearning.setApprovalHRLearningStatus(approvalHRLearningDetails.getApprovalHRLearningStatus());
        approvalHRLearning.setRemarks(approvalHRLearningDetails.getRemarks());

        ApprovalHRLearning updatedApprovalHRLearning = repository.save(approvalHRLearning);

        //checking all approval status
        checkingAllApprovalsStatus.doCheck(id);



        ApiResponse<ApprovalHRLearning> response = new ApiResponse<>(updatedApprovalHRLearning, true, "Update succeeded", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
