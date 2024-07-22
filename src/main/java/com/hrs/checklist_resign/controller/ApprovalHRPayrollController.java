package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalHRLearning;
import com.hrs.checklist_resign.Model.ApprovalHRPayroll;
import com.hrs.checklist_resign.Model.ApprovalTreasury;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.ApprovalHRPayrollService;
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
@RequestMapping("/api/approval-hr-payroll")
public class ApprovalHRPayrollController {

    private final ApprovalHRPayrollService service;

    @Autowired
    public ApprovalHRPayrollController(ApprovalHRPayrollService service) {
        this.service = service;
    }


    @GetMapping("/karyawan-resign")
    public ResponseEntity<ApiResponse<ApprovalHRPayroll>> getByNipKaryawanResign() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalHRPayroll> approvalHRPayroll = service.findByNipKaryawanResign(nipKaryawanResign);
        if (approvalHRPayroll.isPresent()) {
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(approvalHRPayroll.get(), true, "Record fetched successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApprovalHRPayroll>>> getAll() {
        List<ApprovalHRPayroll> approvalHRPayrollList = service.findAll();
        ApiResponse<List<ApprovalHRPayroll>> response = new ApiResponse<>(approvalHRPayrollList, true, "Fetched all records successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalHRPayroll>> getById(@PathVariable Long id) {
        Optional<ApprovalHRPayroll> approvalHRPayroll = service.findById(id);
        if (approvalHRPayroll.isPresent()) {
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(approvalHRPayroll.get(), true, "Record found", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalHRPayroll not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ApprovalHRPayroll>> create(@RequestBody ApprovalHRPayroll approvalHRPayroll) {
        return service.save(approvalHRPayroll);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ApprovalHRPayroll>> update(@PathVariable Long id,
                                                                 @RequestBody ApprovalHRPayroll approvalHRPayroll) {
        return service.update(id, approvalHRPayroll);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        if (!service.findById(id).isPresent()) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalHRPayroll not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        service.deleteById(id);
        ApiResponse<Void> response = new ApiResponse<>(true, "Record deleted successfully", HttpStatus.NO_CONTENT.value());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }


    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ApprovalHRPayroll>> uploadFileHRPayroll(@RequestParam("file") MultipartFile file) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipKaryawanResign = authentication.getName();

        Optional<ApprovalHRPayroll> approvalOpt = service.findByNipKaryawanResign(nipKaryawanResign);
        ApprovalHRPayroll approval = approvalOpt.get();

        if (approval == null) {
            throw new RuntimeException("ApprovalHRPayroll not found");
        }

        try {
            ResponseEntity<ApiResponse<ApprovalHRPayroll>> updatedApproval = service.handleFileUpload(approval, file);
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse(updatedApproval, true, "File uploaded successfully", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            ApiResponse<ApprovalHRPayroll> response = new ApiResponse<>(null, false, "File upload failed", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Optional<ApprovalHRPayroll> approvalHRPayrollOpt = service.findById(id);
        if (!approvalHRPayrollOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        ApprovalHRPayroll approvalHRPayroll = approvalHRPayrollOpt.get();
        Path filePath = Paths.get(approvalHRPayroll.getDocumentPath()); // Assuming getFilePath() returns the file path

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
    public ResponseEntity<ApiResponse<Page<ApprovalHRPayroll>>> getAllWithFiltersAndPagination(
            @RequestParam(required = false) String nipKaryawanResign,
            @RequestParam(required = false) String namaKaryawan,
            @RequestParam(required = false) String approvalHRPayrollStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Page<ApprovalHRPayroll> approvalHRPayrollPage = service.findAllWithFiltersAndPagination(
                nipKaryawanResign, namaKaryawan, approvalHRPayrollStatus, page, size, sortBy, sortDirection);

        ApiResponse<Page<ApprovalHRPayroll>> response = new ApiResponse<>(
                approvalHRPayrollPage,
                true,
                "Fetched records successfully",
                HttpStatus.OK.value()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
