package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.PengajuanResign;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.payload.PengajuanResignDTO;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    private EmailService emailService;


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


    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApiResponse<PengajuanResign>> createResignation(@RequestBody PengajuanResignDTO pengajuanResignDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<PengajuanResign> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String username = authentication.getName();
        Optional<UserDetail> userDetailOpt = Optional.ofNullable(userDetailService.findByUsername(username));

        if (userDetailOpt.isPresent()) {
            UserDetail userDetail = userDetailOpt.get();

            PengajuanResign pengajuanResign = new PengajuanResign();
            pengajuanResign.setIsiUntukOrangLain(pengajuanResignDTO.isIsiUntukOrangLain());
            pengajuanResign.setTanggalPembuatanAkunHRIS(pengajuanResignDTO.getTanggalPembuatanAkunHRIS());
            pengajuanResign.setTanggalBerakhirBekerja(pengajuanResignDTO.getTanggalBerakhirBekerja());
            pengajuanResign.setUserDetailResign(userDetail);

            String nipAtasan = pengajuanResignDTO.getNipAtasan();
            String emailAtasan = pengajuanResignDTO.getEmailAtasan();

            pengajuanResign.setNipAtasan(nipAtasan);
            pengajuanResign.setEmailAtasan(emailAtasan);

            userDetail.setNomerWA(pengajuanResignDTO.getNomerWA());
            userDetail.setEmailAktif(pengajuanResignDTO.getEmailAktif());

            PengajuanResign savedPengajuanResign = pengajuanResignService.saveResignation(pengajuanResign);
            userDetailService.saveUserDetails(userDetail);

            UserDetail userDetailAtasan = userDetailService.findByUsername(nipAtasan);

            ApprovalAtasan approvalAtasanObj = new ApprovalAtasan();
            approvalAtasanObj.setNipAtasan(nipAtasan);
            approvalAtasanObj.setEmailAtasan(emailAtasan);
            approvalAtasanObj.setUserDetailAtasan(userDetailAtasan);
            approvalAtasanObj.setPengajuanResign(savedPengajuanResign);

            approvalAtasanService.saveApproval(approvalAtasanObj);

            Set<UserDetail> recipients = new HashSet<>();
            recipients.add(userDetail);
            recipients.add(userDetailAtasan);

            notificationService.sendNotification("Resignation request submitted", userDetail, recipients);

            emailService.sendEmail(userDetail.getEmailAktif(), "Resignation Request Submitted", "Your resignation request has been submitted.");
            emailService.sendEmail(userDetailAtasan.getEmail(), "Approval Required: Resignation Request", "Please approve the resignation request.");

            ApiResponse<PengajuanResign> response = new ApiResponse<>(savedPengajuanResign, true, "Resignation created successfully", HttpStatus.CREATED.value());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            ApiResponse<PengajuanResign> response = new ApiResponse<>(false, "User details not found", HttpStatus.NOT_FOUND.value(), "No user details found for username: " + username);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }




//    @PutMapping("/{id}")
//    public ResponseEntity<ApiResponse<PengajuanResign>> updateResignation(@PathVariable Long id, @RequestBody PengajuanResign pengajuanResignDetails) {
//        Optional<PengajuanResign> optionalPengajuanResign = pengajuanResignService.getResignationById(id);
//        if (optionalPengajuanResign.isPresent()) {
//            PengajuanResign pengajuanResign = optionalPengajuanResign.get();
//            pengajuanResign.setIsiUntukOrangLain(pengajuanResignDetails.isIsiUntukOrangLain());
//            pengajuanResign.setTanggalPembuatanAkunHRIS(pengajuanResignDetails.getTanggalPembuatanAkunHRIS());
//            pengajuanResign.setTanggalBerakhirBekerja(pengajuanResignDetails.getTanggalBerakhirBekerja());
//
//            // Get the username of the logged-in user
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String username = authentication.getName();
//
//            // Fetch the UserDetail using the username
//            Optional<UserDetail> userDetail = Optional.ofNullable(userDetailService.findByUsername(username));
//            if (userDetail.isPresent()) {
//                pengajuanResign.setUserDetail(userDetail.get());
//                PengajuanResign updatedPengajuanResign = pengajuanResignService.saveResignation(pengajuanResign);
//                ApiResponse<PengajuanResign> response = new ApiResponse<>(updatedPengajuanResign, true, "Resignation updated successfully", HttpStatus.OK.value());
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            } else {
//                ApiResponse<PengajuanResign> response = new ApiResponse<>(false, "User details not found", HttpStatus.NOT_FOUND.value(), "No user details found for username: " + username);
//                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//            }
//        } else {
//            ApiResponse<PengajuanResign> response = new ApiResponse<>(false, "Resignation not found", HttpStatus.NOT_FOUND.value(), "No resignation found with ID: " + id);
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
//    }

    @DeleteMapping("/deleteResignation")
    public ResponseEntity<?> deleteResignation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<PengajuanResign> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String loggedInUsername = authentication.getName();
        Optional<UserDetail> userDetailOpt = Optional.ofNullable(userDetailService.findByUsername(loggedInUsername));

        if (userDetailOpt.isPresent()) {
            UserDetail userDetail = userDetailOpt.get();
            Optional<PengajuanResign> resignationOpt = pengajuanResignService.getResignationByUserDetail(userDetail);

            if (resignationOpt.isPresent()) {
                PengajuanResign resignation = resignationOpt.get();
                try {
                    pengajuanResignService.deleteResignation(resignation.getId());

                    ApiResponse<Void> response = new ApiResponse<>(null, true, "Resignation deleted successfully", HttpStatus.OK.value());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } catch (Exception e) {
                    ApiResponse<Void> response = new ApiResponse<>(false, "Failed to delete resignation", HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
                    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                ApiResponse<Void> response = new ApiResponse<>(false, "Resignation not found", HttpStatus.NOT_FOUND.value(), "No resignation found for the logged-in user");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } else {
            ApiResponse<Void> response = new ApiResponse<>(false, "User details not found", HttpStatus.NOT_FOUND.value(), "No user details found for the logged-in user");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}
