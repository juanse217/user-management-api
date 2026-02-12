package com.sebastian.usermanagement.dto;

import com.sebastian.usermanagement.model.Role;

public class CreateUserDTO {
    private final String name;
    private final String password;
    private final Role role;

    public CreateUserDTO(){
        this(null, null, null);
    }

    //Testability constructor

    public CreateUserDTO(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }



    public String getName() {
        return name;
    }


    public String getPassword() {
        return password;
    }


    public Role getRole() {
        return role;
    }

}
