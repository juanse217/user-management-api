package com.sebastian.usermanagement.dto;

import com.sebastian.usermanagement.model.Role;

public class UserDTO {
    private Long id;
    private String name;
    private Role role;

    public UserDTO(Long id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    
}   
