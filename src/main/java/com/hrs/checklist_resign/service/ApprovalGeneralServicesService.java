package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.repository.ApprovalGeneralServicesRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class ApprovalGeneralServicesService {

    private final ApprovalGeneralServicesRepository repository;

    @Autowired
    private AsyncEmailService asyncEmailService;

    @Autowired
    private UserDetailsService userDetailsService;

    private final String uploadDir = "/home/gusanwa/AA_Programming/checklist-resign-app/checklist-resign/storage/ApprovalGeneralServices";

    @Autowired
    private CheckingAllApprovalsStatus checkingAllApprovalsStatus;

    @Autowired
    public ApprovalGeneralServicesService(ApprovalGeneralServicesRepository repository) {
        this.repository = repository;
    }

    public Optional<ApprovalGeneralServices> findByNipKaryawanResign(String nipKaryawanResign) {
        return repository.findByNipKaryawanResign(nipKaryawanResign);
    }

    public List<ApprovalGeneralServices> findAll() {
        return repository.findAll();
    }

    public Optional<ApprovalGeneralServices> findById(Long id) {
        return repository.findById(id);
    }

    public ResponseEntity<ApiResponse<ApprovalGeneralServices>> save(ApprovalGeneralServices approvalGeneralServices) {
        ApprovalGeneralServices savedApprovalGeneralServices = repository.save(approvalGeneralServices);
        if (savedApprovalGeneralServices != null) {
            ApiResponse<ApprovalGeneralServices> response = new ApiResponse<>(savedApprovalGeneralServices, true, "Operation succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalGeneralServices> response = new ApiResponse<>(false, "Operation failed", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Operation failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ApiResponse<ApprovalGeneralServices>> update(Long id, ApprovalGeneralServices approvalGeneralServicesDetails) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<ApprovalGeneralServices> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        String nipApprover = authentication.getName();

        UserDetail userDetailAtasan =  userDetailsService.findByUsername(nipApprover);
        String namaApprover = userDetailAtasan.getNama();


        Optional<ApprovalGeneralServices> optionalApprovalGeneralServices = repository.findById(id);
        if (!optionalApprovalGeneralServices.isPresent()) {
            ApiResponse<ApprovalGeneralServices> response = new ApiResponse<>(false, "Record not found", HttpStatus.NOT_FOUND.value(), "ApprovalGeneralServices not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalGeneralServices approvalGeneralServices = optionalApprovalGeneralServices.get();
        approvalGeneralServices.setPenutupanPin(approvalGeneralServicesDetails.getPenutupanPin());
        approvalGeneralServices.setPengembalianKendaraanDinas(approvalGeneralServicesDetails.getPengembalianKendaraanDinas());
        approvalGeneralServices.setInventarisKantor(approvalGeneralServicesDetails.getInventarisKantor());
        approvalGeneralServices.setPengembalianAktiva(approvalGeneralServicesDetails.getPengembalianAktiva());
        approvalGeneralServices.setPengembalianKendaraanUMK3(approvalGeneralServicesDetails.getPengembalianKendaraanUMK3());
        approvalGeneralServices.setApprovalGeneralServicesStatus(approvalGeneralServicesDetails.getApprovalGeneralServicesStatus());
        approvalGeneralServices.setRemarks(approvalGeneralServicesDetails.getRemarks());


        if (approvalGeneralServices.getApprovalGeneralServicesStatus().equals("accept"))
        {
            //Set audit Trail
            approvalGeneralServices.setApprovedDate(new Date());
            approvalGeneralServices.setApprovedBy(namaApprover);



            //Send the email
            UserDetail userDetailKaryawan = approvalGeneralServices.getApprovalAtasan().getPengajuanResign().getUserDetailResign();
            String nipKaryawan = approvalGeneralServices.getNipKaryawanResign();

            asyncEmailService.sendNotificationsAndEmails(userDetailKaryawan, userDetailAtasan, nipKaryawan, "Your Resignation has been approved by General Services Departement", "General Services Departement has been approve the Resignation");


        }

        //checking all approval statuslogAction(id, "Final form not created due to pending approvals");
        boolean allApprove = checkingAllApprovalsStatus.doCheck(id, "GENERALSERVICES");

        if (allApprove) {
            // Create the final form
            checkingAllApprovalsStatus.createFinalApproval(id);
        } else {
            // Log or take other actions if final form is not created

            System.out.println("");

        }

        ApprovalGeneralServices updatedApprovalGeneralServices = repository.save(approvalGeneralServices);
        ApiResponse<ApprovalGeneralServices> response = new ApiResponse<>(updatedApprovalGeneralServices, true, "Update succeeded", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public ApprovalGeneralServices handleFileUpload(ApprovalGeneralServices approvalGeneralServices, MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), path);

        approvalGeneralServices.setDocumentPath(path.toString());
        return repository.save(approvalGeneralServices);
    }

    public Page<ApprovalGeneralServices> findAllWithFiltersAndPagination(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalGeneralServicesStatus,
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);

        return repository.findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalGeneralServicesStatusIsOrApprovalGeneralServicesStatusIsNull(
                nipKaryawanResign != null ? nipKaryawanResign : "",
                namaKaryawan != null ? namaKaryawan : "",
                approvalGeneralServicesStatus,
                pageable
        );
    }

    public Page<ApprovalGeneralServices> findAllWithFiltersAndPagination(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalGeneralServicesStatus,
            int page,
            int size,
            String sortBy,
            String sortDirection) {
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return repository.findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalGeneralServicesStatusContainingIgnoreCase(
                nipKaryawanResign != null ? nipKaryawanResign : "",
                namaKaryawan != null ? namaKaryawan : "",
                approvalGeneralServicesStatus != null ? approvalGeneralServicesStatus : "",
                pageable
        );
    }


}
