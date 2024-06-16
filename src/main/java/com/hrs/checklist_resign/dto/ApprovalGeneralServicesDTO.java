package com.hrs.checklist_resign.dto;

public class ApprovalGeneralServicesDTO {
    private long id;
    private String penutupanPin;
    private String pengembalianKendaraanDinas;
    private String inventarisKantor;
    private String pengembalianAktiva;
    private String pengembalianKendaraanUMK3;
    private String approvalGeneralServicesStatus;
    private String remarks;

    public ApprovalGeneralServicesDTO() {
    }

    public ApprovalGeneralServicesDTO(long id, String penutupanPin, String pengembalianKendaraanDinas,
                                      String inventarisKantor, String pengembalianAktiva,
                                      String pengembalianKendaraanUMK3, String approvalGeneralServicesStatus,
                                      String remarks) {
        this.id = id;
        this.penutupanPin = penutupanPin;
        this.pengembalianKendaraanDinas = pengembalianKendaraanDinas;
        this.inventarisKantor = inventarisKantor;
        this.pengembalianAktiva = pengembalianAktiva;
        this.pengembalianKendaraanUMK3 = pengembalianKendaraanUMK3;
        this.approvalGeneralServicesStatus = approvalGeneralServicesStatus;
        this.remarks = remarks;
    }

    // Getters and setters


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
