package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "app_hrs_resign_bucket_approval_atasan")
public class ApprovalAtasan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pengajuan_resign_id", referencedColumnName = "id")
    @JsonBackReference
    private PengajuanResign pengajuanResign;

    @Column(name = "serah_terima_tugas")
    private boolean serahTerimaTugas;

    @Column(name = "pengembalian_notebook")
    private boolean pengembalianNotebook;

    @Column(name = "pengembalian_kunci_loker")
    private boolean pengembalianKunciLoker;

    @Column(name = "pengembalian_kunci_ruangan")
    private boolean pengembalianKunciRuangan;

    @Column(name = "penyerahan_surat_pengunduran_diri")
    private boolean penyerahanSuratPengunduranDiri;

    @Column(name = "pengembalian_id_card")
    private boolean pengembalianIdCard;

    @Column(name = "hapus_aplikasi_mobile")
    private boolean hapusAplikasiMobile;

    @Column(name = "uninstall_software_notebook")
    private boolean uninstallSoftwareNotebook;

    @Column(name = "uninstall_software_unit_kerja")
    private boolean uninstallSoftwareUnitKerja;

    @Column(name = "approval_status_atasan")
    private String approvalStatusAtasan;

    @Column(name = "remarks_atasan")
    private String remarksAtasan;

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

    public boolean isSerahTerimaTugas() {
        return serahTerimaTugas;
    }

    public void setSerahTerimaTugas(boolean serahTerimaTugas) {
        this.serahTerimaTugas = serahTerimaTugas;
    }

    public boolean isPengembalianNotebook() {
        return pengembalianNotebook;
    }

    public void setPengembalianNotebook(boolean pengembalianNotebook) {
        this.pengembalianNotebook = pengembalianNotebook;
    }

    public boolean isPengembalianKunciLoker() {
        return pengembalianKunciLoker;
    }

    public void setPengembalianKunciLoker(boolean pengembalianKunciLoker) {
        this.pengembalianKunciLoker = pengembalianKunciLoker;
    }

    public boolean isPengembalianKunciRuangan() {
        return pengembalianKunciRuangan;
    }

    public void setPengembalianKunciRuangan(boolean pengembalianKunciRuangan) {
        this.pengembalianKunciRuangan = pengembalianKunciRuangan;
    }

    public boolean isPenyerahanSuratPengunduranDiri() {
        return penyerahanSuratPengunduranDiri;
    }

    public void setPenyerahanSuratPengunduranDiri(boolean penyerahanSuratPengunduranDiri) {
        this.penyerahanSuratPengunduranDiri = penyerahanSuratPengunduranDiri;
    }

    public boolean isPengembalianIdCard() {
        return pengembalianIdCard;
    }

    public void setPengembalianIdCard(boolean pengembalianIdCard) {
        this.pengembalianIdCard = pengembalianIdCard;
    }

    public boolean isHapusAplikasiMobile() {
        return hapusAplikasiMobile;
    }

    public void setHapusAplikasiMobile(boolean hapusAplikasiMobile) {
        this.hapusAplikasiMobile = hapusAplikasiMobile;
    }

    public boolean isUninstallSoftwareNotebook() {
        return uninstallSoftwareNotebook;
    }

    public void setUninstallSoftwareNotebook(boolean uninstallSoftwareNotebook) {
        this.uninstallSoftwareNotebook = uninstallSoftwareNotebook;
    }

    public boolean isUninstallSoftwareUnitKerja() {
        return uninstallSoftwareUnitKerja;
    }

    public void setUninstallSoftwareUnitKerja(boolean uninstallSoftwareUnitKerja) {
        this.uninstallSoftwareUnitKerja = uninstallSoftwareUnitKerja;
    }

    public String getApprovalStatusAtasan() {
        return approvalStatusAtasan;
    }

    public void setApprovalStatusAtasan(String approvalStatusAtasan) {
        this.approvalStatusAtasan = approvalStatusAtasan;
    }

    public String getRemarksAtasan() {
        return remarksAtasan;
    }

    public void setRemarksAtasan(String remarksAtasan) {
        this.remarksAtasan = remarksAtasan;
    }
}
