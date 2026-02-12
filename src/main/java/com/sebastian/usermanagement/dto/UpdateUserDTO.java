package com.sebastian.usermanagement.dto;

import com.sebastian.usermanagement.model.Role;

public class UpdateUserDTO {
    private final String name;
    private final Role role;

    public UpdateUserDTO(){
        this(null,null); //Jackson will be in charge to assign the fields witouth the setters. There are other ways to properly do this with annotations or records. 
    }

    //Doing it for manual creation and testing. 
    public UpdateUserDTO(String name, Role role) {
        this.name = name;
        this.role = role;
    }

    public String getName() {
        return name;
    }


    public Role getRole() {
        return role;
    }


    
}
