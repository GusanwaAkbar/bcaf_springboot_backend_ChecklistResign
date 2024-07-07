package com.hrs.checklist_resign.dto;


import java.util.Date;

public class ApprovalAtasanDTO {
    private Long id;
    private String nipAtasan;
    private String emailAtasan;
    private String serahTerimaTugas;
    private String pengembalianNotebook;
    private String pengembalianKunciLoker;
    private String pengembalianKunciRuangan;
    private String penyerahanSuratPengunduranDiri;
    private String pengembalianIdCard;
    private String hapusAplikasiMobile;
    private String uninstallSoftwareNotebook;
    private String uninstallSoftwareUnitKerja;
    private String approvalStatusAtasan;
    private String remarksAtasan;

    private Date approvedDate;
    private Date createdDate;
    private String approvedBy;
    private String documentPath;

    // No-arg constructor
    public ApprovalAtasanDTO() {}

    public ApprovalAtasanDTO(Long id, String nipAtasan, String emailAtasan,
                             String serahTerimaTugas, String pengembalianNotebook, String pengembalianKunciLoker,
                             String pengembalianKunciRuangan, String penyerahanSuratPengunduranDiri,
                             String pengembalianIdCard, String hapusAplikasiMobile,
                             String uninstallSoftwareNotebook, String uninstallSoftwareUnitKerja,
                             String approvalStatusAtasan, String remarksAtasan, Date approvedDate, Date createdDate,
                             String approvedBy, String documentPath) {
        this.id = id;
        this.nipAtasan = nipAtasan;
        this.emailAtasan = emailAtasan;
        this.serahTerimaTugas = serahTerimaTugas;
        this.pengembalianNotebook = pengembalianNotebook;
        this.pengembalianKunciLoker = pengembalianKunciLoker;
        this.pengembalianKunciRuangan = pengembalianKunciRuangan;
        this.penyerahanSuratPengunduranDiri = penyerahanSuratPengunduranDiri;
        this.pengembalianIdCard = pengembalianIdCard;
        this.hapusAplikasiMobile = hapusAplikasiMobile;
        this.uninstallSoftwareNotebook = uninstallSoftwareNotebook;
        this.uninstallSoftwareUnitKerja = uninstallSoftwareUnitKerja;
        this.approvalStatusAtasan = approvalStatusAtasan;
        this.remarksAtasan = remarksAtasan;
        this.approvedDate = approvedDate;
        this.createdDate = createdDate;
        this.approvedBy = approvedBy;
        this.documentPath = documentPath;
    }

    //constructor





    // Getters and setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNipAtasan() {
        return nipAtasan;
    }

    public void setNipAtasan(String nipAtasan) {
        this.nipAtasan = nipAtasan;
    }

    public String getEmailAtasan() {
        return emailAtasan;
    }

    public void setEmailAtasan(String emailAtasan) {
        this.emailAtasan = emailAtasan;
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