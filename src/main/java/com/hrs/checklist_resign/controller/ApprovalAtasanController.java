package com.hrs.checklist_resign.controller;

import com.hrs.checklist_resign.Model.ApprovalAtasan;
import com.hrs.checklist_resign.service.ApprovalAtasanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approval/atasan")
public class ApprovalAtasanController {

    @Autowired
    private ApprovalAtasanService approvalAtasanService;

    @PostMapping("")
    public ResponseEntity<ApprovalAtasan> createApproval(@RequestBody ApprovalAtasan approvalAtasan) {
        ApprovalAtasan createdApprovalAtasan = approvalAtasanService.saveApproval(approvalAtasan);
        return ResponseEntity.ok(createdApprovalAtasan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApprovalAtasan> getApproval(@PathVariable Long id) {
        return approvalAtasanService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ApprovalAtasan>> getAllApproval() {
        List<ApprovalAtasan> approvalAtasanList = approvalAtasanService.findAll();
        return ResponseEntity.ok(approvalAtasanList);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApprovalAtasan> updateApproval(@PathVariable Long id, @RequestBody ApprovalAtasan approvalAtasanDetails) {
        return approvalAtasanService.findById(id).map(approvalAtasan -> {
            approvalAtasan.setSerahTerimaTugas(approvalAtasanDetails.isSerahTerimaTugas());
            approvalAtasan.setPengembalianNotebook(approvalAtasanDetails.isPengembalianNotebook());
            approvalAtasan.setPengembalianKunciLoker(approvalAtasanDetails.isPengembalianKunciLoker());
            approvalAtasan.setPengembalianKunciRuangan(approvalAtasanDetails.isPengembalianKunciRuangan());
            approvalAtasan.setPenyerahanSuratPengunduranDiri(approvalAtasanDetails.isPenyerahanSuratPengunduranDiri());
            approvalAtasan.setPengembalianIdCard(approvalAtasanDetails.isPengembalianIdCard());
            approvalAtasan.setHapusAplikasiMobile(approvalAtasanDetails.isHapusAplikasiMobile());
            approvalAtasan.setUninstallSoftwareNotebook(approvalAtasanDetails.isUninstallSoftwareNotebook());
            approvalAtasan.setUninstallSoftwareUnitKerja(approvalAtasanDetails.isUninstallSoftwareUnitKerja());
            approvalAtasan.setApprovalStatusAtasan(approvalAtasanDetails.getApprovalStatusAtasan());
            approvalAtasan.setRemarksAtasan(approvalAtasanDetails.getRemarksAtasan());
            ApprovalAtasan updatedApprovalAtasan = approvalAtasanService.saveApproval(approvalAtasan);
            return ResponseEntity.ok(updatedApprovalAtasan);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApproval(@PathVariable Long id) {
        return approvalAtasanService.findById(id).map(approvalAtasan -> {
            approvalAtasanService.deleteById(id);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
