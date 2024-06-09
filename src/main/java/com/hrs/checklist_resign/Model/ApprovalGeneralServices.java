package com.hrs.checklist_resign.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "app_hrs_resign_approval_generalservices")
public class ApprovalGeneralServices {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "approval_atasan_id", referencedColumnName = "id")
    @JsonBackReference
    private ApprovalAtasan approvalAtasan;

    @Column(name = "permohonan_penutupan_pin")
    private String penutupanPin;

    @Column(name = "pengembalian_kendaraan_dinas")
    private String pengembalianKendaraanDinas;

    @Column(name = "pengembalian_inventaris_kantor_atk_kartu_nama")
    private String inventarisKantor;

    @Column(name = "serah_terima_bukti_penerimaan_dan_pengembalian_aktiva")
    private String pengembalianAktiva;

    @Column(name = "pengembalian_kendaraan_umk3")
    private String pengembalianKendaraanUMK3;


    @Column(name = "approval_general_services_status")
    private String approvalGeneralServicesStatus;

    //getter and setter


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

    public String getPenutupanPin() {
        return penutupanPin;
    }

    public void setPenutupanPin(String penutupanPin) {
        this.penutupanPin = penutupanPin;
    }

    public String getPengembalianKendaraanDinas() {
        return pengembalianKendaraanDinas;
    }

    public void setPengembalianKendaraanDinas(String pengembalianKendaraanDinas) {
        this.pengembalianKendaraanDinas = pengembalianKendaraanDinas;
    }

    public String getInventarisKantor() {
        return inventarisKantor;
    }

    public void setInventarisKantor(String inventarisKantor) {
        this.inventarisKantor = inventarisKantor;
    }

    public String getPengembalianAktiva() {
        return pengembalianAktiva;
    }

    public void setPengembalianAktiva(String pengembalianAktiva) {
        this.pengembalianAktiva = pengembalianAktiva;
    }

    public String getPengembalianKendaraanUMK3() {
        return pengembalianKendaraanUMK3;
    }

    public void setPengembalianKendaraanUMK3(String pengembalianKendaraanUMK3) {
        this.pengembalianKendaraanUMK3 = pengembalianKendaraanUMK3;
    }

    public String getApprovalGeneralServicesStatus() {
        return approvalGeneralServicesStatus;
    }

    public void setApprovalGeneralServicesStatus(String approvalGeneralServicesStatus) {
        this.approvalGeneralServicesStatus = approvalGeneralServicesStatus;
    }
}
