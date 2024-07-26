package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.PengajuanResign;
import com.hrs.checklist_resign.Model.User;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.dto.ResignationProgressDTO;
import com.hrs.checklist_resign.dto.ResignationProgressDetailDTO;
import com.hrs.checklist_resign.payload.PengajuanResignDTO;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;


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

    @Autowired
    private UserService userService;




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

        String nipAtasan = pengajuanResignDTO.getNipAtasan();
        UserDetail userDetailAtasan = fetchOrCreateUserDetail(nipAtasan, userDetail.getNipAtasan());

        if (userDetailAtasan == null) {
            return buildResponseEntity("Supervisor details not found", HttpStatus.NOT_FOUND, "No user details found for supervisor with NIP: " + nipAtasan);
        }


        PengajuanResign pengajuanResign = buildPengajuanResign(pengajuanResignDTO, username, userDetail, userDetailAtasan);
        PengajuanResign savedPengajuanResign = pengajuanResignService.saveResignation(pengajuanResign);



        saveApprovalAtasan(savedPengajuanResign, pengajuanResignDTO, userDetailAtasan, userDetail);
        //asyncEmailService.sendNotificationsAndEmails(userDetail, userDetailAtasan, username, "Resignation Request Submitted", "Approval Required: New Resignation Request");

        ApiResponse<PengajuanResign> response = new ApiResponse<>(savedPengajuanResign, true, "Resignation created successfully", HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private UserDetail fetchOrCreateUserDetail(String nipAtasanFromDTO, String nipAtasanFromUserDetail) {
        String nipAtasan = nipAtasanFromDTO != null && !nipAtasanFromDTO.isEmpty() ? nipAtasanFromDTO : nipAtasanFromUserDetail;

        if (nipAtasan == null || nipAtasan.isEmpty()) {
            return null;
        }

        UserDetail userDetailAtasan = userDetailService.findByUsername(nipAtasan);

        if (userDetailAtasan == null) {
            List<UserDetail> userDetailsList = userDetailService.fetchUserDetailsByUsername(nipAtasan);

            if (!userDetailsList.isEmpty()) {
                userDetailAtasan = userDetailsList.get(0);

                Optional<User> atasanUserOpt = userService.findByUsername(nipAtasan);

                User userAtasan;
                if (atasanUserOpt.isEmpty()) {
                    userAtasan = new User();
                    userAtasan.setUsername(nipAtasan);
                    userAtasan.setPassword(nipAtasan); // Consider using a more secure default password
                    userAtasan.setRoles("USER");
                    userService.saveUser(userAtasan);
                } else {
                    userAtasan = atasanUserOpt.get();
                }

                userDetailAtasan.setUser(userAtasan);
                userDetailService.saveUserDetails(userDetailAtasan);
            } else {
                return null; // If the NIP Atasan is not found in HRIS
            }
        }

        return userDetailAtasan;
    }





    private ResponseEntity<ApiResponse<PengajuanResign>> buildResponseEntity(String message, HttpStatus status, String detail) {
        ApiResponse<PengajuanResign> response = new ApiResponse<>(false, message, status.value(), detail);
        return new ResponseEntity<>(response, status);
    }

    private PengajuanResign buildPengajuanResign(PengajuanResignDTO pengajuanResignDTO, String nipKaryawanResign, UserDetail userDetail, UserDetail userDetailAtasan) {
        PengajuanResign pengajuanResign = new PengajuanResign();
        pengajuanResign.setNipUser(nipKaryawanResign);
        pengajuanResign.setNamaKaryawan(userDetail.getNama());
        pengajuanResign.setNamaAtasan(userDetailAtasan.getNama());
        pengajuanResign.setIsiUntukOrangLain(pengajuanResignDTO.isIsiUntukOrangLain());
        pengajuanResign.setTanggalPembuatanAkunHRIS(pengajuanResignDTO.getTanggalPembuatanAkunHRIS());
        pengajuanResign.setTanggalBerakhirBekerja(pengajuanResignDTO.getTanggalBerakhirBekerja());
        pengajuanResign.setUserDetailResign(userDetail);
        pengajuanResign.setNipAtasan(pengajuanResignDTO.getNipAtasan());
        pengajuanResign.setEmailAtasan(pengajuanResignDTO.getEmailAtasan());
        return pengajuanResign;
    }

    private void saveApprovalAtasan(PengajuanResign savedPengajuanResign, PengajuanResignDTO pengajuanResignDTO, UserDetail userDetailAtasan, UserDetail userDetailKaryawan) {
        ApprovalAtasan approvalAtasanObj = new ApprovalAtasan();
        approvalAtasanObj.setNipKaryawanResign(userDetailKaryawan.getUserUsername());
        approvalAtasanObj.setNamaKaryawan(userDetailKaryawan.getNama());
        approvalAtasanObj.setNipAtasan(pengajuanResignDTO.getNipAtasan());
        approvalAtasanObj.setNamaAtasan(userDetailAtasan.getNama());
        approvalAtasanObj.setEmailAtasan(pengajuanResignDTO.getEmailAtasan());
        approvalAtasanObj.setUserDetailAtasan(userDetailAtasan);
        approvalAtasanObj.setPengajuanResign(savedPengajuanResign);
        approvalAtasanService.saveApproval(approvalAtasanObj);
    }


    @GetMapping("/V2")
    public ResponseEntity<ApiResponse<Page<PengajuanResign>>> getAllResignations(
            @RequestParam(required = false) String nipKaryawan,
            @RequestParam(required = false) String namaKaryawan,
            @RequestParam(required = false) Integer filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDirection) {

        Page<PengajuanResign> resignationPage = pengajuanResignService.findAllWithFiltersAndPagination(
                nipKaryawan,
                namaKaryawan,
                filter,
                page,
                size,
                sortBy,
                sortDirection
        );

        if (resignationPage.isEmpty()) {
            ApiResponse<Page<PengajuanResign>> response = new ApiResponse<>(resignationPage, true, "No data found", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        ApiResponse<Page<PengajuanResign>> response = new ApiResponse<>(resignationPage, true, "Resignations successfully fetched", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/admin")
    public ResponseEntity<ApiResponse<Page<ResignationProgressDTO>>> getResignationProgress(
            @RequestParam(required = false) String nipUser,
            @RequestParam(required = false) String namaKaryawan,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<Page<ResignationProgressDTO>> response = new ApiResponse<>(
                    false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String userNip = authentication.getName();
        String userRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");

        // Additional logging to debug
        System.out.println("=======================================");
        System.out.println("User NIP: " + userNip);
        System.out.println("User Role: " + userRole);
        System.out.println("Authorities: " + authentication.getAuthorities());

        Page<ResignationProgressDTO> progressPage;

        if ("ROLE_ADMIN".equals(userRole)) {
            // Admin logic: retrieve full data
            progressPage = pengajuanResignService.getResignationProgress(
                    nipUser, namaKaryawan, page, size, sortBy, sortDirection);
        } else if ("ROLE_USER".equals(userRole)) {
            // User logic: retrieve resignations where nipAtasan = userNip
            progressPage = pengajuanResignService.getResignationProgressByNipAtasan(
                    userNip, namaKaryawan, page, size, sortBy, sortDirection);
        } else {
            ApiResponse<Page<ResignationProgressDTO>> response = new ApiResponse<>(
                    false, "Invalid role", HttpStatus.FORBIDDEN.value(), "Access denied");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }

        if (progressPage.hasContent()) {
            ApiResponse<Page<ResignationProgressDTO>> response = new ApiResponse<>(
                    progressPage, true, "Fetch succeeded", HttpStatus.OK.value());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<Page<ResignationProgressDTO>> response = new ApiResponse<>(
                    false, "No records found", HttpStatus.NOT_FOUND.value(), "Resignation progress not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/admin/{id}")
    public ResponseEntity<ApiResponse<ResignationProgressDTO>> getResignationProgressById(@PathVariable Long id) {
        ResignationProgressDTO resignationProgressDTO = pengajuanResignService.getResignationProgressById(id);
        if (resignationProgressDTO != null) {
            ApiResponse<ResignationProgressDTO> response = new ApiResponse<>(
                    resignationProgressDTO, true, "Fetch Succeeded", HttpStatus.OK.value());


            return  new ResponseEntity<>(response, HttpStatus.OK);
        } else {

            ApiResponse<ResignationProgressDTO> response = new ApiResponse<>(
                    false, "Resignation Not Found", HttpStatus.NOT_FOUND.value());


            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }




}
