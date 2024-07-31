package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.Model.PengajuanResign;
import com.hrs.checklist_resign.Model.User;
import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.dto.PengajuanResignAdminDTO;
import com.hrs.checklist_resign.dto.ResignationProgressDTO;
import com.hrs.checklist_resign.dto.ResignationProgressDetailDTO;
import com.hrs.checklist_resign.dto.UserDetailsResponseDTO;
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



    @GetMapping("/user-detail/V2")
    public ResponseEntity<ApiResponse<UserDetailsResponseDTO>> getUserDetailsV2() {

        // Start Authentication checking
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        String username = authentication.getName();

        Optional<UserDetail> userDetailOpt = Optional.ofNullable(userDetailService.findByUsername(username));

        // Check if user detail is null
        if (!userDetailOpt.isPresent()) {
            ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(true, "User Detail Not Found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        UserDetail userDetail = userDetailOpt.get();

        // Fetch or create user detail for atasan
        String nipAtasan = userDetail.getNipAtasan();
        UserDetail userDetailAtasan = fetchOrCreateUserDetail(nipAtasan, null);

        if (userDetailAtasan == null) {
            ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(true, "Supervisor details not found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Create a response object that includes both userDetail and userDetailAtasan
        UserDetailsResponseDTO userDetailResponse = new UserDetailsResponseDTO(userDetail, userDetailAtasan);

        ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(userDetailResponse, true, "User details retrieved successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user-detail-atasan2")
    public ResponseEntity<ApiResponse<UserDetailsResponseDTO>> getUserDetailsAtasan2() {

        // Start Authentication checking
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
        // End Authentication checking

        String username = authentication.getName();

        Optional<UserDetail> userDetailOpt = Optional.ofNullable(userDetailService.findByUsername(username));

        // Check if user detail is null
        if (!userDetailOpt.isPresent()) {
            ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(true, "User Detail Not Found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        UserDetail userDetail = userDetailOpt.get();

        // Fetch or create user detail for atasan
        String nipAtasan = userDetail.getNipAtasan();

        UserDetail userDetailAtasan = fetchOrCreateUserDetail(nipAtasan, null);

        if (userDetailAtasan == null) {
            ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(true, "Supervisor details not found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        String nipAtasan2 = userDetailAtasan.getNipAtasan();

        UserDetail userDetailAtasan2 = fetchOrCreateUserDetail(nipAtasan2, null);

        if (userDetailAtasan2 == null) {
            ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(true, "Supervisor's supervisor details not found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Create a response object that includes both userDetail and userDetailAtasan2
        UserDetailsResponseDTO userDetailResponse = new UserDetailsResponseDTO(userDetail, userDetailAtasan2);

        ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(userDetailResponse, true, "User details retrieved successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping("/user-detail-atasan2/{nipKaryawan}")
    public ResponseEntity<ApiResponse<UserDetailsResponseDTO>> getUserDetailsAtasan2(@PathVariable String nipKaryawan) {



        String username = nipKaryawan;

        Optional<UserDetail> userDetailOpt = Optional.ofNullable(userDetailService.findByUsername(username));

        // Check if user detail is null
        if (!userDetailOpt.isPresent()) {
            ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(true, "User Detail Not Found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        UserDetail userDetail = userDetailOpt.get();

        // Fetch or create user detail for atasan
        String nipAtasan = userDetail.getNipAtasan();

        UserDetail userDetailAtasan = fetchOrCreateUserDetail(nipAtasan, null);

        if (userDetailAtasan == null) {
            ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(true, "Supervisor details not found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        String nipAtasan2 = userDetailAtasan.getNipAtasan();

        UserDetail userDetailAtasan2 = fetchOrCreateUserDetail(nipAtasan2, null);

        if (userDetailAtasan2 == null) {
            ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(true, "Supervisor's supervisor details not found", HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Create a response object that includes both userDetail and userDetailAtasan2
        UserDetailsResponseDTO userDetailResponse = new UserDetailsResponseDTO(userDetail, userDetailAtasan2);

        ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(userDetailResponse, true, "User details retrieved successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
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
            return buildResponseEntity("User details USER LOGIN not found in HRIS", HttpStatus.NOT_FOUND, "No user details found for username: " + username);
        }

        UserDetail userDetail = userDetailOpt.get();

        // Determine the NIP Atasan based on Approver input
        String nipAtasan = "";
        UserDetail userDetailAtasan = null;

        int selectedApprover = pengajuanResignDTO.getApprover();

        if (selectedApprover == 1) {
            nipAtasan = userDetail.getNipAtasan();
            userDetailAtasan = fetchOrCreateUserDetail(nipAtasan, null);
        } else if (selectedApprover == 2) {
            nipAtasan = userDetail.getNipAtasan();
            UserDetail intermediateAtasan = fetchOrCreateUserDetail(nipAtasan, null);

            if (intermediateAtasan != null) {
                nipAtasan = intermediateAtasan.getNipAtasan();
                userDetailAtasan = fetchOrCreateUserDetail(nipAtasan, null);
            }
        }

        if (userDetailAtasan == null) {
            return buildResponseEntity("Supervisor details not found in HRIS Database", HttpStatus.NOT_FOUND, "No user details found for supervisor with NIP: " + nipAtasan);
        }

        PengajuanResign pengajuanResign = buildPengajuanResign(pengajuanResignDTO, username, userDetail, userDetailAtasan, nipAtasan);
        PengajuanResign savedPengajuanResign = pengajuanResignService.saveResignation(pengajuanResign);

        saveApprovalAtasan(savedPengajuanResign, pengajuanResignDTO, userDetailAtasan, userDetail, nipAtasan);

        // Send notifications and emails
        sendNotificationsAndEmails(userDetail, userDetailAtasan, userDetail.getUserUsername());


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
        } else {
            // Update userDetail Atasan
            Optional<User> userOpt = userService.findByUsername(nipAtasan);
            User user = userOpt.get();

            // Fetch data from HRIS
            List<UserDetail> userDetailsList = userDetailService.fetchUserDetailsByUsername(nipAtasan);

            // Update user detail's user
            if (!userDetailsList.isEmpty()) {
                UserDetail updatedUserDetail = userDetailsList.get(0);

                // Merge fields from HRIS data to existing user detail
                userDetailAtasan.setCabang(updatedUserDetail.getCabang());
                userDetailAtasan.setDivisi(updatedUserDetail.getDivisi());
                userDetailAtasan.setEmail(updatedUserDetail.getEmail());
                userDetailAtasan.setIdDivisi(updatedUserDetail.getIdDivisi());
                userDetailAtasan.setJabatan(updatedUserDetail.getJabatan());
                userDetailAtasan.setNama(updatedUserDetail.getNama());
                userDetailAtasan.setNipAtasan(updatedUserDetail.getNipAtasan());
                userDetailAtasan.setUser(user);

                userDetailService.saveUserDetails(userDetailAtasan);
            }

            // Update user's userDetail
            user.setUserDetails(userDetailAtasan);

        }

        return userDetailAtasan;
    }



    private ResponseEntity<ApiResponse<PengajuanResign>> buildResponseEntity(String message, HttpStatus status, String detail) {
        ApiResponse<PengajuanResign> response = new ApiResponse<>(false, message, status.value(), detail);
        return new ResponseEntity<>(response, status);
    }

    private PengajuanResign buildPengajuanResign(PengajuanResignDTO pengajuanResignDTO, String nipKaryawanResign, UserDetail userDetail, UserDetail userDetailAtasan, String nipAtasan) {
        PengajuanResign pengajuanResign = new PengajuanResign();
        pengajuanResign.setNipUser(nipKaryawanResign);
        pengajuanResign.setNamaKaryawan(userDetail.getNama());
        pengajuanResign.setNamaAtasan(userDetailAtasan.getNama());
        pengajuanResign.setIsiUntukOrangLain(pengajuanResignDTO.isIsiUntukOrangLain());
        pengajuanResign.setTanggalPembuatanAkunHRIS(userDetail.getDateCreated());
        pengajuanResign.setTanggalBerakhirBekerja(pengajuanResignDTO.getTanggalBerakhirBekerja());
        pengajuanResign.setUserDetailResign(userDetail);
        pengajuanResign.setNipAtasan(userDetailAtasan.getUserUsername());
        pengajuanResign.setEmailAtasan(userDetailAtasan.getEmail());

        return pengajuanResign;
    }

    private PengajuanResign buildPengajuanResignAdmin(PengajuanResignAdminDTO pengajuanResignDTO, String nipKaryawanResign, UserDetail userDetail, UserDetail userDetailAtasan) {
        PengajuanResign pengajuanResign = new PengajuanResign();
        pengajuanResign.setNipUser(nipKaryawanResign);
        pengajuanResign.setNamaKaryawan(userDetail.getNama());
        pengajuanResign.setNamaAtasan(userDetailAtasan.getNama());
        pengajuanResign.setIsiUntukOrangLain(pengajuanResignDTO.isIsiUntukOrangLain());
        pengajuanResign.setTanggalPembuatanAkunHRIS(userDetail.getDateCreated());
        pengajuanResign.setTanggalBerakhirBekerja(pengajuanResignDTO.getTanggalBerakhirBekerja());
        pengajuanResign.setUserDetailResign(userDetail);
        pengajuanResign.setNipAtasan(userDetailAtasan.getUserUsername());
        pengajuanResign.setEmailAtasan(userDetailAtasan.getEmail());

        return pengajuanResign;
    }

    private void saveApprovalAtasan(PengajuanResign savedPengajuanResign, PengajuanResignDTO pengajuanResignDTO, UserDetail userDetailAtasan, UserDetail userDetailKaryawan, String nipAtasan) {
        ApprovalAtasan approvalAtasanObj = new ApprovalAtasan();
        approvalAtasanObj.setNipKaryawanResign(userDetailKaryawan.getUserUsername());
        approvalAtasanObj.setNamaKaryawan(userDetailKaryawan.getNama());
        approvalAtasanObj.setNipAtasan(userDetailAtasan.getUserUsername());
        approvalAtasanObj.setNamaAtasan(userDetailAtasan.getNama());
        approvalAtasanObj.setEmailAtasan(userDetailAtasan.getEmail());
        approvalAtasanObj.setUserDetailAtasan(userDetailAtasan);
        approvalAtasanObj.setPengajuanResign(savedPengajuanResign);

        approvalAtasanService.saveApproval(approvalAtasanObj);
    }

    private void saveApprovalAtasanAdmin (PengajuanResign savedPengajuanResign, PengajuanResignAdminDTO pengajuanResignDTO, UserDetail userDetailAtasan, UserDetail userDetailKaryawan) {
        ApprovalAtasan approvalAtasanObj = new ApprovalAtasan();
        approvalAtasanObj.setNipKaryawanResign(userDetailKaryawan.getUserUsername());
        approvalAtasanObj.setNamaKaryawan(userDetailKaryawan.getNama());
        approvalAtasanObj.setNipAtasan(userDetailAtasan.getUserUsername());
        approvalAtasanObj.setNamaAtasan(userDetailAtasan.getNama());
        approvalAtasanObj.setEmailAtasan(userDetailAtasan.getEmail());
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

        Page<ResignationProgressDTO> progressPage = null;

        if ("ROLE_ADMIN".equals(userRole)) {
            // Admin logic: retrieve full data
            progressPage = pengajuanResignService.getResignationProgress(
                    nipUser, namaKaryawan, page, size, sortBy, sortDirection);
        } else if ("ROLE_USER".equals(userRole)) {
            // User logic: retrieve resignations where nipAtasan = userNip
            progressPage = pengajuanResignService.getResignationProgressByNipAtasan(
                    userNip, namaKaryawan, page, size, sortBy, sortDirection);
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



//    @GetMapping("/admin/{id}")
//    public ResponseEntity<ApiResponse<ResignationProgressDTO>> getResignationProgressById(@PathVariable Long id) {
//        ResignationProgressDTO resignationProgressDTO = pengajuanResignService.getResignationProgressById(id);
//        if (resignationProgressDTO != null) {
//            ApiResponse<ResignationProgressDTO> response = new ApiResponse<>(
//                    resignationProgressDTO, true, "Fetch Succeeded", HttpStatus.OK.value());
//
//
//            return  new ResponseEntity<>(response, HttpStatus.OK);
//        } else {
//
//            ApiResponse<ResignationProgressDTO> response = new ApiResponse<>(
//                    false, "Resignation Not Found", HttpStatus.NOT_FOUND.value());
//
//
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
//    }


    @PostMapping("admin")
    public ResponseEntity<ApiResponse<PengajuanResign>> createResignationAdmin(@RequestBody PengajuanResignAdminDTO pengajuanResignAdminDTO) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return buildResponseEntity("User not authenticated", HttpStatus.UNAUTHORIZED, "Authentication required");
        }

        //get nip karyawan resign from dto (input froma admin)
        String nipKaryawanResign = pengajuanResignAdminDTO.getNipKaryawanResign();

        //get user detail karyawan resign from nip
        Optional<UserDetail> userDetailOpt = Optional.ofNullable(userDetailService.findByUsername(nipKaryawanResign));

        UserDetail userDetail = userDetailOpt.get();

        // Determine the NIP Atasan based on Approver input
        String nipAtasan = "";
        UserDetail userDetailAtasan = null;

        int selectedApprover = pengajuanResignAdminDTO.getApprover();

        if (selectedApprover == 1) {
            nipAtasan = userDetail.getNipAtasan();
            userDetailAtasan = fetchOrCreateUserDetail(nipAtasan, null);
        } else if (selectedApprover == 2) {
            nipAtasan = userDetail.getNipAtasan();
            UserDetail intermediateAtasan = fetchOrCreateUserDetail(nipAtasan, null);

            if (intermediateAtasan != null) {
                nipAtasan = intermediateAtasan.getNipAtasan();
                userDetailAtasan = fetchOrCreateUserDetail(nipAtasan, null);
            }
        }

        if (userDetailAtasan == null) {
            return buildResponseEntity("Supervisor details not found in HRIS Database", HttpStatus.NOT_FOUND, "No user details found for supervisor with NIP: " + nipAtasan);
        }

        // Build and save the resignation request
        PengajuanResign pengajuanResign = buildPengajuanResignAdmin(pengajuanResignAdminDTO, nipKaryawanResign, userDetail, userDetailAtasan);
        PengajuanResign savedPengajuanResign = pengajuanResignService.saveResignation(pengajuanResign);

        // Save approval details
        saveApprovalAtasanAdmin(savedPengajuanResign, pengajuanResignAdminDTO, userDetailAtasan, userDetail);

        // Send notifications and emails
        sendNotificationsAndEmails(userDetail, userDetailAtasan, userDetail.getUserUsername());

        // Construct the response
        ApiResponse<PengajuanResign> response = new ApiResponse<>(savedPengajuanResign, true, "Resignation created successfully", HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("admin/check-nip-karyawan/{nipKaryawanResign}")
    public ResponseEntity<ApiResponse<UserDetailsResponseDTO>> checkNipKaryawan(@PathVariable String nipKaryawanResign) {
        // Fetch or create Karyawan user detail
        UserDetail karyawanUserDetail = fetchOrCreateUserDetail(nipKaryawanResign, null);

        if (karyawanUserDetail == null) {
            return new ResponseEntity<>(new ApiResponse<>(null, false, "Karyawan not found in HRIS", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }

        // Fetch or create Atasan user detail
        String nipAtasan = karyawanUserDetail.getNipAtasan();
        UserDetail atasanUserDetail = fetchOrCreateUserDetail(nipAtasan, null);

        if (atasanUserDetail == null) {
            return new ResponseEntity<>(new ApiResponse<>(null, false, "Atasan not found in HRIS", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }

        // Create response DTO with user details of Karyawan and Atasan
        UserDetailsResponseDTO userDetailsResponseDTO = new UserDetailsResponseDTO(karyawanUserDetail, atasanUserDetail);

        ApiResponse<UserDetailsResponseDTO> response = new ApiResponse<>(userDetailsResponseDTO, true, "User details fetched successfully", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private void sendNotificationsAndEmails(UserDetail userDetail, UserDetail userDetailAtasan, String nipKaryawanResign) {
        String emailAtasan = userDetailAtasan.getEmail();
        String userEmail = userDetail.getEmail();
        String userNama = userDetail.getNama();
        String atasanNama = userDetailAtasan.getNama();
        notificationService.sendNotification("Approval Required: Resignation Request from: " + nipKaryawanResign + ", " + userNama, userDetail, userDetailAtasan.getUserUsername());
        notificationService.sendNotification("Resignation request submitted", userDetail, nipKaryawanResign);
        String linkKaryawan = "http://localhost:4200/#/progress-approval";
        String linkAtasan = "http://localhost:4200/#/approval-atasan";
        Map<String, Object> variablesKaryawan = emailTemplateService.createEmailVariables(userNama, "Your resignation request has been submitted.", linkKaryawan);
        Map<String, Object> variablesAtasan = emailTemplateService.createEmailVariables(atasanNama, "Approval Required: New Resignation Request from " + nipKaryawanResign + ", " + userNama, linkAtasan);
        try {

            if(userEmail != null)
            {
                emailTemplateService.sendHtmlEmail(userEmail, "Resignation Request Submitted", "email-template", variablesKaryawan);
            }
            if (userEmail != null)
            {
                emailTemplateService.sendHtmlEmail(emailAtasan, "Approval Required: New Resignation Request from " + nipKaryawanResign + ", " + userNama, "email-template", variablesAtasan);
            }


        } catch (MessagingException e) {
            e.printStackTrace();
            // Handle exception
        }


    }









}
