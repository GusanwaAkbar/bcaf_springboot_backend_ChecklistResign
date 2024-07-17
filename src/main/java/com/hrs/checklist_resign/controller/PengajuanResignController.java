package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.PengajuanResign;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.payload.PengajuanResignDTO;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/resignations")
public class PengajuanResignController {

    @Autowired
    private PengajuanResignService pengajuanResignService;

    @Autowired
    private UserDetailsService userDetailService;

    @Autowired
    private ApprovalAtasanService approvalAtasanService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AsyncEmailService asyncEmailService;

    @Autowired
    private EmailTemplateService emailTemplateService;


    @GetMapping
    public ResponseEntity<ApiResponse<List<PengajuanResign>>> getAllResignations() {
        List<PengajuanResign> resignations = pengajuanResignService.getAllResignations();
        ApiResponse<List<PengajuanResign>> response = new ApiResponse<>(resignations, true, "Fetched all resignations", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user-detail")
    public ResponseEntity<ApiResponse<UserDetail>> getUserDetails() {

        //Start Authentication checking
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<UserDetail> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        //End Authententication checking

        String username = authentication.getName();

        Optional<UserDetail> userDetailOpt = Optional.ofNullable(userDetailService.findByUsername(username));

        //check if user detailnull
        if (!userDetailOpt.isPresent())
        {
            ApiResponse <UserDetail> response = new ApiResponse<>( true, "User Detail Not Found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
        }

        UserDetail userDetail = userDetailOpt.get();

        ApiResponse <UserDetail> response = new ApiResponse<>(userDetail, true, "Resignation created successfully", HttpStatus.CREATED.value());
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getResignationById(@PathVariable Long id) {
        Optional<PengajuanResign> pengajuanResign = pengajuanResignService.getResignationById(id);
        if (pengajuanResign.isPresent()) {
            ApiResponse<PengajuanResign> response = new ApiResponse<>(pengajuanResign.get(), true, "Resignation found", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<PengajuanResign> response = new ApiResponse<>(false, "Resignation not found", HttpStatus.NOT_FOUND.value(), "No resignation found with ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/karyawan-resign")
    public ResponseEntity<ApiResponse<PengajuanResign>> getResignationByNipUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String nipUser = authentication.getName();

        Optional<PengajuanResign> pengajuanResign = pengajuanResignService.getResignationByNipUser(nipUser);
        if(pengajuanResign.isPresent()) {
            ApiResponse<PengajuanResign> response = new ApiResponse<>(pengajuanResign.get(), true, "Resignation found", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        else {
            ApiResponse<PengajuanResign> response = new ApiResponse<>(false, "Resignation not found", HttpStatus.NOT_FOUND.value(), "No resignation found with ID: ");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }



    @DeleteMapping("/deleteResignation")
    public ResponseEntity<String> deletePengajuanResign() {
        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                ApiResponse response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
                return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
            }
            // End Authentication checking

            String nipUser = authentication.getName();
            pengajuanResignService.deletePengajuanResign(nipUser);

            return ResponseEntity.ok("PengajuanResign and related entities deleted successfully");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete PengajuanResign");
        }
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<PengajuanResign>> createResignation(@RequestBody PengajuanResignDTO pengajuanResignDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return buildResponseEntity("User not authenticated", HttpStatus.UNAUTHORIZED, "Authentication required");
        }

        String username = authentication.getName();
        Optional<UserDetail> userDetailOpt = Optional.ofNullable(userDetailService.findByUsername(username));

        if (userDetailOpt.isEmpty()) {
            return buildResponseEntity("User details not found", HttpStatus.NOT_FOUND, "No user details found for username: " + username);
        }

        UserDetail userDetail = userDetailOpt.get();
        PengajuanResign pengajuanResign = buildPengajuanResign(pengajuanResignDTO, username, userDetail);

        PengajuanResign savedPengajuanResign = pengajuanResignService.saveResignation(pengajuanResign);

        UserDetail userDetailAtasan;

        if (!pengajuanResignDTO.getNipAtasan().isEmpty()) {
            userDetailAtasan = userDetailService.findByUsername(pengajuanResignDTO.getNipAtasan());
        } else {
            userDetailAtasan = userDetailService.findByUsername(userDetail.getNipAtasan());
        }

        if (userDetailAtasan == null) {
            return buildResponseEntity("Supervisor details not found", HttpStatus.NOT_FOUND, "No user details found for supervisor with NIP: " + pengajuanResignDTO.getNipAtasan());
        }

        saveApprovalAtasan(savedPengajuanResign, pengajuanResignDTO, userDetailAtasan, username);
        asyncEmailService.sendNotificationsAndEmails(userDetail, userDetailAtasan, username, "Resignation Request Submitted", "Approval Required: New Resignation Request");

        ApiResponse<PengajuanResign> response = new ApiResponse<>(savedPengajuanResign, true, "Resignation created successfully", HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }



    private ResponseEntity<ApiResponse<PengajuanResign>> buildResponseEntity(String message, HttpStatus status, String detail) {
        ApiResponse<PengajuanResign> response = new ApiResponse<>(false, message, status.value(), detail);
        return new ResponseEntity<>(response, status);
    }

    private PengajuanResign buildPengajuanResign(PengajuanResignDTO pengajuanResignDTO, String nipKaryawanResign, UserDetail userDetail) {
        PengajuanResign pengajuanResign = new PengajuanResign();
        pengajuanResign.setNipUser(nipKaryawanResign);
        pengajuanResign.setIsiUntukOrangLain(pengajuanResignDTO.isIsiUntukOrangLain());
        pengajuanResign.setTanggalPembuatanAkunHRIS(pengajuanResignDTO.getTanggalPembuatanAkunHRIS());
        pengajuanResign.setTanggalBerakhirBekerja(pengajuanResignDTO.getTanggalBerakhirBekerja());
        pengajuanResign.setUserDetailResign(userDetail);
        pengajuanResign.setNipAtasan(pengajuanResignDTO.getNipAtasan());
        pengajuanResign.setEmailAtasan(pengajuanResignDTO.getEmailAtasan());
        return pengajuanResign;
    }

    private void saveApprovalAtasan(PengajuanResign savedPengajuanResign, PengajuanResignDTO pengajuanResignDTO, UserDetail userDetailAtasan, String nipKaryawanResign) {
        ApprovalAtasan approvalAtasanObj = new ApprovalAtasan();
        approvalAtasanObj.setId(savedPengajuanResign.getId());
        approvalAtasanObj.setNipKaryawanResign(nipKaryawanResign);
        approvalAtasanObj.setNipAtasan(pengajuanResignDTO.getNipAtasan());
        approvalAtasanObj.setEmailAtasan(pengajuanResignDTO.getEmailAtasan());
        approvalAtasanObj.setUserDetailAtasan(userDetailAtasan);
        approvalAtasanObj.setPengajuanResign(savedPengajuanResign);
        approvalAtasanService.saveApproval(approvalAtasanObj);
    }




}
