package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.Model.ApprovalTreasury;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalGeneralServicesService;
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
@RequestMapping("/api/approval-general-services")
public class ApprovalGeneralServicesController {

    private final ApprovalGeneralServicesService service;

    @Autowired
    public ApprovalGeneralServicesController(ApprovalGeneralServicesService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalGeneralServices>>> getAll() {
        List<ApprovalGeneralServices> approvalGeneralServicesList = service.findAll();
        ApiResponse<List<ApprovalGeneralServices>> response = new ApiResponse<>(approvalGeneralServicesList, true, "Fetched all records successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalGeneralServices>> getById(@PathVariable Long id) {
        Optional<ApprovalGeneralServices> approvalGeneralServices = service.findById(id);
        if (approvalGeneralServices.isPresent()) {
            ApiResponse<ApprovalGeneralServices> response = new ApiResponse<>(approvalGeneralServices.get(), true, "Record found", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalGeneralServices> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/karyawan-resign")
    public ResponseEntity<ApiResponse<ApprovalGeneralServices>> getByNipKaryawanResign() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalGeneralServices> approvalGeneralServices = service.findByNipKaryawanResign(nipKaryawanResign);
        if (approvalGeneralServices.isPresent()) {
            ApiResponse<ApprovalGeneralServices> response = new ApiResponse<>(approvalGeneralServices.get(), true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalGeneralServices> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApprovalGeneralServices>> create(@RequestBody ApprovalGeneralServices approvalGeneralServices) {
        return service.save(approvalGeneralServices);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalGeneralServices>> update(@PathVariable Long id,
                                                                       @RequestBody ApprovalGeneralServices approvalGeneralServices) {
        return service.update(id, approvalGeneralServices);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        service.deleteById(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Record deleted successfully", HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ApprovalGeneralServices>> uploadFileGeneralServices(@RequestParam("file") MultipartFile file) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalGeneralServices> approvalOpt = service.findByNipKaryawanResign(nipKaryawanResign);
        ApprovalGeneralServices approval = approvalOpt.get();

        if (approval == null) {
            throw new RuntimeException("ApprovalGeneralServices not found");
        }

        try {
            ApprovalGeneralServices updatedApproval = service.handleFileUpload(approval, file);
            ApiResponse<ApprovalGeneralServices> response = new ApiResponse(updatedApproval, true, "File uploaded successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            ApiResponse<ApprovalGeneralServices> response = new ApiResponse<>(null, false, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Optional<ApprovalGeneralServices> approvalGeneralServicesOpt = service.findById(id);
        if (!approvalGeneralServicesOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ApprovalGeneralServices approvalGeneralServices = approvalGeneralServicesOpt.get();
        Path filePath = Paths.get(approvalGeneralServices.getDocumentPath()); // Assuming getFilePath() returns the file path

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
