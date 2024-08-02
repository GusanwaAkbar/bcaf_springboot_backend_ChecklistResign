package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.ApprovalHRIR;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.interfaces.ApprovalService;
import com.hrs.checklist_resign.repository.ApprovalHRIRRepository;
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
public class ApprovalHRIRService implements ApprovalService {

    @Autowired
    ApprovalHRIRRepository approvalHRIRRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CheckingAllApprovalsStatus checkingAllApprovalsStatus;

    @Autowired
    private  AsyncEmailService asyncEmailService;

    @Autowired
    private EmailServiceV2 emailServiceV2;

    private final String uploadDir = "/home/gusanwa/AA_Programming/checklist-resign-app/checklist-resign/storage/ApprovalHRIR";

    public Optional<ApprovalHRIR> findByNipKaryawanResign(String nipKaryawanResign) {
        return approvalHRIRRepository.findByNipKaryawanResign(nipKaryawanResign);
    }

    public Optional<ApprovalHRIR> findApprovalHRIRById (Long id)
    {
        //get item by id using Repository
        Optional<ApprovalHRIR> approvalHRIRObj  = approvalHRIRRepository.findById(id);

        return approvalHRIRObj;
    }

    public List<ApprovalHRIR> findAllApprovalHRIR()
    {
        //get all item
        List<ApprovalHRIR> allApprovalHRIR = approvalHRIRRepository.findAll();

        return  allApprovalHRIR;
    }

    public  ApprovalHRIR saveApprovalHRIR(ApprovalHRIR approvalHRIR)
    {
        return approvalHRIRRepository.save(approvalHRIR);
    }

    public void deleteApprovalHRIR(Long id)
    {
        approvalHRIRRepository.deleteById(id);
    }

    public ResponseEntity<?> update(Long id, ApprovalHRIR approvalHRIRDetails) {

        // Start Authentication checking
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<ApprovalAtasan> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        String nipApprover = authentication.getName();

        UserDetail userDetailAtasan =  userDetailsService.findByUsername(nipApprover);
        String namaApprover = userDetailAtasan.getNama();

        // Checking current approval HRIR
        Optional<ApprovalHRIR> approvalHRIRObj = approvalHRIRRepository.findById(id);
        if (approvalHRIRObj == null) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Approval HRIR Not Found", HttpStatus.NOT_FOUND.value(), "Approval HRIR with ID " + id + " is not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        ApprovalHRIR approvalHRIR = approvalHRIRObj.get();

        // Update the approval HRIR
        approvalHRIR.setExitInterview(approvalHRIRDetails.getExitInterview());
        approvalHRIR.setApprovalHRIRStatus(approvalHRIRDetails.getApprovalHRIRStatus());
        approvalHRIR.setRemarks(approvalHRIRDetails.getRemarks());

        boolean isAccept = false;

        if(approvalHRIR.getApprovalHRIRStatus().equals("accept"))
        {
            //Set audit Trail if accept
            approvalHRIR.setApprovedDate(new Date());
            approvalHRIR.setApprovedBy(namaApprover);
            isAccept = true;
        }
        else {
            isAccept = false;
        }

        ApprovalAtasan approvalAtasan = approvalHRIR.getApprovalAtasan();

        //============== SEND EMAIL START ==============

        //Send the email
        //Set User Detail Karyawan
        UserDetail userDetailKaryawan = approvalAtasan.getPengajuanResign().getUserDetailResign();
        String nipKaryawan = approvalAtasan.getNipKaryawanResign();
        String namaKaryawan = approvalAtasan.getNamaKaryawan();

        //Set User Detail Atasan
        UserDetail userDetailAtasanResign = approvalHRIR.getApprovalAtasan().getUserDetailAtasan();
        emailServiceV2.sendDepartmentEmail(userDetailKaryawan, userDetailAtasanResign, nipKaryawan, "General Services", isAccept);

        //============== SEND EMAIL END ==============

        // Save the instance
        ApprovalHRIR savedApprovalHRIR = approvalHRIRRepository.save(approvalHRIR);

        //checking all approval statuslogAction(id, "Final form not created due to pending approvals");
        boolean allApprove = checkingAllApprovalsStatus.doCheck(id, "HRIR");

        if (allApprove) {
            // Create the final form
            checkingAllApprovalsStatus.createFinalApproval(id);
        } else {
            // Log or take other actions if final form is not created

        }

        // Response based on save result
        if (savedApprovalHRIR != null) {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(savedApprovalHRIR, true, "Update Approval HRIR Succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<ApprovalHRIR> response = new ApiResponse<>(false, "Update Approval HRIR Failed", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Update approval HRIR failed");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }



    public ApprovalHRIR handleFileUpload(ApprovalHRIR approvalHRIR, MultipartFile file) throws IOException
    {

        String fileName = file.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), path);

        approvalHRIR.setDocumentPath(path.toString());
        return saveApprovalHRIR(approvalHRIR);
    }

    public Page<ApprovalHRIR> findAllWithFiltersAndPagination(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalHRIRStatus,
            int page,
            int size) {
        Pageable pageable = PageRequest.of(page, size);

        return approvalHRIRRepository.findByNipKaryawanResignContainingIgnoreCaseAndNamaKaryawanContainingIgnoreCaseAndApprovalHRIRStatusIsOrApprovalHRIRStatusIsNull(
                nipKaryawanResign != null ? nipKaryawanResign : "",
                namaKaryawan != null ? namaKaryawan : "",
                approvalHRIRStatus,
                pageable
        );
    }

    public Page<ApprovalHRIR> findAllWithFiltersAndPagination(
            String nipKaryawanResign,
            String namaKaryawan,
            String approvalHRIRStatus,
            int page,
            int size,
            String sortBy,
            String sortDirection) {
        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return approvalHRIRRepository.findWithFilters(
                nipKaryawanResign,
                namaKaryawan,
                approvalHRIRStatus,
                pageable
        );
    }



}
