package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalHRLearning;
import com.hrs.checklist_resign.Model.ApprovalSecurityAdministrator;
import com.hrs.checklist_resign.Model.ApprovalTreasury;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalSecurityAdministratorService;
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
@RequestMapping("/api/approval-security-administrator")
public class ApprovalSecurityAdministratorController {

    private final ApprovalSecurityAdministratorService service;

    @Autowired
    public ApprovalSecurityAdministratorController(ApprovalSecurityAdministratorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalSecurityAdministrator>>> getAll() {
        List<ApprovalSecurityAdministrator> approvalSecurityAdministratorList = service.findAll();
        ApiResponse<List<ApprovalSecurityAdministrator>> response = new ApiResponse<>(approvalSecurityAdministratorList, true, "Fetched all records successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/karyawan-resign")
    public ResponseEntity<ApiResponse<ApprovalSecurityAdministrator>> getByNipKaryawanResign() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalSecurityAdministrator> approvalSecurityAdministrator = service.findByNipKaryawanResign(nipKaryawanResign);
        if (approvalSecurityAdministrator.isPresent()) {
            ApiResponse<ApprovalSecurityAdministrator> response = new ApiResponse<>(approvalSecurityAdministrator.get(), true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalSecurityAdministrator> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalSecurityAdministrator>> getById(@PathVariable Long id) {
        Optional<ApprovalSecurityAdministrator> approvalSecurityAdministrator = service.findById(id);
        if (approvalSecurityAdministrator.isPresent()) {
            ApiResponse<ApprovalSecurityAdministrator> response = new ApiResponse<>(approvalSecurityAdministrator.get(), true, "Record found", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalSecurityAdministrator> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalSecurityAdministrator not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApprovalSecurityAdministrator>> create(@RequestBody ApprovalSecurityAdministrator approvalSecurityAdministrator) {
        return service.save(approvalSecurityAdministrator);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalSecurityAdministrator>> update(@PathVariable Long id,
                                                                             @RequestBody ApprovalSecurityAdministrator approvalSecurityAdministrator) {
        return service.update(id, approvalSecurityAdministrator);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalSecurityAdministrator not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        service.deleteById(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Record deleted successfully", HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }


    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ApprovalSecurityAdministrator>> uploadFileSecurityAdministrator(@RequestParam("file") MultipartFile file) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalSecurityAdministrator> approvalOpt = service.findByNipKaryawanResign(nipKaryawanResign);
        ApprovalSecurityAdministrator approval = approvalOpt.get();

        if (approval == null) {
            throw new RuntimeException("ApprovalSecurityAdministrator not found");
        }

        try {
            ResponseEntity<ApiResponse<ApprovalSecurityAdministrator>> updatedApproval = service.handleFileUpload(approval, file);
            ApiResponse<ApprovalSecurityAdministrator> response = new ApiResponse(updatedApproval, true, "File uploaded successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            ApiResponse<ApprovalSecurityAdministrator> response = new ApiResponse<>(null, false, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Optional<ApprovalSecurityAdministrator> approvalSecurityAdministratorOpt = service.findById(id);
        if (!approvalSecurityAdministratorOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ApprovalSecurityAdministrator approvalSecurityAdministrator = approvalSecurityAdministratorOpt.get();
        Path filePath = Paths.get(approvalSecurityAdministrator.getDocumentPath()); // Assuming getFilePath() returns the file path

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
    public ResponseEntity<ApiResponse<Page<ApprovalSecurityAdministrator>>> getAllWithFiltersAndPagination(
            @RequestParam(required = false) String nipKaryawanResign,
            @RequestParam(required = false) String namaKaryawan,
            @RequestParam(required = false) String approvalSecurityAdministratorStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Page<ApprovalSecurityAdministrator> approvalSecurityAdministratorPage = service.findAllWithFiltersAndPagination(
                nipKaryawanResign, namaKaryawan, approvalSecurityAdministratorStatus, page, size, sortBy, sortDirection);

        ApiResponse<Page<ApprovalSecurityAdministrator>> response = new ApiResponse<>(
                approvalSecurityAdministratorPage,
                true,
                "Fetched records successfully",
                HttpStatus.OK.value()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
