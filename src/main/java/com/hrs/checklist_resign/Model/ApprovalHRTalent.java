package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table( name = "app_hrs_resign_approval_hrtalent")
public class ApprovalHRTalent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "pengajuan_resign_id", referencedColumnName = "id")
    @JsonBackReference
    private PengajuanResign pengajuanResign;

    @Value("${pengecekan.biaya:false}")
    private String pengecekanBiaya;

    private String filePengecekanBiaya;

    @Value("${approvalHRTalent:false}")
    private String approvalHRTalentStatus;


    @OneToOne()
    @JoinColumn(name = "approval_atasan_id", referencedColumnName = "id")
    private ApprovalAtasan approvalAtasan;



    //Getter Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PengajuanResign getPengajuanResign() {
        return pengajuanResign;
    }

    public void setPengajuanResign(PengajuanResign pengajuanResign) {
        this.pengajuanResign = pengajuanResign;
    }

    public String getPengecekanBiaya() {
        return pengecekanBiaya;
    }

    public void setPengecekanBiaya(String pengecekanBiaya) {
        this.pengecekanBiaya = pengecekanBiaya;
    }

    public String getFilePengecekanBiaya() {
        return filePengecekanBiaya;
    }

    public void setFilePengecekanBiaya(String filePengecekanBiaya) {
        this.filePengecekanBiaya = filePengecekanBiaya;
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
}
