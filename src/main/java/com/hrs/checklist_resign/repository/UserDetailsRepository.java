package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetail, Long> {

    UserDetail findByUserUsername(String username);
}
