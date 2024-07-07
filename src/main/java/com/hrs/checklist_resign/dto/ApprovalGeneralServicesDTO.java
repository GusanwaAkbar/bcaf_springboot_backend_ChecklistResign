package com.hrs.checklist_resign.dto;

import java.util.Date;

public class ApprovalGeneralServicesDTO {
    private long id;
    private String penutupanPin;
    private String pengembalianKendaraanDinas;
    private String inventarisKantor;
    private String pengembalianAktiva;
    private String pengembalianKendaraanUMK3;
    private String approvalGeneralServicesStatus;
    private String remarks;
    private Date approvedDate;
    private Date createdDate;
    private String approvedBy;
    private String documentPath;

    public ApprovalGeneralServicesDTO() {}

    public ApprovalGeneralServicesDTO(long id, String penutupanPin, String pengembalianKendaraanDinas,
                                      String inventarisKantor, String pengembalianAktiva,
                                      String pengembalianKendaraanUMK3, String approvalGeneralServicesStatus,
                                      String remarks, Date approvedDate, Date createdDate,
                                      String approvedBy, String documentPath) {
        this.id = id;
        this.penutupanPin = penutupanPin;
        this.pengembalianKendaraanDinas = pengembalianKendaraanDinas;
        this.inventarisKantor = inventarisKantor;
        this.pengembalianAktiva = pengembalianAktiva;
        this.pengembalianKendaraanUMK3 = pengembalianKendaraanUMK3;
        this.approvalGeneralServicesStatus = approvalGeneralServicesStatus;
        this.remarks = remarks;
        this.approvedDate = approvedDate;
        this.createdDate = createdDate;
        this.approvedBy = approvedBy;
        this.documentPath = documentPath;
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

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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
}
