package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table( name = "app_hrs_resign_approval_securityadministrator")
public class ApprovalSecurityAdministrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nip_karyawan_resign")
    private String nipKaryawanResign;

    @OneToOne
    @JoinColumn(name = "approval_atasan_id", referencedColumnName = "id")
    @JsonManagedReference(value = "approvalSecurityAdministrator")
    private ApprovalAtasan approvalAtasan;

    @Column(name = "permohonan_penutupan_user")
    private String permohonanPenutupanUser;

    @Column(name = "penutupan_email_bca")
    private String penutupanEmailBCA;

    @Column(name = "pengembalianToken")
    private String pengembalianToken;

    @Column(name = "approval_security_administrator_status")
    private String approvalSecurityAdministratorStatus;

    private String remarks;


    //Getter and Setter


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNipKaryawanResign() {
        return nipKaryawanResign;
    }

    public void setNipKaryawanResign(String nipKaryawanResign) {
        this.nipKaryawanResign = nipKaryawanResign;
    }

    public ApprovalAtasan getApprovalAtasan() {
        return approvalAtasan;
    }

    public void setApprovalAtasan(ApprovalAtasan approvalAtasan) {
        this.approvalAtasan = approvalAtasan;
    }

    public String getPermohonanPenutupanUser() {
        return permohonanPenutupanUser;
    }

    public void setPermohonanPenutupanUser(String permohonanPenutupanUser) {
        this.permohonanPenutupanUser = permohonanPenutupanUser;
    }

    public String getPenutupanEmailBCA() {
        return penutupanEmailBCA;
    }

    public void setPenutupanEmailBCA(String penutupanEmailBCA) {
        this.penutupanEmailBCA = penutupanEmailBCA;
    }

    public String getPengembalianToken() {
        return pengembalianToken;
    }

    public void setPengembalianToken(String pengembalianToken) {
        this.pengembalianToken = pengembalianToken;
    }

    public String getApprovalSecurityAdministratorStatus() {
        return approvalSecurityAdministratorStatus;
    }

    public void setApprovalSecurityAdministratorStatus(String approvalSecurityAdministratorStatus) {
        this.approvalSecurityAdministratorStatus = approvalSecurityAdministratorStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
