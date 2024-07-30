package com.hrs.checklist_resign.dto;



import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;

import java.util.Date;

public class PengajuanResignAdminDTO {

    private boolean isiUntukOrangLain;

    private String nipKaryawanResign;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date tanggalBerakhirBekerja;

    private int approver;

    // Getters and setters


    public PengajuanResignAdminDTO(boolean isiUntukOrangLain, String nipKaryawanResign, Date tanggalBerakhirBekerja, int approver) {
        this.isiUntukOrangLain = isiUntukOrangLain;
        this.nipKaryawanResign = nipKaryawanResign;
        this.tanggalBerakhirBekerja = tanggalBerakhirBekerja;
        this.approver = approver;
    }

    public boolean isIsiUntukOrangLain() {
        return isiUntukOrangLain;
    }

    public void setIsiUntukOrangLain(boolean isiUntukOrangLain) {
        this.isiUntukOrangLain = isiUntukOrangLain;
    }

    public String getNipKaryawanResign() {
        return nipKaryawanResign;
    }

    public void setNipKaryawanResign(String nipKaryawanResign) {
        this.nipKaryawanResign = nipKaryawanResign;
    }

    public Date getTanggalBerakhirBekerja() {
        return tanggalBerakhirBekerja;
    }

    public void setTanggalBerakhirBekerja(Date tanggalBerakhirBekerja) {
        this.tanggalBerakhirBekerja = tanggalBerakhirBekerja;
    }

    public int getApprover() {
        return approver;
    }

    public void setApprover(int approver) {
        this.approver = approver;
    }
}
