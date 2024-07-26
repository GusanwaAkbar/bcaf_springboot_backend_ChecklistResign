package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.*;
import com.hrs.checklist_resign.interfaces.Approval;
import com.hrs.checklist_resign.interfaces.ApprovalService;
import com.hrs.checklist_resign.response.ApiResponse;
import com.hrs.checklist_resign.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/tracking")
public class TrackingController {

    @Autowired
    private ApprovalGeneralServicesService approvalGeneralServicesService;

    @Autowired
    private ApprovalHRIRService approvalHRIRService;

    @Autowired
    private ApprovalHRLearningService approvalHRLearningService;

    @Autowired
    private ApprovalHRPayrollService approvalHRPayrollService;

    @Autowired
    private ApprovalHRServicesAdminService approvalHRServicesAdminService;

    @Autowired
    private ApprovalHRTalentService approvalHRTalentService;

    @Autowired
    private ApprovalSecurityAdministratorService approvalSecurityAdministratorService;

    @Autowired
    private ApprovalTreasuryService approvalTreasuryService;

    @Autowired
    private FinalApprovalService finalApprovalService;

    @Autowired
    private ApprovalAtasanService approvalAtasanService;

    @Autowired
    private PengajuanResignService pengajuanResignService;

    @GetMapping("/get-resignations/{nipKaryawan}")
    public ResponseEntity<ApiResponse<PengajuanResign>> getPengajuanResignByUser(@PathVariable String nipKaryawan)
    {
        return getApproval(nipKaryawan, (ApprovalService<PengajuanResign>) pengajuanResignService);
    }


    @GetMapping("/get-approval-general-services/{nipKaryawan}")
    public ResponseEntity<ApiResponse<ApprovalGeneralServices>> getApprovalGeneralServicesByUser(@PathVariable String nipKaryawan) {
        return getApproval(nipKaryawan, (ApprovalService<ApprovalGeneralServices>) approvalGeneralServicesService);
    }

    @GetMapping("/get-approval-hr-ir/{nipKaryawan}")
    public ResponseEntity<ApiResponse<ApprovalHRIR>> getApprovalHRIRByUser(@PathVariable String nipKaryawan) {
        return getApproval(nipKaryawan, (ApprovalService<ApprovalHRIR>) approvalHRIRService);
    }

    @GetMapping("/get-approval-hr-learning/{nipKaryawan}")
    public ResponseEntity<ApiResponse<ApprovalHRLearning>> getApprovalHRLearningByUser(@PathVariable String nipKaryawan) {
        return getApproval(nipKaryawan, (ApprovalService<ApprovalHRLearning>) approvalHRLearningService);
    }

    @GetMapping("/get-approval-hr-payroll/{nipKaryawan}")
    public ResponseEntity<ApiResponse<ApprovalHRPayroll>> getApprovalHRPayrollByUser(@PathVariable String nipKaryawan) {
        return getApproval(nipKaryawan, (ApprovalService<ApprovalHRPayroll>) approvalHRPayrollService);
    }

    @GetMapping("/get-approval-hr-services-admin/{nipKaryawan}")
    public ResponseEntity<ApiResponse<ApprovalHRServicesAdmin>> getApprovalHRServicesAdminByUser(@PathVariable String nipKaryawan) {
        return getApproval(nipKaryawan, (ApprovalService<ApprovalHRServicesAdmin>) approvalHRServicesAdminService);
    }

    @GetMapping("/get-approval-hr-talent/{nipKaryawan}")
    public ResponseEntity<ApiResponse<ApprovalHRTalent>> getApprovalHRTalentByUser(@PathVariable String nipKaryawan) {
        return getApproval(nipKaryawan, (ApprovalService<ApprovalHRTalent>) approvalHRTalentService);
    }

    @GetMapping("/get-approval-security-administrator/{nipKaryawan}")
    public ResponseEntity<ApiResponse<ApprovalSecurityAdministrator>> getApprovalSecurityAdministratorByUser(@PathVariable String nipKaryawan) {
        return getApproval(nipKaryawan, (ApprovalService<ApprovalSecurityAdministrator>) approvalSecurityAdministratorService);
    }

    @GetMapping("/get-approval-treasury/{nipKaryawan}")
    public ResponseEntity<ApiResponse<ApprovalTreasury>> getApprovalTreasuryByUser(@PathVariable String nipKaryawan) {
        return getApproval(nipKaryawan, (ApprovalService<ApprovalTreasury>) approvalTreasuryService);
    }

    @GetMapping("/get-final-approval/{nipKaryawan}")
    public ResponseEntity<ApiResponse<FinalApproval>> getFinalApprovalByUser(@PathVariable String nipKaryawan) {
        return getApproval(nipKaryawan, (ApprovalService<FinalApproval>) finalApprovalService);
    }

    @GetMapping("/get-approval-atasan/{nipKaryawan}")
    public ResponseEntity<ApiResponse<ApprovalAtasan>> getApprovalAtasanByUser(@PathVariable String nipKaryawan) {
        return getApproval(nipKaryawan, (ApprovalService<ApprovalAtasan>) approvalAtasanService);
    }

    private <T extends Approval> ResponseEntity<ApiResponse<T>> getApproval(String nipKaryawan, ApprovalService<T> service) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            ApiResponse<T> response = new ApiResponse<>(false, "User not authenticated", HttpStatus.UNAUTHORIZED.value(), "Authentication required");
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


        Optional<T> approval = service.findByNipKaryawanResign(nipKaryawan);

        if ("ROLE_ADMIN".equals(userRole)) {
            if (approval.isPresent()) {
                ApiResponse<T> response = new ApiResponse<>(approval.get(), true, "Approval successfully fetched", HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ApiResponse<T> response = new ApiResponse<>(null, false, "Approval not found", HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else if ("ROLE_USER".equals(userRole)) {
            if (approval.isPresent()) {
                if  (userNip.equals(approval.get().getNipAtasan()) || userNip.equals(approval.get().getNipKaryawanResign()))
                {
                    ApiResponse<T> response = new ApiResponse<>(approval.get(), true, "Approval successfully fetched", HttpStatus.OK.value());
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    ApiResponse<T> response = new ApiResponse<>(false, "User doesn't have the right to access this item", HttpStatus.UNAUTHORIZED.value(), "Unauthorized access");
                    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
                }
            } else {
                ApiResponse<T> response = new ApiResponse<>(false, "Approval not found", HttpStatus.OK.value());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else {
            ApiResponse<T> response = new ApiResponse<>(false, "Invalid role", HttpStatus.FORBIDDEN.value(), "Access denied");
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    }
}
