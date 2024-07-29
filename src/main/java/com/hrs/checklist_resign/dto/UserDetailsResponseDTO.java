package com.hrs.checklist_resign.dto;

import com.hrs.checklist_resign.Model.UserDetail;

public class UserDetailsResponseDTO {
    private UserDetail karyawanResignDetail;
    private UserDetail atasanDetail;

    public UserDetailsResponseDTO(UserDetail karyawanResignDetail, UserDetail atasanDetail) {
        this.karyawanResignDetail = karyawanResignDetail;
        this.atasanDetail = atasanDetail;
    }

    public UserDetail getKaryawanResignDetail() {
        return karyawanResignDetail;
    }

    public void setKaryawanResignDetail(UserDetail karyawanResignDetail) {
        this.karyawanResignDetail = karyawanResignDetail;
    }

    public UserDetail getAtasanDetail() {
        return atasanDetail;
    }

    public void setAtasanDetail(UserDetail atasanDetail) {
        this.atasanDetail = atasanDetail;
    }
}
