package com.hrs.checklist_resign.service;

import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.repository.UserDetailsRepository;
import com.hrs.checklist_resign.util.UserDetailsRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

@Service
public class UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveUserDetails(UserDetail userDetails) {
        userDetailsRepository.save(userDetails);
    }

    public UserDetail findByUsername(String username) {
        return userDetailsRepository.findByUser_Username(username);
    }

    public UserDetail updateUserDetails(UserDetail userDetails) {
        return userDetailsRepository.save(userDetails);
    }

    public List<UserDetail> fetchUserDetailsByUsername(String username) {
        String sql = "SELECT DISTINCT h.id, h.c_name AS Nama, h.c_email AS Email, h.c_approver1 AS nipAtasan," +
                "COALESCE(b.c_location, '-') AS Cabang, COALESCE(d.c_id, '-') AS id_divisi, " +
                "COALESCE(d.c_name, '-') AS Divisi, COALESCE(t.c_name, '-') AS Jabatan, " +
                "COALESCE(h.c_externalUser, 'No') AS External_User, " +
                "COALESCE(h.dateCreated, '-') AS dateCreated " + // Add this line
                "FROM app_fd_bcafs_hris h WITH (NOLOCK) " +
                "LEFT JOIN dir_user du WITH (NOLOCK) ON (h.id = du.username) " +
                "LEFT JOIN app_fd_bcafs_branch b WITH (NOLOCK) ON (h.c_branchId = b.c_branchCode) " +
                "LEFT JOIN app_fd_bcafs_title t WITH (NOLOCK) ON (h.c_titleId = t.id) " +
                "LEFT JOIN app_fd_bcafs_division d WITH (NOLOCK) ON (t.c_divisionId = d.id) " +
                "WHERE (h.c_active = 'Yes' OR h.c_externaluser = 'Yes') " +
                "AND h.id = ?";

        return jdbcTemplate.query(sql, new UserDetailsRowMapper(), username);
    }

}
