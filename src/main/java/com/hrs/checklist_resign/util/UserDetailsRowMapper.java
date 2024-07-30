package com.hrs.checklist_resign.util;

import com.hrs.checklist_resign.Model.User;
import com.hrs.checklist_resign.Model.UserDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

 public class UserDetailsRowMapper implements RowMapper<UserDetail> {
    @Override
    public UserDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserDetail userDetailObj = new UserDetail();
        User user = new User(); // Create a new User object
        userDetailObj.setUser(user); // Set user details
        userDetailObj.setNama(rs.getString("Nama"));
        userDetailObj.setEmail(rs.getString("Email"));
        userDetailObj.setCabang(rs.getString("Cabang"));
        userDetailObj.setIdDivisi(rs.getString("id_divisi"));
        userDetailObj.setDivisi(rs.getString("Divisi"));
        userDetailObj.setJabatan(rs.getString("Jabatan"));
        userDetailObj.setExternalUser(rs.getString("External_User"));
        userDetailObj.setNipAtasan(rs.getString("nipAtasan"));
        userDetailObj.setDateCreated(rs.getDate("dateCreated")); // Add this line
        return userDetailObj;
    }

}