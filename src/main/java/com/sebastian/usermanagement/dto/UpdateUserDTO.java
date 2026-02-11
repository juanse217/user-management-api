package com.sebastian.usermanagement.dto;

import com.sebastian.usermanagement.model.Role;

public class UpdateUserDTO {
    private String name;
    private Role role;

    public UpdateUserDTO(){

    }

    //Doing it for manual creation and testing. 
    public UpdateUserDTO(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    
}
