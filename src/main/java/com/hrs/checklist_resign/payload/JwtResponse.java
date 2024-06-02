package com.hrs.checklist_resign.payload;

import com.hrs.checklist_resign.Model.UserDetail;
import com.hrs.checklist_resign.Model.UserDetailsImpl;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Data
public class JwtResponse {
    private String token;
    private Long id;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private UserDetail userDetail;


    public JwtResponse(String token, String username, Collection<? extends GrantedAuthority> authorities, UserDetail userDetails) {
        this.token = token;
        this.username = username;
        this.authorities = authorities;
        this.userDetail = userDetails;
    }
}
