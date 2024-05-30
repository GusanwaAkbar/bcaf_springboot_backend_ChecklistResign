package com.hrs.checklist_resign.payload;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtResponse {
    private String token;
    private Long id;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(String token, Long id, String username, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.authorities = authorities;
    }
}