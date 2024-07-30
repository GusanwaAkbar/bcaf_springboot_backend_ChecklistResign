package com.hrs.checklist_resign.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;

import java.util.Date;

public class PengajuanResignDTO {

    private boolean isiUntukOrangLain;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date tanggalPembuatanAkunHRIS;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date tanggalBerakhirBekerja;

    private int approver;

    public PengajuanResignDTO(boolean isiUntukOrangLain, Date tanggalPembuatanAkunHRIS, Date tanggalBerakhirBekerja, int approver) {
        this.isiUntukOrangLain = isiUntukOrangLain;
        this.tanggalPembuatanAkunHRIS = tanggalPembuatanAkunHRIS;
        this.tanggalBerakhirBekerja = tanggalBerakhirBekerja;
        this.approver = approver;
    }

    // Getters and setters



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



    public int getApprover() {
        return approver;
    }



    public void setApprover(int approver) {
        approver = approver;
    }
}
