package com.hrs.checklist_resign.interfaces;

import java.util.Optional;

public interface ApprovalService<T extends Approval> {
    Optional<T> findByNipKaryawanResign(String nipKaryawan);
}
