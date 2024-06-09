package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.ApprovalGeneralServices;
import com.hrs.checklist_resign.repository.ApprovalGeneralServicesRepository;
import com.hrs.checklist_resign.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprovalGeneralServicesService {

    private final ApprovalGeneralServicesRepository repository;

    @Autowired
    public ApprovalGeneralServicesService(ApprovalGeneralServicesRepository repository) {
        this.repository = repository;
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

        ApprovalGeneralServices updatedApprovalGeneralServices = repository.save(approvalGeneralServices);
        ApiResponse<ApprovalGeneralServices> response = new ApiResponse<>(updatedApprovalGeneralServices, true, "Update succeeded", HttpStatus.OK.value());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
