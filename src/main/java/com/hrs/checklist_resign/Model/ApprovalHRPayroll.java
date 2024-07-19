package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Entity
@Table(name = "app_hrs_resign_approval_hrpayroll")
public class ApprovalHRPayroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(name = "nip_karyawan_resign")
    private String nipKaryawanResign;

    @Column(name = "nama_karyawan")
    private String namaKaryawan;

    @Column(name = "nip_atasan")
    private String nipAtasan;

    @Column(name = "nama_atasan")
    private String namaAtasan;

    @OneToOne
    @JoinColumn(name = "approval_atasan_id", referencedColumnName = "id")
    @JsonManagedReference(value = "approvalHRPayroll")
    private ApprovalAtasan approvalAtasan;

    @Column(name = "soft_loan")
    private String softLoan;

    @Column(name = "emergency_loan")
    private String emergencyLoan;

    @Column(name = "smartphone_loan")
    private String smartphoneLoan;

    @Column(name = "motor_loan")
    private String motorLoan;

    @Column(name = "umk_loan")
    private String umkLoan;

    @Column(name = "laptop_loan")
    private String laptopLoan;

    @Column(name = "approval_hr_payroll_status")
    private String approvalHRPayrollStatus;

    private String remarks;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "approved_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date approvedDate;

    @Column(name = "approved_by")
    private String approvedBy;

    private String documentPath;


    //getter and setter

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNipKaryawanResign() {
        return nipKaryawanResign;
    }

    public void setNipKaryawanResign(String nipKaryawanResign) {
        this.nipKaryawanResign = nipKaryawanResign;
    }

    public String getSoftLoan() {
        return softLoan;
    }

    public void setSoftLoan(String softLoan) {
        this.softLoan = softLoan;
    }

    public String getEmergencyLoan() {
        return emergencyLoan;
    }

    public void setEmergencyLoan(String emergencyLoan) {
        this.emergencyLoan = emergencyLoan;
    }

    public String getSmartphoneLoan() {
        return smartphoneLoan;
    }

    public void setSmartphoneLoan(String smartphoneLoan) {
        this.smartphoneLoan = smartphoneLoan;
    }

    public String getMotorLoan() {
        return motorLoan;
    }

    public void setMotorLoan(String motorLoan) {
        this.motorLoan = motorLoan;
    }

    public String getUmkLoan() {
        return umkLoan;
    }

    public void setUmkLoan(String umkLoan) {
        this.umkLoan = umkLoan;
    }

    public String getLaptopLoan() {
        return laptopLoan;
    }

    public void setLaptopLoan(String laptopLoan) {
        this.laptopLoan = laptopLoan;
    }

    public String getApprovalHRPayrollStatus() {
        return approvalHRPayrollStatus;
    }

    public void setApprovalHRPayrollStatus(String approvalHRPayrollStatus) {
        this.approvalHRPayrollStatus = approvalHRPayrollStatus;
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

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }

    public String getNamaKaryawan() {
        return namaKaryawan;
    }

    public void setNamaKaryawan(String namaKaryawan) {
        this.namaKaryawan = namaKaryawan;
    }

    public String getNipAtasan() {
        return nipAtasan;
    }

    public void setNipAtasan(String nipAtasan) {
        this.nipAtasan = nipAtasan;
    }

    public String getNamaAtasan() {
        return namaAtasan;
    }

    public void setNamaAtasan(String namaAtasan) {
        this.namaAtasan = namaAtasan;
    }
}
