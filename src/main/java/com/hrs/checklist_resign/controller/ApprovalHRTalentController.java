package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.*;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalHRIRService;
import com.hrs.checklist_resign.service.ApprovalHRTalentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
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

    @GetMapping("/karyawan-resign")
    public ResponseEntity<ApiResponse<ApprovalHRTalent>> getByNipKaryawanResign() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalHRTalent> approvalHRTalent = approvalHRTalentService.findByNipKaryawanResign(nipKaryawanResign);
        if (approvalHRTalent.isPresent()) {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(approvalHRTalent.get(), true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
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

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ApprovalHRTalent>> uploadFileHRTalent(@RequestParam("file") MultipartFile file) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalHRTalent> approvalOpt = approvalHRTalentService.findByNipKaryawanResign(nipKaryawanResign);
        ApprovalHRTalent approval = approvalOpt.get();

        if (approval == null) {
            throw new RuntimeException("ApprovalHRTalent not found");
        }

        try {
            ApprovalHRTalent updatedApproval = approvalHRTalentService.handleFileUpload(approval, file);
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(updatedApproval, true, "File uploaded successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            ApiResponse<ApprovalHRTalent> response = new ApiResponse<>(null, false, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Optional<ApprovalHRTalent> approvalHRTalentOpt = approvalHRTalentService.findById(id);
        if (!approvalHRTalentOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ApprovalHRTalent approvalHRTalent = approvalHRTalentOpt.get();
        Path filePath = Paths.get(approvalHRTalent.getDocumentPath()); // Assuming getFilePath() returns the file path

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

    @GetMapping("/V2/approval-hrtalent")
    public ResponseEntity<ApiResponse<Page<ApprovalHRTalent>>> getAllWithFiltersAndPagination(
            @RequestParam(required = false) String nipKaryawanResign,
            @RequestParam(required = false) String namaKaryawan,
            @RequestParam(required = false) String approvalHRTalentStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ApprovalHRTalent> approvalHRTalentPage = approvalHRTalentService.findAllWithFiltersAndPagination(
                nipKaryawanResign, namaKaryawan, approvalHRTalentStatus, page, size);

        ApiResponse<Page<ApprovalHRTalent>> response = new ApiResponse<>(
                approvalHRTalentPage,
                true,
                "Fetched records successfully",
                HttpStatus.OK.value()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}

