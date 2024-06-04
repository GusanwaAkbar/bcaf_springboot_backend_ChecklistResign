package com.hrs.checklist_resign.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class PengajuanResignDTO {
    private Long id;
    private boolean isiUntukOrangLain;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date tanggalPembuatanAkunHRIS;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date tanggalBerakhirBekerja;

    // Getters and setters

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
}
