package com.hrs.checklist_resign.repository;

import com.hrs.checklist_resign.Model.User;
import com.hrs.checklist_resign.dto.UserResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.roles NOT LIKE %:role%")
    List<User> findUsersWithRolesNotContaining(@Param("role") String role);

//    @Query("SELECT new com.hrs.checklist_resign.dto.UserResponseDTO(u.username, u.userDetails, u.authorities, u.rolesList) " +
//            "FROM User u WHERE u.roles NOT LIKE %:role%")
//    List<UserResponseDTO> findUsersWithRolesNotContainingV2(@Param("role") String role);

    @Query("SELECT new com.hrs.checklist_resign.dto.UserResponseDTO(u.username, u.userDetails, u.roles) " +
            "FROM User u WHERE u.roles NOT LIKE %:role%")
    List<UserResponseDTO> findUsersWithRolesNotContainingV2(@Param("role") String role);

}