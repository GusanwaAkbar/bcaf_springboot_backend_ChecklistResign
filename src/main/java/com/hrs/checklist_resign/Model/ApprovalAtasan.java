package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "app_hrs_resign_bucket_approval_atasan")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApprovalAtasan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nip_karyawan")
    private String nipKaryawanResign;

    @Column(name = "nip_atasan")
    private String nipAtasan;

    @Column(name = "email_atasan")
    private String emailAtasan;

    //change this one to one to many to many
    @ManyToOne
    @JoinColumn(name = "user_detail_atasan_id", referencedColumnName = "id")
    private UserDetail userDetailAtasan;

//    @OneToOne(mappedBy = "approvalAtasan", cascade = CascadeType.ALL)
//    private PengajuanResign pengajuanResign;

    @OneToOne
    @JoinColumn(name = "pengajuan_resign_id", referencedColumnName = "id")
    @JsonManagedReference(value = "pengajuanResign")
    private  PengajuanResign pengajuanResign;


    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "serah_terima_tugas")
    private String serahTerimaTugas;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "pengembalian_notebook")
    private String pengembalianNotebook;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "pengembalian_kunci_loker")
    private String pengembalianKunciLoker;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "pengembalian_kunci_ruangan")
    private String pengembalianKunciRuangan;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "penyerahan_surat_pengunduran_diri")
    private String penyerahanSuratPengunduranDiri;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "pengembalian_id_card")
    private String pengembalianIdCard;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "hapus_aplikasi_mobile")
    private String hapusAplikasiMobile;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "uninstall_software_notebook")
    private String uninstallSoftwareNotebook;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "uninstall_software_unit_kerja")
    private String uninstallSoftwareUnitKerja;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "approval_status_atasan")
    private String approvalStatusAtasan;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Column(name = "remarks_atasan")
    private String remarksAtasan;

    @OneToOne(mappedBy = "approvalAtasan", cascade = CascadeType.ALL)
    @JsonBackReference(value = "approvalHRTalent")
    private ApprovalHRTalent approvalHRTalent;

    @OneToOne(mappedBy = "approvalAtasan", cascade = CascadeType.ALL)
    @JsonBackReference(value = "approvalHRIR")
    private ApprovalHRIR approvalHRIR;

    @OneToOne(mappedBy = "approvalAtasan", cascade = CascadeType.ALL)
    @JsonBackReference(value = "approvalGeneralServices")
    private ApprovalGeneralServices approvalGeneralServices;

    @OneToOne(mappedBy = "approvalAtasan", cascade = CascadeType.ALL)
    @JsonBackReference(value = "approvalHRPayroll")
    private ApprovalHRPayroll approvalHRPayroll;

    @OneToOne(mappedBy = "approvalAtasan", cascade = CascadeType.ALL)
    @JsonBackReference(value = "approvalHRServicesAdmin")
    private ApprovalHRServicesAdmin approvalHRServicesAdmin;

    @OneToOne(mappedBy = "approvalAtasan", cascade = CascadeType.ALL)
    @JsonBackReference(value = "approvalSecurityAdministrator")
    private ApprovalSecurityAdministrator approvalSecurityAdministrator;

    @OneToOne(mappedBy = "approvalAtasan", cascade = CascadeType.ALL)
    @JsonBackReference(value = "approvalTreasury")
    private ApprovalTreasury approvalTreasury;

    @OneToOne(mappedBy = "approvalAtasan", cascade = CascadeType.ALL)
    @JsonBackReference(value = "approvalHRLearning")
    private ApprovalHRLearning approvalHRLearning;


    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PengajuanResign getPengajuanResign() {
        return pengajuanResign;
    }

    public void setPengajuanResign(PengajuanResign pengajuanResign) {
        this.pengajuanResign = pengajuanResign;
    }

    public String getSerahTerimaTugas() {
        return serahTerimaTugas;
    }

    public void setSerahTerimaTugas(String serahTerimaTugas) {
        this.serahTerimaTugas = serahTerimaTugas;
    }

    public String getPengembalianNotebook() {
        return pengembalianNotebook;
    }

    public void setPengembalianNotebook(String pengembalianNotebook) {
        this.pengembalianNotebook = pengembalianNotebook;
    }

    public String getPengembalianKunciLoker() {
        return pengembalianKunciLoker;
    }

    public void setPengembalianKunciLoker(String pengembalianKunciLoker) {
        this.pengembalianKunciLoker = pengembalianKunciLoker;
    }

    public String getPengembalianKunciRuangan() {
        return pengembalianKunciRuangan;
    }

    public void setPengembalianKunciRuangan(String pengembalianKunciRuangan) {
        this.pengembalianKunciRuangan = pengembalianKunciRuangan;
    }

    public String getPenyerahanSuratPengunduranDiri() {
        return penyerahanSuratPengunduranDiri;
    }

    public void setPenyerahanSuratPengunduranDiri(String penyerahanSuratPengunduranDiri) {
        this.penyerahanSuratPengunduranDiri = penyerahanSuratPengunduranDiri;
    }

    public String getPengembalianIdCard() {
        return pengembalianIdCard;
    }

    public void setPengembalianIdCard(String pengembalianIdCard) {
        this.pengembalianIdCard = pengembalianIdCard;
    }

    public String getHapusAplikasiMobile() {
        return hapusAplikasiMobile;
    }

    public void setHapusAplikasiMobile(String hapusAplikasiMobile) {
        this.hapusAplikasiMobile = hapusAplikasiMobile;
    }

    public String getUninstallSoftwareNotebook() {
        return uninstallSoftwareNotebook;
    }

    public void setUninstallSoftwareNotebook(String uninstallSoftwareNotebook) {
        this.uninstallSoftwareNotebook = uninstallSoftwareNotebook;
    }

    public String getUninstallSoftwareUnitKerja() {
        return uninstallSoftwareUnitKerja;
    }

    public void setUninstallSoftwareUnitKerja(String uninstallSoftwareUnitKerja) {
        this.uninstallSoftwareUnitKerja = uninstallSoftwareUnitKerja;
    }

    public ApprovalHRTalent getApprovalHRTalent() {
        return approvalHRTalent;
    }

    public void setApprovalHRTalent(ApprovalHRTalent approvalHRTalent) {
        this.approvalHRTalent = approvalHRTalent;
    }

    public String getApprovalStatusAtasan() {
        return approvalStatusAtasan;
    }

    public void setApprovalStatusAtasan(String approvalStatusAtasan) {
        this.approvalStatusAtasan = approvalStatusAtasan;
    }

    public String getNipAtasan() {
        return nipAtasan;
    }

    public void setNipAtasan(String nipAtasan) {
        this.nipAtasan = nipAtasan;
    }


    public UserDetail getUserDetailAtasan() {
        return userDetailAtasan;
    }

    public void setUserDetailAtasan(UserDetail userDetailAtasan) {
        this.userDetailAtasan = userDetailAtasan;
    }

    public String getRemarksAtasan() {
        return remarksAtasan;
    }

    public void setRemarksAtasan(String remarksAtasan) {
        this.remarksAtasan = remarksAtasan;
    }

    public String getEmailAtasan() {
        return emailAtasan;
    }

    public void setEmailAtasan(String emailAtasan) {
        this.emailAtasan = emailAtasan;
    }

    public String getNipKaryawanResign() {
        return nipKaryawanResign;
    }

    public void setNipKaryawanResign(String nipKaryawanResign) {
        this.nipKaryawanResign = nipKaryawanResign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetail that = (UserDetail) o;
        return id != null && Objects.equals(id, that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
