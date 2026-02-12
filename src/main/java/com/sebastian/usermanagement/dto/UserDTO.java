package com.sebastian.usermanagement.dto;

import com.sebastian.usermanagement.model.Role;

public class UserDTO {
    private final Long id;
    private final String name;
    private final Role role;

    public UserDTO(Long id, String name, Role role) { //No need for no arg constructor because there's no JSON input. 
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
