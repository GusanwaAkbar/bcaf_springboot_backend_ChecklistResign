package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "app_hrs_resign_bucket_pengajuan_resign")
public class PengajuanResign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nip_user")
    private String nipUser;

    @Column(name = "isi_untuk_orang_lain")
    private boolean isiUntukOrangLain;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_pembuatan_akun_hris")
    private Date tanggalPembuatanAkunHRIS;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "tanggal_berakhir_bekerja")
    private Date tanggalBerakhirBekerja;

    @OneToOne
    @JoinColumn(name = "user_detail_id", referencedColumnName = "id")
    private UserDetail userDetailResign;

    @Column(name = "nip_atasan")
    private String nipAtasan;

    @Column(name = "email_atasan")
    private String emailAtasan;




    @OneToOne(mappedBy = "pengajuanResign", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "pengajuanResign")
    private ApprovalAtasan approvalAtasan;

    @OneToOne(mappedBy = "pengajuanResign", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "FinalApproval")
    private FinalApproval finalApproval;


    // Getters and setters


    public FinalApproval getFinalApproval() {
        return finalApproval;
    }

    public void setFinalApproval(FinalApproval finalApproval) {
        this.finalApproval = finalApproval;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isIsiUntukOrangLain() {
        return isiUntukOrangLain;
    }

    public void setIsiUntukOrangLain(boolean isiUntukOrangLain) {
        this.isiUntukOrangLain = isiUntukOrangLain;
    }

    public Date getTanggalPembuatanAkunHRIS() {
        return tanggalPembuatanAkunHRIS;
    }

    public void setTanggalPembuatanAkunHRIS(Date tanggalPembuatanAkunHRIS) {
        this.tanggalPembuatanAkunHRIS = tanggalPembuatanAkunHRIS;
    }

    public Date getTanggalBerakhirBekerja() {
        return tanggalBerakhirBekerja;
    }

    public void setTanggalBerakhirBekerja(Date tanggalBerakhirBekerja) {
        this.tanggalBerakhirBekerja = tanggalBerakhirBekerja;
    }

    public UserDetail getUserDetailResign() {
        return userDetailResign;
    }

    public void setUserDetailResign(UserDetail userDetailResign) {
        this.userDetailResign = userDetailResign;
    }

    public String getNipAtasan() {
        return nipAtasan;
    }

    public void setNipAtasan(String nipAtasan) {
        this.nipAtasan = nipAtasan;
    }

    public ApprovalAtasan getApprovalAtasan() {
        return approvalAtasan;
    }

    public void setApprovalAtasan(ApprovalAtasan approvalAtasan) {
        this.approvalAtasan = approvalAtasan;
    }

    public String getEmailAtasan() {
        return emailAtasan;
    }

    public void setEmailAtasan(String emailAtasan) {
        this.emailAtasan = emailAtasan;
    }

    public String getNipUser() {
        return nipUser;
    }

    public void setNipUser(String nipUser) {
        this.nipUser = nipUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PengajuanResign that = (PengajuanResign) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
