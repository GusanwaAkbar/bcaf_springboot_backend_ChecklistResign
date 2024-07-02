package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Entity
@Table( name = "app_hrs_resign_approval_hrtalent")
public class ApprovalHRTalent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nip_karyawan_resign")
    private String nipKaryawanResign;

    @Value("${pengecekan.biaya:false}")
    private String pengecekanBiaya;

    @Value("${approvalHRTalent:false}")
    private String approvalHRTalentStatus;

    private String remarks;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "approved_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;


    @OneToOne()
    @JoinColumn(name = "approval_atasan_id", referencedColumnName = "id")
    @JsonManagedReference(value = "approvalHRTalent")
    private ApprovalAtasan approvalAtasan;



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

    public String getPengecekanBiaya() {
        return pengecekanBiaya;
    }

    public void setPengecekanBiaya(String pengecekanBiaya) {
        this.pengecekanBiaya = pengecekanBiaya;
    }


    public String getApprovalHRTalentStatus() {
        return approvalHRTalentStatus;
    }

    public void setApprovalHRTalentStatus(String approvalHRTalentStatus) {
        this.approvalHRTalentStatus = approvalHRTalentStatus;
    }

    public ApprovalAtasan getApprovalAtasan() {
        return approvalAtasan;
    }

    public void setApprovalAtasan(ApprovalAtasan approvalAtasan) {
        this.approvalAtasan = approvalAtasan;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
