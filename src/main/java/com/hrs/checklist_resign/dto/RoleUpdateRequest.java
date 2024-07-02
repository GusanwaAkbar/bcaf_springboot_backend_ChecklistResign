package com.hrs.checklist_resign.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class RoleUpdateRequest {

    @NotNull
    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
