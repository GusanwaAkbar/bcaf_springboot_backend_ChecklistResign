package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.*;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalHRIRService;
import com.hrs.checklist_resign.service.ApprovalHRTalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @GetMapping("/karyawan-resign")
    public ResponseEntity<ApiResponse<ApprovalHRIR>> getByNipKaryawanResign() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalHRIR> approvalHRIR = approvalHRIRService.findByNipKaryawanResign(nipKaryawanResign);
        if (approvalHRIR.isPresent()) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(approvalHRIR.get(), true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ApprovalHRIR>> uploadFileHRIR(@RequestParam("file") MultipartFile file) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalHRIR> approvalOpt = approvalHRIRService.findByNipKaryawanResign(nipKaryawanResign);
        ApprovalHRIR approval = approvalOpt.get();

        if (approval == null) {
            throw new RuntimeException("ApprovalHRIR not found");
        }

        try {
            ApprovalHRIR updatedApproval = approvalHRIRService.handleFileUpload(approval, file);
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(updatedApproval, true, "File uploaded successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(null, false, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Optional<ApprovalHRIR> approvalHRIROpt = approvalHRIRService.findApprovalHRIRById(id);
        if (!approvalHRIROpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ApprovalHRIR approvalHRIR = approvalHRIROpt.get();
        Path filePath = Paths.get(approvalHRIR.getDocumentPath()); // Assuming getFilePath() returns the file path

        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                String mimeType = Files.probeContentType(filePath);
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(mimeType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                throw new RuntimeException("File not found or not readable");
            }
        } catch (Exception e) {
            throw new RuntimeException("File download error", e);
        }
    }



}
