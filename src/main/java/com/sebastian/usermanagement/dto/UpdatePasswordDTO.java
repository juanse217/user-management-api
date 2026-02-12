package com.sebastian.usermanagement.dto;

public class UpdatePasswordDTO {
    private final String currentPassword;
    private final String newPassword; 

    public UpdatePasswordDTO(){
        this(null, null);
    }
    
    //Testability constructor

    public UpdatePasswordDTO(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }



    public String getCurrentPassword() {
        return currentPassword;
    }


    public String getNewPassword() {
        return newPassword;
    }

}
