package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetail, Long> {
    UserDetail findByUser_Username(String username);
}
