package com.hrs.checklist_resign.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.stereotype.Service;

@Entity
@Table(name = "hrs_app_resign_approval_hrservicesadmin")
public class ApprovalHRServicesAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "approval_atasan_id", referencedColumnName = "id")
    @JsonBackReference
    private ApprovalAtasan approvalAtasan;

    @Column(name = "hutang_kelebihan_biaya_berobat")
    private String excessOfClaim;

    @Column(name = "penyelesaian_biaya_di_hr")
    private String penyelesaianBiayaHR;

    @Column(name = "penonaktifan_kartu_elektronik")
    private String penonaktifanKartuElektronik;

    @Column(name = "approval_hr_services_admin_status")
    private String approvalHRServicesAdminStatus;

    //getter setter


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ApprovalAtasan getApprovalAtasan() {
        return approvalAtasan;
    }

    public void setApprovalAtasan(ApprovalAtasan approvalAtasan) {
        this.approvalAtasan = approvalAtasan;
    }

    public String getExcessOfClaim() {
        return excessOfClaim;
    }

    public void setExcessOfClaim(String excessOfClaim) {
        this.excessOfClaim = excessOfClaim;
    }

    public String getPenyelesaianBiayaHR() {
        return penyelesaianBiayaHR;
    }

    public void setPenyelesaianBiayaHR(String penyelesaianBiayaHR) {
        this.penyelesaianBiayaHR = penyelesaianBiayaHR;
    }

    public String getPenonaktifanKartuElektronik() {
        return penonaktifanKartuElektronik;
    }

    public void setPenonaktifanKartuElektronik(String penonaktifanKartuElektronik) {
        this.penonaktifanKartuElektronik = penonaktifanKartuElektronik;
    }

    public String getApprovalHRServicesAdminStatus() {
        return approvalHRServicesAdminStatus;
    }

    public void setApprovalHRServicesAdminStatus(String approvalHRServicesAdminStatus) {
        this.approvalHRServicesAdminStatus = approvalHRServicesAdminStatus;
    }
}
