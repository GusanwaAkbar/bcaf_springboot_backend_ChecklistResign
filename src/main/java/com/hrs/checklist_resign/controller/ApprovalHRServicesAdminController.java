package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalHRLearning;
import com.hrs.checklist_resign.Model.ApprovalHRServicesAdmin;
import com.hrs.checklist_resign.Model.ApprovalTreasury;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalHRIRService;
import com.hrs.checklist_resign.service.ApprovalHRServicesAdminService;
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
@RequestMapping("/api/approval-hr-services-admin")
public class ApprovalHRServicesAdminController {

    private final ApprovalHRServicesAdminService service;

    @Autowired
    public ApprovalHRServicesAdminController(ApprovalHRServicesAdminService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalHRServicesAdmin>>> getAll() {
        List<ApprovalHRServicesAdmin> approvalHRServicesAdminList = service.findAll();
        ApiResponse<List<ApprovalHRServicesAdmin>> response = new ApiResponse<>(approvalHRServicesAdminList, true, "Fetched all records successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/karyawan-resign")
    public ResponseEntity<ApiResponse<ApprovalHRServicesAdmin>> getByNipKaryawanResign() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalHRServicesAdmin> approvalHRServicesAdmin = service.findByNipKaryawanResign(nipKaryawanResign);
        if (approvalHRServicesAdmin.isPresent()) {
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(approvalHRServicesAdmin.get(), true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/admin/{nipKaryawan}")
    public ResponseEntity<ApiResponse<ApprovalHRServicesAdmin>> getByNipKaryawanResignAdmin(@PathVariable String nipKaryawan) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = nipKaryawan;

        Optional<ApprovalHRServicesAdmin> approvalHRServicesAdmin = service.findByNipKaryawanResign(nipKaryawanResign);
        if (approvalHRServicesAdmin.isPresent()) {
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(approvalHRServicesAdmin.get(), true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalHRServicesAdmin>> getById(@PathVariable Long id) {
        Optional<ApprovalHRServicesAdmin> approvalHRServicesAdmin = service.findById(id);
        if (approvalHRServicesAdmin.isPresent()) {
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(approvalHRServicesAdmin.get(), true, "Record found", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalHRServicesAdmin not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApprovalHRServicesAdmin>> create(@RequestBody ApprovalHRServicesAdmin approvalHRServicesAdmin) {
        return service.save(approvalHRServicesAdmin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalHRServicesAdmin>> update(@PathVariable Long id,
                                                                       @RequestBody ApprovalHRServicesAdmin approvalHRServicesAdmin) {
        return service.update(id, approvalHRServicesAdmin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalHRServicesAdmin not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        service.deleteById(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Record deleted successfully", HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ApprovalHRServicesAdmin>> uploadFileHRServicesAdmin(@RequestParam("file") MultipartFile file) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalHRServicesAdmin> approvalOpt = service.findByNipKaryawanResign(nipKaryawanResign);
        ApprovalHRServicesAdmin approval = approvalOpt.get();

        if (approval == null) {
            throw new RuntimeException("ApprovalHRServicesAdmin not found");
        }

        try {
            ResponseEntity<ApiResponse<ApprovalHRServicesAdmin>> updatedApproval = service.handleFileUpload(approval, file);
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse(updatedApproval, true, "File uploaded successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            ApiResponse<ApprovalHRServicesAdmin> response = new ApiResponse<>(null, false, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Optional<ApprovalHRServicesAdmin> approvalHRServicesAdminOpt = service.findById(id);
        if (!approvalHRServicesAdminOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ApprovalHRServicesAdmin approvalHRServicesAdmin = approvalHRServicesAdminOpt.get();
        Path filePath = Paths.get(approvalHRServicesAdmin.getDocumentPath()); // Assuming getFilePath() returns the file path

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

    @GetMapping("/V2")
    public ResponseEntity<ApiResponse<Page<ApprovalHRServicesAdmin>>> getAllWithFiltersAndPagination(
            @RequestParam(required = false) String nipKaryawanResign,
            @RequestParam(required = false) String namaKaryawan,
            @RequestParam(required = false) String approvalHRServicesAdminStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Page<ApprovalHRServicesAdmin> approvalHRServicesAdminPage = service.findAllWithFiltersAndPagination(
                nipKaryawanResign, namaKaryawan, approvalHRServicesAdminStatus, page, size, sortBy, sortDirection);

        ApiResponse<Page<ApprovalHRServicesAdmin>> response = new ApiResponse<>(
                approvalHRServicesAdminPage,
                true,
                "Fetched records successfully",
                HttpStatus.OK.value()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}
