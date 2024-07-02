package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Entity
@Table(name = "app_hrs_resign_approval_hrir")
public class ApprovalHRIR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nip_karyawan_resign")
    private String nipKaryawanResign;

    @OneToOne
    @JoinColumn(name = "approval_atasan_id", referencedColumnName = "id")
    @JsonManagedReference(value = "approvalHRIR")
    private ApprovalAtasan approvalAtasan;


    @Value("${exitInterview:false}")
    @Column(name = "exit_interview")
    private String exitInterview;

    @Value("${approvalHRIR:false}")
    private String approvalHRIRStatus;

    private String remarks;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "approved_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;




    //Getter Setter

    @PrePersist
    protected void onCreate() {
        createdDate = new Date();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

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

    public String getExitInterview() {
        return exitInterview;
    }

    public void setExitInterview(String exitInterview) {
        this.exitInterview = exitInterview;
    }

    public String getApprovalHRIRStatus() {
        return approvalHRIRStatus;
    }

    public void setApprovalHRIRStatus(String approvalHRIRStatus) {
        this.approvalHRIRStatus = approvalHRIRStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
