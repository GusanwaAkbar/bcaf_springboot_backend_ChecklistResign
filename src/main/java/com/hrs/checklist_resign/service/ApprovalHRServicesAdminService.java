package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalHRServicesAdmin;
import com.hrs.checklist_resign.repository.ApprovalHRServicesAdminRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class ApprovalHRServicesAdminService {

    private final ApprovalHRServicesAdminRepository repository;

    @Autowired
    private CheckingAllApprovalsStatus checkingAllApprovalsStatus;

    private final String uploadDir = "/home/gusanwa/AA_Programming/checklist-resign-app/checklist-resign/storage/ApprovalHRServicesAdmin";

    @Autowired
    public ApprovalHRServicesAdminService(ApprovalHRServicesAdminRepository repository) {
        this.repository = repository;
    }

    public List<ApprovalHRServicesAdmin> findAll() {
        return repository.findAll();
    }

    public Optional<ApprovalHRServicesAdmin> findByNipKaryawanResign(String nipKaryawanResign) {
        return repository.findByNipKaryawanResign(nipKaryawanResign);
    }

    public Optional<ApprovalHRServicesAdmin> findById(Long id) {
        return repository.findById(id);
    }

    public ResponseEntity<ApiResponse<ApprovalHRServicesAdmin>> save(ApprovalHRServicesAdmin approvalHRServicesAdmin) {
        ApprovalHRServicesAdmin savedApprovalHRServicesAdmin = repository.save(approvalHRServicesAdmin);
        if (savedApprovalHRServicesAdmin != null) {
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(savedApprovalHRServicesAdmin, true, "Operation succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(false, "Operation failed", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Operation failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<ApprovalHRServicesAdmin>> update(Long id, ApprovalHRServicesAdmin approvalHRServicesAdminDetails) {
        Optional<ApprovalHRServicesAdmin> optionalApprovalHRServicesAdmin = repository.findById(id);
        if (!optionalApprovalHRServicesAdmin.isPresent()) {
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalHRServicesAdmin not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalHRServicesAdmin approvalHRServicesAdmin = optionalApprovalHRServicesAdmin.get();
        approvalHRServicesAdmin.setExcessOfClaim(approvalHRServicesAdminDetails.getExcessOfClaim());
        approvalHRServicesAdmin.setPenyelesaianBiayaHR(approvalHRServicesAdminDetails.getPenyelesaianBiayaHR());
        approvalHRServicesAdmin.setPenonaktifanKartuElektronik(approvalHRServicesAdminDetails.getPenonaktifanKartuElektronik());
        approvalHRServicesAdmin.setApprovalHRServicesAdminStatus(approvalHRServicesAdminDetails.getApprovalHRServicesAdminStatus());
        approvalHRServicesAdmin.setRemarks(approvalHRServicesAdminDetails.getRemarks());

        if(approvalHRServicesAdmin.getApprovalHRServicesAdminStatus().equals("accept"))
        {
            approvalHRServicesAdmin.setApprovedDate(new Date());
        }

        // Check all approval status
        boolean allApprove = checkingAllApprovalsStatus.doCheck(id);

        if (allApprove) {
            // Create the final form
            checkingAllApprovalsStatus.createFinalApproval(id);
        } else {
            // Log or take other actions if final form is not created
        }

        ApprovalHRServicesAdmin updatedApprovalHRServicesAdmin = repository.save(approvalHRServicesAdmin);
        ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(updatedApprovalHRServicesAdmin, true, "Update succeeded", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


    public ResponseEntity<ApiResponse<ApprovalHRServicesAdmin>> handleFileUpload(ApprovalHRServicesAdmin approvalHRServicesAdmin, MultipartFile file) throws IOException
    {

        String fileName = file.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), path);

        approvalHRServicesAdmin.setDocumentPath(path.toString());
        return save(approvalHRServicesAdmin);
    }
}
